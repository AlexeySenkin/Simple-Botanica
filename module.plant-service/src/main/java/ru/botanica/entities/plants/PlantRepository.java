package ru.botanica.entities.plants;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface PlantRepository extends JpaRepository<Plant, Long>, JpaSpecificationExecutor<Plant> {
    @Transactional
    @Modifying
    @Query("""
            update Plant p set p.name = ?1, p.family = ?2, p.genus = ?3,
             p.description = ?4, p.shortDescription = ?5, p.isActive = ?6
            where p.id = ?7""")
    void updateById(@NonNull String name, @NonNull String family,
                    @NonNull String genus, @NonNull String description,
                    @NonNull String shortDescription, @NonNull boolean isActive, @NonNull Long id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = """
            insert into plants (name, family, genus, description, short_description, is_active)
            values (:name, :family, :genus, :description, :shortDescription, :isActive)
            """)
    void insertProduct(@NonNull String name, @NonNull String family,
                    @NonNull String genus, @NonNull String description,
                    @NonNull String shortDescription, @NonNull boolean isActive);


    boolean existsByName(String name);

    @Query("select p.id from Plant p where p.name like ?1")
    Long findIdByName(String name);
}
