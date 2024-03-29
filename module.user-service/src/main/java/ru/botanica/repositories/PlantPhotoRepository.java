package ru.botanica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import ru.botanica.entities.PlantPhoto;

@Repository
public interface PlantPhotoRepository extends JpaRepository<PlantPhoto, Long> {
    boolean existsByIdNotAndFilePath(@Nullable Long id, String filePath);
}
