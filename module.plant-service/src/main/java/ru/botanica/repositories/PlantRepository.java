package ru.botanica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.botanica.entities.Plant;

import java.util.Optional;


@Repository
public interface PlantRepository extends JpaRepository<Plant, Long>, JpaSpecificationExecutor<Plant> {

    boolean existsByName(String name);

    Optional<Plant> findByName(String name);
    Optional<Plant> findById (Long id);
}
