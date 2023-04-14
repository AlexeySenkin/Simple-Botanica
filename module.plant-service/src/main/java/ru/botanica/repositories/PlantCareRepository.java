package ru.botanica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.botanica.entities.plantCares.PlantCare;

@Repository
public interface PlantCareRepository extends JpaRepository<PlantCare, Long>, JpaSpecificationExecutor<PlantCare> {
}
