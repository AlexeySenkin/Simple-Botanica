package ru.botanica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.botanica.entities.photos.PlantPhoto;

@Repository
public interface PlantPhotoRepository extends JpaRepository<PlantPhoto, Long> {
}
