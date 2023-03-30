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
                        select * from plants p
                        where (:name is null or p.name like :name)
""", countQuery = """
                        select * from plants p
                        where (:name is null or p.name like :name)
""", nativeQuery = true
    )
    Page<Plant> findAllByNameContains(String name, Pageable pageable);
}
