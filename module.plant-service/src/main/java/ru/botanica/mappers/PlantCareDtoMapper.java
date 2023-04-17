package ru.botanica.mappers;

import org.springframework.stereotype.Component;
import ru.botanica.dtos.PlantCareDto;
import ru.botanica.entities.PlantCare;
import ru.botanica.dtos.PlantDto;

@Component
public final class PlantCareDtoMapper {
    public static PlantCareDto mapToDto(PlantCare plantCare) {
        PlantCareDto plantCareDto = new PlantCareDto();
        plantCareDto.setId(plantCare.getId());
        plantCareDto.setCareVolume(plantCare.getCareVolume());
        plantCareDto.setCareCount(plantCare.getCareCount());
        plantCareDto.setCareDto(CareDtoMapper.mapToDto(plantCare.getCare()));
        return plantCareDto;
    }

    public static PlantCare mapToEntity(PlantCareDto plantCareDto, PlantDto plantDto) {
        PlantCare plantCare = new PlantCare();
        plantCare.setId(plantCareDto.getId());
        plantCare.setCareVolume(plantCareDto.getCareVolume());
        plantCare.setCareCount(plantCareDto.getCareCount());
        plantCare.setPlant(PlantDtoMapper.mapToEntity(plantDto));
        plantCare.setCare(CareDtoMapper.mapToEntity(plantCareDto.getCareDto()));
        return plantCare;
    }
}
