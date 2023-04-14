package ru.botanica.entities.care;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.botanica.entities.plantCares.PlantCare;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(schema = "simple_botanica", name = "care")
public class Care {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "care_id")
    private Long id;

    @Column(name = "care_name", nullable = false, length = 128)
    private String name;

    @Column(name = "is_active")
    private boolean isActive;

    @OneToMany(mappedBy = "care")
    private Set<PlantCare> plantCares;

    public Care() {
    }

    @Override
    public String toString() {
        return "Care{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}