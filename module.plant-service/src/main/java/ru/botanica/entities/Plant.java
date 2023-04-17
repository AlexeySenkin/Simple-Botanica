package ru.botanica.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

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

    @Column(nullable = false, name = "description")
    private String description;

    @Column(nullable = false, length = 1024, name = "short_description")
    private String shortDescription;

    @Column(nullable = false, name = "is_active")
    private boolean isActive;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "plant_id")
    private PlantPhoto photo;

//    TODO: EAGER - техдолг. До доработки сущностей без него выдавало ошибку на стадии инициализации, теперь - нет.
//     Проверить, все ли работает без него
    @OneToMany(mappedBy = "plant", fetch = FetchType.EAGER)
    private Set<PlantCare> cares;

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
                ", filePath=" + (photo == null ? null : photo.getFilePath()) +
                ", cares=" + (cares == null ? null : cares.toString()) +
                '}';
    }
}
