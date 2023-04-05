package ru.botanica.entities.plants;

import org.springframework.stereotype.Component;
import ru.botanica.entities.photos.PlantPhoto;

@Component
public final class PlantDtoMapper {
    public static Plant mapToEntity(PlantDto plantDto) {
        Plant plant = new Plant();
        plant.setId(plantDto.getId());
        plant.setName(plantDto.getName());
        plant.setFamily(plantDto.getFamily());
        plant.setGenus(plantDto.getGenus());
        plant.setDescription(plantDto.getDescription());
        plant.setShortDescription(plantDto.getShortDescription());
        plant.setActive(plantDto.isActive());
        plant.setPhoto(new PlantPhoto(plantDto.getFilePath(), plantDto.getId()));
        return plant;
    }

    public static PlantDto mapToDto(Plant plant) {
        PlantDto plantDto = new PlantDto();
        plantDto.setId(plant.getId());
        plantDto.setName(plant.getName());
        plantDto.setFamily(plant.getFamily());
        plantDto.setGenus(plant.getGenus());
        plantDto.setDescription(plant.getDescription());
        plantDto.setShortDescription(plant.getShortDescription());
        plantDto.setActive(plant.isActive());
        plantDto.setFilePath(plant.getPhoto() == null? null : plant.getPhoto().getFilePath());
        return plantDto;
    }

    public static PlantDto mapToDtoWithIdNameShortDescAndFilePath(Plant plant) {
        PlantDto plantDto = new PlantDto();
        plantDto.setId(plant.getId());
        plantDto.setName(plant.getName());
        plantDto.setShortDescription(plant.getShortDescription());
        plantDto.setFilePath(plant.getPhoto() == null? null : plant.getPhoto().getFilePath());
        return plantDto;
    }
}
