package ru.botanica.entities.photos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

//  TODO: !!!добавление этой сущности вызвало уничтожение данных в таблице plant_photos при инициализации приложения. ИСПРАВИТЬ!!!
@Entity
@Getter
@Setter
@Table(schema = "simple_botanica", name = "plant_photos")
public class PlantPhoto {
    @Id
    @Column(name = "plant_id")
    private Long id;

    @Column(name = "file_path")
    private String filePath;

    public PlantPhoto(String filePath, Long id) {
        this.filePath = filePath;
        this.id = id;
    }

    public PlantPhoto() {
    }

    @Override
    public String toString() {
        return "PlantPhoto{" +
                "id=" + id +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
