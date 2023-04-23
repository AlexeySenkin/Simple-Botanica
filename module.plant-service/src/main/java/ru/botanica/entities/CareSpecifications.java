package ru.botanica.entities;

import org.springframework.data.jpa.domain.Specification;

public class CareSpecifications {
    public static Specification<Care> isActive(boolean isActive) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("isActive"), isActive));
    }
}
