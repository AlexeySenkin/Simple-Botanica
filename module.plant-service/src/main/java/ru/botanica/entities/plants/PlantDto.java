package ru.botanica.entities.plants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Objects;

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
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlantDto plantDto = (PlantDto) o;
        return isActive == plantDto.isActive && Objects.equals(id, plantDto.id) && Objects.equals(name, plantDto.name) && Objects.equals(family, plantDto.family) && Objects.equals(genus, plantDto.genus) && Objects.equals(description, plantDto.description) && Objects.equals(shortDescription, plantDto.shortDescription) && Objects.equals(filePath, plantDto.filePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, family, genus, description, shortDescription, isActive, filePath);
    }
}
