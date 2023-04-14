package ru.botanica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.botanica.entities.plantCares.PlantCare;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface PlantCareRepository extends JpaRepository<PlantCare, Long>, JpaSpecificationExecutor<PlantCare> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = """
    insert into plant_care(care_id, plant_id, care_count, care_volume)
    values (:careId, :plantId, :careCount, :careVolume)
    """)
    void insertPlantCare(Long careId, Long plantId, int careCount, BigDecimal careVolume);

    @Query("select pc from PlantCare pc where pc.care.id = ?1 and pc.plant.id = ?2")
    PlantCare findByCareIdAndPlantId(Long careId, Long plantId);

    @Query("select pc from PlantCare pc where pc.care.id = ?1 and pc.plant.id = ?2")
    Optional<PlantCare> existsByCareIdAndPlantId(Long careId, Long plantId);
}
