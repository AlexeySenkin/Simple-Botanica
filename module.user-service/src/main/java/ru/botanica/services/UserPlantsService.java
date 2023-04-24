package ru.botanica.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.botanica.dto.UserPlantsDtoMapper;
import ru.botanica.dto.UserPlantsDto;

import ru.botanica.dto.UserPlantsShortDto;
import ru.botanica.entities.Plant;
import ru.botanica.entities.UserPlant;
import ru.botanica.repositories.PlantRepository;
import ru.botanica.repositories.UserPlantsRepository;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserPlantsService {
    private final UserPlantsRepository userPlantsRepository;

    private final PlantRepository plantRepository;

    public Page<UserPlantsDto> findAllByUserId(long userId, Pageable pageable) {
        Specification<UserPlant> specification = Specification.where(null);
        specification = specification
                .and((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("userId"), userId))
                .and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"), 1));
        return userPlantsRepository.findAll(specification, pageable).map(UserPlantsDtoMapper::mapToDto);
    }

    public Optional<Plant> findByPlantId(long plantId) {
        return plantRepository.findById(plantId);
    }

    public Optional<UserPlant> findByUserPlantId(long userPlantId) {
        return userPlantsRepository.findByUserPlantId(userPlantId);
    }

    @Transactional
    public void createUserPlant(UserPlantsShortDto userPlantsShortDto){
        UserPlant userPlant = new UserPlant();
        userPlant.setUserId(userPlantsShortDto.getUserId());
        userPlant.setPlantId(userPlantsShortDto.getPlantId());
        userPlant.setIsBanned(false);
        userPlant.setIsActive(true);
        userPlantsRepository.save(userPlant);
    }

    @Transactional
    public void activeUserPlant(UserPlantsShortDto userPlantsShortDto){
        UserPlant userPlant = userPlantsRepository.findByUserPlantId(userPlantsShortDto.getUserPlantId()).orElseThrow();
        userPlant.setIsActive(!userPlant.getIsActive());
        userPlantsRepository.save(userPlant);
    }

    @Transactional
    public void bannedUserPlant(UserPlantsShortDto userPlantsShortDto){
        UserPlant userPlant = userPlantsRepository.findByUserPlantId(userPlantsShortDto.getUserPlantId()).orElseThrow();
        userPlant.setIsBanned(!userPlant.getIsBanned());
        userPlantsRepository.save(userPlant);
    }
}
