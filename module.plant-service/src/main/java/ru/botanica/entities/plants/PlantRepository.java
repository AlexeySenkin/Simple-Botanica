package ru.botanica.entities.plants;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface PlantRepository extends JpaRepository<Plant, Long>, JpaSpecificationExecutor<Plant> {

    @Query("select (max(p.id) + 1) from Plant p")
    Long findLastIdAvailable();

    boolean existsByName(String name);

    @Query("select p.id from Plant p where p.name like ?1")
    Long findIdByName(String name);
}
