package ru.botanica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.botanica.entities.PlantCare;

import java.util.List;

@Repository
public interface PlantCareRepository extends JpaRepository<PlantCare, Long>, JpaSpecificationExecutor<PlantCare> {
    List<PlantCare> deleteByPlantId(@NonNull Long id);

    @Query("select pc from PlantCare pc where pc.plant.id = ?1")
    List<PlantCare> findAllByPlantId(Long plantId);
}
