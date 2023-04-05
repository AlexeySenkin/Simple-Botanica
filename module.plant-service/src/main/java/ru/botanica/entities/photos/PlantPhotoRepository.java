package ru.botanica.entities.photos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PlantPhotoRepository extends JpaRepository<PlantPhoto, Long> {
    @Transactional
    @Modifying
    @Query("update PlantPhoto p set p.filePath = ?1 where p.id = ?2")
    void updateById(@Nullable String filePath, @NonNull Long id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value =
            "insert into plant_photos values (:id, :filePath)")
    void insertById(@NonNull String filePath, @NonNull Long id);
}
