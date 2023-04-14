package ru.botanica.entities.plantCares;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.botanica.entities.care.Care;
import ru.botanica.entities.plants.Plant;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(schema = "simple_botanica", name = "plant_care")
public class PlantCare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plant_care_id")
    private Long id;

    @Column(nullable = false, name = "care_count")
    private int careCount;

    @Column(nullable = false, name = "care_volume")
    private BigDecimal careVolume;

    @ManyToOne
    @JoinColumn(name = "care_id", nullable = false)
    private Care care;

    @ManyToOne
    @JoinColumn(name = "plant_id", nullable = false)
    private Plant plant;

    public PlantCare() {
    }

    @Override
    public String toString() {
        return "PlantCare{" +
                "id=" + id +
                ", careCount=" + careCount +
                ", careVolume=" + careVolume +
                ", care=" + care.getId() + " " + care.getName() +
                ", plant=" + plant.getId() +" " + plant.getName() +
                '}';
    }
}
