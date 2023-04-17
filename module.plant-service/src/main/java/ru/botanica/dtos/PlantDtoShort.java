package ru.botanica.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@Component
public class PlantDtoShort {
    private Long id;
    private String name;
    private String shortDescription;
    private boolean isActive;

    private String filePath;

    public PlantDtoShort() {
    }

    @Override
    public String toString() {
        return "PlantDtoList{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", isActive=" + isActive +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
