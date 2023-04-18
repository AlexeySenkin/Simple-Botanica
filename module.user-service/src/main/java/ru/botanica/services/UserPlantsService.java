package ru.botanica.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.botanica.dto.UserPlantsDtoMapper;
import ru.botanica.dto.UserPlantsDto;

import ru.botanica.entities.UserPlant;
import ru.botanica.repositories.UserPlantsRepository;



@Service
@RequiredArgsConstructor
@Slf4j
public class UserPlantsService {
    private final UserPlantsRepository userPlantsRepository;

    public Page<UserPlantsDto> findAllByUserId(long userId, Pageable pageable) {
        Specification<UserPlant> specification = Specification.where(null);
        specification = specification
                .and((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("userId"), userId))
                .and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"), 1));
        return userPlantsRepository.findAll(specification, pageable).map(UserPlantsDtoMapper::mapToDto);
    }



}
