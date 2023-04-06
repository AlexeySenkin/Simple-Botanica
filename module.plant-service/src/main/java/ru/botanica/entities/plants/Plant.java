package ru.botanica.entities.plants;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.botanica.entities.photos.PlantPhoto;

@Entity
@Getter
@Setter
@Table(schema = "simple_botanica", name = "plants")
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plant_id")
    private Long id;

    @Column(nullable = false, length = 128, name = "name", unique = true)
    private String name;

    @Column(nullable = false, length = 128, name = "family")
    private String family;

    @Column(nullable = false, length = 128, name = "genus")
    private String genus;

    @Column(nullable = false, length = 1024, name = "description")
    private String description;

    @Column(nullable = false, length = 128, name = "short_description")
    private String shortDescription;

    @Column(nullable = false, name = "is_active")
    private boolean isActive;

    @OneToOne
    @JoinColumn(name = "plant_id")
    private PlantPhoto photo;

    public Plant() {
    }

    @Override
    public String toString() {
        return "Plant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", family='" + family + '\'' +
                ", genus='" + genus + '\'' +
                ", description='" + description + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", isActive=" + isActive + '\'' +
                ", filePath=" + (photo == null? null : photo.getFilePath()) +
                '}';
    }
}
