package ru.botanica.entities.plants;

import org.springframework.stereotype.Component;

@Component
public class PlantDtoMapper {
    public Plant map(PlantDto plantDto) {
        Plant plant = new Plant();
        plant.setId(plantDto.getId());
        plant.setName(plantDto.getName());
        plant.setFamily(plantDto.getFamily());
        plant.setGenus(plantDto.getGenus());
        plant.setDescription(plantDto.getDescription());
        plant.setShortDescription(plantDto.getShortDescription());
        plant.setActive(plantDto.isActive());
        return plant;
    }

    public PlantDto map(Plant plant) {
        PlantDto plantDto = new PlantDto();
        plantDto.setId(plant.getId());
        plantDto.setName(plant.getName());
        plantDto.setFamily(plant.getFamily());
        plantDto.setGenus(plant.getGenus());
        plantDto.setDescription(plant.getDescription());
        plantDto.setShortDescription(plant.getShortDescription());
        plantDto.setActive(plant.isActive());
        return plantDto;
    }
}
