package ru.botanica.entities.photos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantPhotoRepository extends JpaRepository<PlantPhoto, Long> {
}
