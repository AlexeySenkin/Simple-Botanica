package ru.botanica.entities;

import org.springframework.data.jpa.domain.Specification;

public class PlantSpecifications {
    public static Specification<Plant> nameLike(String title) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + title + "%");
    }

    public static Specification<Plant> isActive(boolean isActive) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("isActive"), isActive);
    }
}
