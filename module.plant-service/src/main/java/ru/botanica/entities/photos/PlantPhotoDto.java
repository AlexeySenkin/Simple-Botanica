package ru.botanica.entities.photos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Getter
@Setter
@Component
public class PlantPhotoDto {
    private Long id;
    private String filePath;

    public PlantPhotoDto() {
    }

    @Override
    public String toString() {
        return "PlantPhotoDto{" +
                "id=" + id +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
