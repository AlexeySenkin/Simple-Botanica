package ru.botanica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.botanica.entities.Care;

@Repository
public interface CareRepository extends JpaRepository<Care, Integer> , JpaSpecificationExecutor<Care> {
}
