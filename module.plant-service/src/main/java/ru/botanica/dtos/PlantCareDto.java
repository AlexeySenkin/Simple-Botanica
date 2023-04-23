package ru.botanica.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlantCareDto that = (PlantCareDto) o;
        return careCount == that.careCount && Objects.equals(id, that.id) && Objects.equals(careVolume, that.careVolume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, careCount, careVolume);
    }
}
