package ru.botanica.services;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.botanica.builders.PlantBuilder;
import ru.botanica.dtos.PlantCareDto;
import ru.botanica.dtos.PlantDto;
import ru.botanica.dtos.PlantDtoShort;
import ru.botanica.entities.Plant;
import ru.botanica.entities.PlantSpecifications;
import ru.botanica.exceptions.PlantExistsException;
import ru.botanica.exceptions.ServerHandleException;
import ru.botanica.mappers.PlantDtoMapper;
import ru.botanica.repositories.PlantRepository;

import java.util.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class PlantService {
    private final PlantRepository plantRepository;

    private final PlantPhotoService plantPhotoService;

    private final CareService careService;
    private PlantBuilder plantBuilder;

    /**
     * Метод инициализирующий builder для растений
     */
    @PostConstruct
    private void init() {
        plantBuilder = new PlantBuilder();
    }

    /**
     * Возвращает список растений, учитывающий заданные для поиска параметры
     *
     * @param title    Название
     * @param pageable Страница(номер страницы, размер страницы)
     * @return Список растений
     */
    public Page<PlantDtoShort> findAll(String title, Pageable pageable) throws ServerHandleException {
        Specification<Plant> specification = createSpecificationsWithFilter(title, true);
        try {
            return plantRepository.findAll(specification, pageable)
                    .map(PlantDtoMapper::mapToDtoShort);
        } catch (Exception e) {
            throw new ServerHandleException("Не удалось загрузить растения из БД");
        }
    }

    /**
     * Составляет список, собирающий все параметры, задаваемые при запросе списка растений
     *
     * @param title Название
     * @return Список параметров для запроса
     */
    private Specification<Plant> createSpecificationsWithFilter(String title, Boolean isActive) {
        Specification<Plant> specification = Specification.where(null);
        if (title != null) {
            specification = specification.and(PlantSpecifications.nameLike(title));
        }
        if (isActive != null) {
            specification = specification.and(PlantSpecifications.isActive(isActive));
        }
        return specification;
    }

    /**
     * Возвращает растение по идентификатору
     *
     * @param id Идентификатор
     * @return Растение
     */
    public PlantDto findById(long id) throws PlantExistsException {
        return PlantDtoMapper.mapToDto(plantRepository.findById(id).orElseThrow(() -> new PlantExistsException("Растения с id " + id + " не существует")));
    }

    /**
     * Обновляет значения растения в БД
     *
     * @param plantDto PlantDto.class
     * @return Dto растения
     */
    @Transactional
    public PlantDto updatePlant(PlantDto plantDto) throws Exception {
        if (!isIdExist(plantDto.getId())) {
            throw new PlantExistsException("Растения c id " + plantDto.getId() + " не существует");
        }
        Plant plant = plantBuilder
                .withId(plantDto.getId())
                .withName(plantDto.getName())
                .withFamily(plantDto.getFamily())
                .withGenus(plantDto.getGenus())
                .withShortDescription(plantDto.getShortDescription())
                .withDescription(plantDto.getDescription())
                .withIsActive(plantDto.isActive())
                .build();
        try {
            plantRepository.saveAndFlush(plant);
        } catch (Exception e) {
            throw new ServerHandleException("Не удалось обновить растение. DTO растения: " + plantDto.toString());
        }

        return PlantDtoMapper.mapToDto(addAllOptionsToPlant(plantDto, plant));
    }

    /**
     * Добавляет растение с данными значениями в БД
     *
     * @param plantDto      PlantDto.class
     * @param isOverwriting флаг, нужно ли проводить перезапись в случае существования растения с таким именем
     * @return Dto растения
     */

    @Transactional
    public PlantDto addNewPlant(PlantDto plantDto, boolean isOverwriting) throws Exception {
        if (findByName(plantDto.getName()).isPresent() && !isOverwriting) {
            throw new PlantExistsException("Растение с именем " + plantDto.getName() + " уже существует");
        }
        boolean existsByName = plantRepository.existsByName(plantDto.getName());
        Plant plant;
        if (!existsByName) {
            plant = plantBuilder
                    .withName(plantDto.getName())
                    .withFamily(plantDto.getFamily())
                    .withGenus(plantDto.getGenus())
                    .withShortDescription(plantDto.getShortDescription())
                    .withDescription(plantDto.getDescription())
                    .withIsActive(true)
                    .build();
            plantRepository.saveAndFlush(plant);
        } else if (isOverwriting) {
            plant = plantBuilder
                    .withId(findByName(plantDto.getName()).orElseThrow().getId())
                    .withName(plantDto.getName())
                    .withFamily(plantDto.getFamily())
                    .withGenus(plantDto.getGenus())
                    .withShortDescription(plantDto.getShortDescription())
                    .withDescription(plantDto.getDescription())
                    .withIsActive(true)
                    .build();
            plantRepository.saveAndFlush(plant);
        } else {
            throw new ServerHandleException("Не удалось сохранить растение. DTO растения: " + plantDto);
        }
        return PlantDtoMapper.mapToDto(addAllOptionsToPlant(plantDto, plant));
    }

    /**
     * Добавляет фото и план ухода к растению, если соответствующие данные присутствуют
     *
     * @param plantDto Переданная Dto
     * @param plant    Сохраненная Entity
     * @return Растение с новыми данными
     */
    private Plant addAllOptionsToPlant(PlantDto plantDto, Plant plant) throws ServerHandleException {
        if (isPhotoPathAvailable(plantDto.getFilePath())) {
            try {
                plant.setPhoto(plantPhotoService.createPhoto(plantDto.getFilePath(), plant.getId()));
            } catch (Exception e) {
                throw new ServerHandleException("Серверу не удалось сохранить путь к фото");
            }
        } else {
            log.warn("Фото для растения с id= {} отсутствует", plant.getId());
        }
        if (plantDto.getStandardCarePlan() != null && !plantDto.getStandardCarePlan().isEmpty()) {
            try {
                List<PlantCareDto> standardCarePlan = plantDto.getStandardCarePlan();
                plant.setCares(careService.addAllCaresToPlant(standardCarePlan, PlantDtoMapper.mapToDto(plant)));
            } catch (Exception e) {
                throw new ServerHandleException("Серверу не удалось сохранить план ухода для растения");
            }
        } else {
            log.warn("План ухода для растения с id= {} отсутствует", plant.getId());
        }
        plant = plantRepository.saveAndFlush(plant);

        return plant;
    }

    /**
     * Проверяет доступен ли путь к фотографии
     *
     * @param photoFilePath имя фотографии
     * @return boolean-значение
     */
    private boolean isPhotoPathAvailable(String photoFilePath) {
        return photoFilePath != null && !photoFilePath.isBlank() && !photoFilePath.isEmpty();
    }

    /**
     * Удаляет растение по идентификатору
     *
     * @param id Идентификатор
     */
    public PlantDto deletePlantById(long id) throws Exception {
        PlantDto plantDto = findById(id);
        plantDto.setActive(false);
        try {
            plantRepository.saveAndFlush(PlantDtoMapper.mapToEntity(plantDto));
        } catch (Exception e) {
            throw new ServerHandleException("Не удалось удалить растение. Dto растения: " + plantDto.toString());
        }
        return plantDto;
    }

    public Optional<PlantDto> findByName(String name) {
        return plantRepository.findByName(name).map(PlantDtoMapper::mapToDto);
    }

    public boolean isIdExist(Long id) throws ServerHandleException {
        try {
            return plantRepository.existsById(id);
        } catch (Exception e) {
            throw new ServerHandleException("Не удалось загрузить список активных действий");
        }
    }
}
