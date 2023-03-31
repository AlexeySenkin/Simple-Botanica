package ru.botanica.entities.plants;

import org.springframework.data.jpa.domain.Specification;

public class PlantSpecifications {
    public static Specification<Plant> nameLike(String title) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + title + "%");
    }
}
