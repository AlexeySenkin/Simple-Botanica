package ru.botanica.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.botanica.dtos.CareDto;
import ru.botanica.dtos.CareDtoShort;
import ru.botanica.entities.Care;
import ru.botanica.entities.CareSpecifications;
import ru.botanica.mappers.CareDtoMapper;
import ru.botanica.entities.PlantCare;
import ru.botanica.dtos.PlantCareDto;
import ru.botanica.mappers.PlantCareDtoMapper;
import ru.botanica.dtos.PlantDto;
import ru.botanica.mappers.PlantDtoMapper;
import ru.botanica.repositories.CareRepository;
import ru.botanica.repositories.PlantCareRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
@Slf4j
public class CareService {
    private final CareRepository careRepository;
    private final PlantCareRepository plantCareRepository;

    /**
     * Возвращает список процедур, учитывающий лишь активные(isActive=true)
     *
     * @return Список процедур
     */
    public List<CareDtoShort> findAllActive() {
        Specification<Care> specification = createSpecificationsWithFilter(true);
        return careRepository.findAll(specification).stream()
                .map(CareDtoMapper::matToDtoShort).toList();
    }

    /**
     * Составляет список, собирающий все параметры, задаваемые при запросе списка процедур
     *
     * @param isActive флаг активной процедуры
     * @return Список параметров
     */
    private Specification<Care> createSpecificationsWithFilter(Boolean isActive) {
        Specification<Care> specification = Specification.where(null);
        if (isActive != null) {
            specification = specification.and(CareSpecifications.isActive(isActive));
        }
        return specification;
    }

    /**
     * Находит процедуру из общего списка по id
     *
     * @param id идентификатор процедуры
     * @return Дто процедуры
     */
    public CareDto findById(Long id) {
        return CareDtoMapper.mapToDto(careRepository.findById(id).orElseThrow());
    }

    /**
     * Создает список ухода для добавления Entity растения
     *
     * @param standardCarePlan Список Dto-процедур для конвертации
     * @param plantDto         Растение
     * @return Список ухода для Entity
     */
    public Set<PlantCare> addAllCaresToPlant(List<PlantCareDto> standardCarePlan, PlantDto plantDto) {
        Set<PlantCare> resultCarePlan = new HashSet<>();
        for (PlantCareDto plantCareDto : standardCarePlan) {
            PlantCare plantCare = new PlantCare();
            plantCare.setCare(CareDtoMapper.mapToEntity(plantCareDto.getCareDto()));
            plantCare.setCareCount(plantCareDto.getCareCount());
            plantCare.setCareVolume(plantCareDto.getCareVolume());
            plantCare.setPlant(PlantDtoMapper.mapToEntity(plantDto));
            resultCarePlan.add(createPlantCare(plantCare));
        }
        return resultCarePlan;
    }

    /**
     * Создает процедуру ухода, привязанную к растению
     *
     * @param plantCare процедура для добавления
     * @return Созданную процедуру
     */
    private PlantCare createPlantCare(PlantCare plantCare) {
        return plantCareRepository.saveAndFlush(plantCare);
    }

    /**
     * Находит все процедуры по id растения
     *
     * @param plantId идентификатор растения
     * @return Список процедур для конкретного растения
     */
    public List<PlantCareDto> findAllPlantDtoCaresByPlantId(Long plantId) {
        return plantCareRepository.findAllByPlantId(plantId).stream()
                .map(PlantCareDtoMapper::mapToDto).toList();
    }

    /**
     * Удаляет все процедуры по id растения
     *
     * @param plantId идентификатор растения
     * @return Список удаленных процедур
     */
    public List<PlantCare> deletePlantCaresByPlantId(Long plantId) {
        return plantCareRepository.deleteByPlantId(plantId);
    }
}
