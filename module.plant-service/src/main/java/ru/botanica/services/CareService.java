package ru.botanica.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.botanica.entities.care.CareDto;
import ru.botanica.entities.care.CareDtoMapper;
import ru.botanica.entities.plantCares.PlantCare;
import ru.botanica.entities.plantCares.PlantCareDto;
import ru.botanica.entities.plantCares.PlantCareDtoMapper;
import ru.botanica.entities.plants.PlantDto;
import ru.botanica.entities.plants.PlantDtoMapper;
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
     * Находит процедуру из общего списка по id
     *
     * @param id идентификатор процедуры
     * @return Дто процедуры
     */
    public CareDto findById(Long id) {
        return CareDtoMapper.mapToDto(careRepository.findById(id).orElseThrow());
    }

    /**
     * Создает процедуру, привязанную к конкретному растению
     * Основано на действиях с объектами
     *
     * @param careDto      Дто процедуры из общего списка
     * @param plantDto     Дто растения, к которому привязывается процедура
     * @param plantCareDto Дто, уточняющее детали процедуры(количество, объем)
     * @return Дто процедуры, записанной в БД
     */
    public PlantCareDto createPlantCareWithObjects(CareDto careDto, PlantDto plantDto, PlantCareDto plantCareDto) {
        PlantCare plantCare = PlantCareDtoMapper.mapToEntity(plantCareDto,
                PlantDtoMapper.mapToEntity(plantDto), CareDtoMapper.mapToEntity(careDto));
        PlantCare result = plantCareRepository.saveAndFlush(plantCare);
        return PlantCareDtoMapper.mapToDto(result);
    }

    /**
     * Создает процедуру, привязанную к конкретному растению
     * Основано на написании query
     *
     * @param plantCareDto Дто, уточняющее детали процедуры(количество, объем)
     * @param plantId      идентификатор растения, к которому привязывается процедура
     * @param careId       идентификатор процедуры из общего списка
     * @return Дто процедуры, записанной в БД
     */
    public PlantCareDto createPlantCareWithQuery(PlantCareDto plantCareDto, Long plantId, Long careId) {
        /**
         * Проверка на существование процедуры по id растения и процедуры
         */
        if (plantCareRepository.existsByCareIdAndPlantId(careId, plantId).isPresent()) {
            log.error("УЖЕ СУЩЕСТВУЕТ, careId= {}, plantId= {}", careId, plantId);
        } else {
            /**
             * Сохранение данных
             */
            plantCareRepository.insertPlantCare(careId, plantId,
                    plantCareDto.getCareCount(), plantCareDto.getCareVolume());
        }
        return PlantCareDtoMapper.mapToDto(plantCareRepository.findByCareIdAndPlantId(careId, plantId));
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
