package ru.botanica.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(schema = "simple_botanica", name = "plant_photos")
public class PlantPhoto {
    @Id
    @Column(name = "plant_id")
    private Long id;

    @Column(name = "file_path")
    private String filePath;

    @Override
    public String toString() {
        return "PlantPhoto{" +
                "id=" + id +
                ", filePath='" + filePath + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlantPhoto that = (PlantPhoto) o;
        return Objects.equals(id, that.id) && Objects.equals(filePath, that.filePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, filePath);
    }
}
