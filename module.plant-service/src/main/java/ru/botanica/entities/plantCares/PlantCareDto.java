package ru.botanica.entities.plantCares;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import ru.botanica.entities.care.CareDto;

import java.math.BigDecimal;

@Getter
@Setter
@Component
public class PlantCareDto {
    private Long id;
    private int careCount;
    private BigDecimal careVolume;

    private CareDto careDto;

    public PlantCareDto() {
    }

    @Override
    public String toString() {
        return "PlantCareDto{" +
                "id=" + id +
                ", careCount=" + careCount +
                ", careVolume=" + careVolume +
                ", careDto=" + careDto +
                '}';
    }
}
