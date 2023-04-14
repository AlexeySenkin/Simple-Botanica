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


@Service
@RequiredArgsConstructor
@Slf4j
//  Сначала сделал только отображение, потому не стал привязывать сервис никуда, чтобы не ломать тесты
public class CareService {
    private final CareRepository careRepository;
    private final PlantCareRepository plantCareRepository;

    public CareDto findById(Long id) {
        return CareDtoMapper.mapToDto(careRepository.findById(id).orElseThrow());
    }

    public PlantCareDto createPlantCareWithObjects(CareDto careDto, PlantDto plantDto, PlantCareDto plantCareDto) {
        PlantCare plantCare = PlantCareDtoMapper.mapToEntity(plantCareDto,
                PlantDtoMapper.mapToEntity(plantDto), CareDtoMapper.mapToEntity(careDto));
        PlantCare result = plantCareRepository.saveAndFlush(plantCare);
        return PlantCareDtoMapper.mapToDto(result);
    }

    public PlantCareDto createPlantCareWithQuery(PlantCareDto plantCareDto, Long plantId, Long careId) {
        /**
         * Проверка на существование процедуры по id растения и процедуры
         */
        if (plantCareRepository.existsByCareIdAndPlantId(careId, plantId).isPresent()){
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
}
