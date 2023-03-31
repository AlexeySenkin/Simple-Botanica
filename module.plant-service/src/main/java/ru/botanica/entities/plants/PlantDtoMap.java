package ru.botanica.entities.plants;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PlantDtoMap {
    private final Map<Long, PlantDto> plantDtoMap = new HashMap<>();

    public void addPlant(PlantDto plantDto) {
        plantDtoMap.put(plantDto.getId(), plantDto);
    }

    public PlantDto getPlant(Long id) {
        return plantDtoMap.get(id);
    }
}
