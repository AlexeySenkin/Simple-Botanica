package ru.botanica.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.botanica.entities.photos.PlantPhotoRepository;
import ru.botanica.entities.plants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlantService {
    private final PlantRepository plantRepository;
    private final PlantPhotoRepository photoRepository;

    /**
     * Возвращает список растений, учитывающий заданные для поиска параметры
     *
     * @param title    Название
     * @param pageable Страница(номер страницы, размер страницы)
     * @return Список растений
     */
    public Page<PlantDto> findAll(String title, Pageable pageable) {
        Specification<Plant> specification = createSpecificationsWithFilter(title);
        Page<Plant> plantPage = plantRepository.findAll(specification, pageable);
        return plantPage.map(PlantDtoMapper::mapToDtoWithIdNameShortDescAndFilePath);
    }

    /**
     * Составляет список, собирающий все параметры, задаваемые при запросе списка растений
     *
     * @param title Название
     * @return Список параметров для запроса
     */
    private Specification<Plant> createSpecificationsWithFilter(String title) {
        Specification<Plant> specification = Specification.where(null);
        if (title != null) {
            specification = specification.and(PlantSpecifications.nameLike(title));
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
        Plant plant = plantRepository.findById(id).orElseThrow();
        return PlantDtoMapper.mapToDto(plant);
    }

    /**
     * Обновляет значения растения {plant_id, name, short_description, description, file_path}
     *
     * @param plantId                Идентификатор
     * @param name              Название
     * @param family            Семья
     * @param genus             Род
     * @param shortDescription Краткое описание
     * @param description       Полное описание
     * @param file_path         Путь к фото
     * @param isActive         Флаг активного растения
     * @return Идентификатор
     */
    public Long updateByID(long plantId, String name, String family, String genus,
                           String shortDescription, String description, String file_path, boolean isActive) {
        /**
         * Часть, обновляющая данные растения
         */
        plantRepository.updateById(name, family, genus, description, shortDescription, isActive, plantId);

        /**
         * Часть, добавляющая фотографию к растению, если поля не существует и обновляющая оное в обратном случае
         */
        if (photoRepository.existsById(plantId)) {
            photoRepository.updateById(file_path, plantId);
        } else {
            photoRepository.insertById(file_path, plantId);
        }
        return plantId;
    }

}
