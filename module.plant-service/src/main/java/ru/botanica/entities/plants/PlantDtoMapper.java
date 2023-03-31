package ru.botanica.entities.plants;

import org.springframework.stereotype.Component;
import ru.botanica.entities.photos.PlantPhoto;

@Component
public class PlantDtoMapper {
    public Plant mapToEntity(PlantDto plantDto) {
        Plant plant = new Plant();
        plant.setId(plantDto.getId());
        plant.setName(plantDto.getName());
        plant.setFamily(plantDto.getFamily());
        plant.setGenus(plantDto.getGenus());
        plant.setDescription(plantDto.getDescription());
        plant.setShortDescription(plantDto.getShortDescription());
        plant.setActive(plantDto.isActive());
//        TODO: проверить на ошибки в более готовой версии
        plant.setPhoto(new PlantPhoto(plantDto.getFilePath(), plantDto.getId()));
        return plant;
    }

    public PlantDto mapToDto(Plant plant) {
        PlantDto plantDto = new PlantDto();
        plantDto.setId(plant.getId());
        plantDto.setName(plant.getName());
        plantDto.setFamily(plant.getFamily());
        plantDto.setGenus(plant.getGenus());
        plantDto.setDescription(plant.getDescription());
        plantDto.setShortDescription(plant.getShortDescription());
        plantDto.setActive(plant.isActive());
        plantDto.setFilePath(plant.getPhoto().getFilePath());
        return plantDto;
    }

    public PlantDto mapToDtoWithIdNameShortDescAndFilePath(Plant plant) {
        PlantDto plantDto = new PlantDto();
        plantDto.setId(plant.getId());
        plantDto.setName(plant.getName());
        plantDto.setShortDescription(plant.getShortDescription());
        plantDto.setFilePath(plant.getPhoto().getFilePath());
        return plantDto;
    }
}
