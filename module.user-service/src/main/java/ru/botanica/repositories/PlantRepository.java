package ru.botanica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.botanica.entities.Plant;

import java.util.Optional;


@Repository
public interface PlantRepository extends JpaRepository<Plant, Integer>, JpaSpecificationExecutor<Plant> {

    Optional<Plant> findById(Long Id);
}
