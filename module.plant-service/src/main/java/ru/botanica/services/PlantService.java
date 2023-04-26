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
    public Page<PlantDtoShort> findAll(String title, Pageable pageable) {
        Specification<Plant> specification = createSpecificationsWithFilter(title, true);
        return plantRepository.findAll(specification, pageable)
                .map(PlantDtoMapper::mapToDtoShort);
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
    public PlantDto findById(long id) {
        return PlantDtoMapper.mapToDto(plantRepository.findById(id).orElseThrow());
    }

    /**
     * Обновляет значения растения в БД
     *
     * @param plantDto PlantDto.class
     * @return Dto растения
     */
    @Transactional
    public PlantDto updatePlant(PlantDto plantDto) {
        Plant plant = plantBuilder
                .withId(plantDto.getId())
                .withName(plantDto.getName())
                .withFamily(plantDto.getFamily())
                .withGenus(plantDto.getGenus())
                .withShortDescription(plantDto.getShortDescription())
                .withDescription(plantDto.getDescription())
                .withIsActive(plantDto.isActive())
                .build();
        plantRepository.saveAndFlush(plant);
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
            throw new Exception("Не удалось сохранить растение: " + plantDto.toString());
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
    private Plant addAllOptionsToPlant(PlantDto plantDto, Plant plant) {
        if (isPhotoPathAvailable(plantDto.getFilePath())) {
            plant.setPhoto(plantPhotoService.createPhoto(plantDto.getFilePath(), plant.getId()));
        } else {
            log.warn("Сохранить фото для растения с id= {} не вышло", plant.getId());
        }
        if (plantDto.getStandardCarePlan() != null && !plantDto.getStandardCarePlan().isEmpty()) {
            List<PlantCareDto> standardCarePlan = plantDto.getStandardCarePlan();
            plant.setCares(careService.addAllCaresToPlant(standardCarePlan, PlantDtoMapper.mapToDto(plant)));
        } else {
            log.warn("Сохранить план ухода для растения с id= {} не вышло", plant.getId());
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
    public PlantDto deletePlantById(long id) {
        PlantDto plantDto = findById(id);
        plantDto.setActive(false);
        plantRepository.saveAndFlush(PlantDtoMapper.mapToEntity(plantDto));
        return plantDto;
    }

    public Optional<PlantDto> findByName(String name) {
        return plantRepository.findByName(name).map(PlantDtoMapper::mapToDto);
    }

    public boolean isIdExist(Long id) {
        return plantRepository.existsById(id);
    }
}
