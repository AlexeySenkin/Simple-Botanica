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
import ru.botanica.entities.plants.*;
import ru.botanica.repositories.PlantRepository;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class PlantService {
    private final PlantRepository plantRepository;

    private final PlantPhotoService plantPhotoService;
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
                .withDescription(plantDto.getShortDescription())
                .withIsActive(plantDto.isActive())
                .build();
        plantRepository.saveAndFlush(plant);
        if (isPhotoPathAvailable(plantDto.getFilePath())) {
            plant.setPhoto(plantPhotoService.saveOrUpdate(plant.getId(), plantDto.getFilePath()));
        }
        return PlantDtoMapper.mapToDto(plant);
    }

    /**
     * Добавляет растение с данными значениями и добавляет фото в БД
     *
     * @param plantDto PlantDto.class
     * @return Dto растения
     */

    @Transactional
    public PlantDto addNewPlant(PlantDto plantDto) {
        if (!plantRepository.existsByName(plantDto.getName())) {
            Plant plant = plantBuilder
                    .withName(plantDto.getName())
                    .withFamily(plantDto.getFamily())
                    .withGenus(plantDto.getGenus())
                    .withShortDescription(plantDto.getShortDescription())
                    .withDescription(plantDto.getDescription())
                    .withIsActive(true)
                    .build();
            plantRepository.saveAndFlush(plant);
            if (isPhotoPathAvailable(plantDto.getFilePath())) {
                plant.setPhoto(plantPhotoService.saveOrUpdate(plant.getId(), plantDto.getFilePath()));
//                Пока оставлено по просьбе Марии
//            plantRepository.saveAndFlush(plant);
            }
            return PlantDtoMapper.mapToDto(plant);
        } else {
            return plantDto;
        }
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
