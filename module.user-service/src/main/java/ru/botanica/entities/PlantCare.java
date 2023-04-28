package ru.botanica.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



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
    private Long careCount;

    @Column(nullable = false, name = "care_volume")
    private Double careVolume;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "care_id", nullable = false)
    private Care care;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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
                ", care=" + care.getCareId() +
                ", plant=" + plant.getId() +
                '}';
    }
}
