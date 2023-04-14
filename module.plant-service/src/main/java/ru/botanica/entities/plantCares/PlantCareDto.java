package ru.botanica.entities.plantCares;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Getter
@Setter
@Component
public class PlantCareDto {
    private Long id;
    private String name;
    private int careCount;
    private BigDecimal careVolume;

    public PlantCareDto() {
    }

    @Override
    public String toString() {
        return "PlantCareDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", careCount=" + careCount +
                ", careVolume=" + careVolume +
                '}';
    }
}
