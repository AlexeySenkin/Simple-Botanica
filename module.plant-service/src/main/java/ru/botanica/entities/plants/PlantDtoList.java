package ru.botanica.entities.plants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@Component
public class PlantDtoList {
    private Long id;
    private String name;
    private String shortDescription;
    private boolean isActive;

    private String filePath;

    public PlantDtoList() {
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
