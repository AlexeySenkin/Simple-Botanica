package ru.botanica.entities.plants;

import org.springframework.stereotype.Component;
import ru.botanica.entities.photos.PlantPhoto;
import ru.botanica.entities.plantCares.PlantCare;
import ru.botanica.entities.plantCares.PlantCareDto;
import ru.botanica.entities.plantCares.PlantCareDtoMapper;

import java.util.ArrayList;
import java.util.List;

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
//        TODO: проверить на ошибки в более готовой версии
        plant.setPhoto(new PlantPhoto(plantDto.getFilePath(), plantDto.getId()));
//        Здесь мы вставить cares не пытаемся. Это отдельный метод при создании\обновлении. Без них Plant
//        существовать может
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
        plantDto.setFilePath(plant.getPhoto() == null ? null : plant.getPhoto().getFilePath());
        if (plant.getCares() == null || plant.getCares().isEmpty()) {
            plantDto.setCares(null);
        } else {
            List<PlantCareDto> careDtoList = new ArrayList<>();
            for (PlantCare care : plant.getCares()) {
                careDtoList.add(PlantCareDtoMapper.mapToDto(care));
            }
            plantDto.setCares(careDtoList);
        }
        return plantDto;
    }

    public static PlantDtoShort mapToDtoShort(Plant plant) {
        PlantDtoShort plantDto = new PlantDtoShort();
        plantDto.setId(plant.getId());
        plantDto.setName(plant.getName());
        plantDto.setShortDescription(plant.getShortDescription());
        plantDto.setActive(plant.isActive());
        plantDto.setFilePath(plant.getPhoto() == null ? null : plant.getPhoto().getFilePath());
        return plantDto;
    }
}
