package ru.botanica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.botanica.entities.UserPlants;

@Repository
public interface UserPlantsRepository extends JpaRepository<UserPlants, Integer>, JpaSpecificationExecutor<UserPlants> {
}
