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
import ru.botanica.repositories.CareRepository;
import ru.botanica.repositories.PlantCareRepository;

import java.util.List;


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
     * Создает процедуру ухода, привязанную к конкретному растению
     *
     * @param plantDto     Дто растения, к которому привязывается процедура ухода
     * @param plantCareDto Дто, уточняющее детали процедуры(количество, объем)
     * @return Дто процедуры, записанной в БД
     */
    public PlantCareDto createPlantCareWithObjects(PlantDto plantDto, PlantCareDto plantCareDto) {
        PlantCare plantCare = PlantCareDtoMapper.mapToEntity(plantCareDto, plantDto);
        PlantCare result = plantCareRepository.saveAndFlush(plantCare);
        return PlantCareDtoMapper.mapToDto(result);
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
