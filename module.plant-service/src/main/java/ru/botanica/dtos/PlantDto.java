package ru.botanica.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Component
public class PlantDto {
    private Long id;
    private String name;
    private String family;
    private String genus;
    private String description;
    private String shortDescription;
    private boolean isActive;

    private String filePath;

    private List<PlantCareDto> standardCarePlan;

    public PlantDto() {
    }

    @Override
    public String toString() {
        return "PlantDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", family='" + family + '\'' +
                ", genus='" + genus + '\'' +
                ", description='" + description + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", isActive=" + isActive + '\'' +
                ", filePath=" + filePath +
                ", cares=" + (standardCarePlan == null ? null : standardCarePlan.toString()) +
                '}';
    }
}
