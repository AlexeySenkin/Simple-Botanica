package ru.botanica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.botanica.entities.PlantCare;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlantCareRepository extends JpaRepository<PlantCare, Long>, JpaSpecificationExecutor<PlantCare> {
    @Query("select pc from PlantCare pc where pc.care.id = ?1 and pc.plant.id = ?2")
    Optional<PlantCare> findByCareIdAndPlantId(Long careId, Long plantId);
    List<PlantCare> deleteByPlantId(@NonNull Long id);

    @Query("select pc from PlantCare pc where pc.plant.id = ?1")
    List<PlantCare> findAllByPlantId(Long plantId);
}
