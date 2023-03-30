package ru.botanica.entities.plants;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {
    @Query(
            value = """
                        select p from Plant p
                        where (:name is null or p.name like :name)
""", countQuery = """
                        select p from Plant p
                        where (:name is null or p.name like :name)
"""
    )
    Page<Plant> findAllByNameContains(String name, Pageable pageable);
}
