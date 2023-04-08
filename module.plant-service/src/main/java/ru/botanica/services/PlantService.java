package ru.botanica.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.botanica.entities.photos.PlantPhotoDto;
import ru.botanica.entities.photos.PlantPhotoDtoMapper;
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
        return plantRepository.findAll(specification, pageable)
                .map(PlantDtoMapper::mapToDtoWithIdNameShortDescAndFilePath);
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
        return PlantDtoMapper.mapToDto(plantRepository.findById(id).orElseThrow());
    }

    /**
     * Обновляет значения растения
     *
     * @param plantId          Идентификатор
     * @param name             Название
     * @param family           Семья
     * @param genus            Род
     * @param shortDescription Краткое описание
     * @param description      Полное описание
     * @param filePath         Путь к фото
     * @param isActive         Флаг активного растения
     * @return Идентификатор
     */
    public Long updateByID(long plantId, String name, String family, String genus,
                           String shortDescription, String description, String filePath, boolean isActive) {

        boolean isOk = savePlantInRepo(plantId, name, family, genus, shortDescription, description, filePath, isActive);
        if (isOk) {
            savePhotoInRepo(plantId, filePath);
        }
        return plantId;
    }

    /**
     * Добавляет растения с данными значениями, добавляет фото, если есть,
     * и возвращает идентификатор созданного растения
     *
     * @param name             Название
     * @param family           Семья
     * @param genus            Род
     * @param shortDescription Краткое описание
     * @param description      Полное описание
     * @param filePath         Путь к фото
     * @param isActive         Флаг активного растения
     * @return Идентификатор
     */

//    TODO: наша БД пропускает название как неуникальное значение. Т.е. условных растений с именем Test у нас может быть целая база.
//     Искать вообще по всем полям ничем не помогает: остальные поля изначально могут совпадать, потому два растения с одинаковым именем
//     будут находится и в данном случае. Этот момент надо поправить в скрипте.
//     Так же пока нет обработки ошибок вынужден прокидывать id'шник уже существующего растения и логировать внесение ошибочны данных

//     Поиск нового растения в нашем случае подходит только по имени, т.к. в один момент могут создавать сразу несколько растений и
//     не факт, что id растения в таком случае будет последним. Если команда посчитает это не критичным, переделаю на поиск по
//     последнему id из списка. Тогда можно оставить name не уникальным, но на мой взгляд, это будет ошибкой
    public Long addPlant(String name, String family, String genus,
                         String shortDescription, String description, String filePath, boolean isActive) {
        if (!checkOnExisting(name)) {
            boolean isOk = savePlantInRepo(null, name, family, genus, description, shortDescription, filePath, isActive);
            Long id = plantRepository.findIdByName(name);
            if (isOk) {
                savePhotoInRepo(id, filePath);
            }
            return id;
        } else {
            log.error("Растение с именем " + name + " уже существует");
            return plantRepository.findIdByName(name);
        }
    }

    /**
     * Собирает Dto и затем использует метод save() из CrudRepository.
     *
     * @param plantId          Идентификатор
     * @param name             Название
     * @param family           Семья
     * @param genus            Род
     * @param shortDescription Краткое описание
     * @param description      Полное описание
     * @param filePath         Путь к фото
     * @param isActive         Флаг активного растения
     * @return флаг, прошла ли операция
     */
    private boolean savePlantInRepo(Long plantId, String name, String family, String genus,
                                    String shortDescription, String description, String filePath, boolean isActive) {
        try {
            PlantDto plantDto = new PlantDto();
            /**
             * Если нет значения id, вычисляет последнее доступное
             */
            if (plantId != null) {
                plantDto.setId(plantId);
            } else {
                plantDto.setId(plantRepository.findLastIdAvailable());
            }
            plantDto.setName(name);
            plantDto.setFamily(family);
            plantDto.setGenus(genus);
            plantDto.setShortDescription(shortDescription);
            plantDto.setDescription(description);
            plantDto.setFilePath(filePath);
            plantDto.setActive(isActive);
            plantRepository.saveAndFlush(PlantDtoMapper.mapToEntity(plantDto));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private void savePhotoInRepo(Long plantId, String filePath) {
        PlantPhotoDto photoDto = new PlantPhotoDto();
        photoDto.setId(plantId);
        photoDto.setFilePath(filePath);
        photoRepository.saveAndFlush(PlantPhotoDtoMapper.mapToEntity(photoDto));
    }

    /**
     * Находит, если в базе растение с указанным названием
     *
     * @param name Название
     * @return boolean-значение
     */
    public boolean checkOnExisting(String name) {
        return plantRepository.existsByName(name);
    }

    /**
     * Удаляет растение по идентификатору
     *
     * @param id Идентификатор
     */
    public void deletePlantById(long id) {
        plantRepository.deleteById(id);
    }
}
