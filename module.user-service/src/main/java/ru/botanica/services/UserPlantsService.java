package ru.botanica.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.botanica.dto.*;

import ru.botanica.entities.Plant;
import ru.botanica.entities.UserCare;
import ru.botanica.entities.UserPlant;
import ru.botanica.repositories.*;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserPlantsService {
    private final UserPlantsRepository userPlantsRepository;

    private final PlantRepository plantRepository;

    private final UserCareCustomRepository userCareCustomRepository;

    private final CareRepository careRepository;

    private final UserCareRepository userCareRepository;

    public Page<UserPlantsFullDto> findFullByUserId(long userId, Pageable pageable) {
        Specification<UserPlant> specification = Specification.where(null);
        specification = specification
                .and((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("userId"), userId))
                .and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"), 1));

        return userPlantsRepository.findAll(specification, pageable).map(UserPlantsFullDtoMapper::mapToDto);
    }

    public Page<UserPlantsDto> findByUserId(long userId, Pageable pageable) {
        Specification<UserPlant> specification = Specification.where(null);
        specification = specification
                .and((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("userId"), userId))
                .and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"), 1));
        return userPlantsRepository.findAll(specification, pageable).map(UserPlantsDtoMapper::mapToDto);
    }

    public Optional<UserPlantsDto> findPlantByUserPlantId(long userPlantId) {
        return Optional.ofNullable(UserPlantsDtoMapper.mapToDto(userPlantsRepository.findByUserPlantId(userPlantId).orElseThrow()));
    }

    public Page<UserCareDto> findUserCareByUserPlantId(long userPlantId, Pageable pageable) {
        Specification<UserCare> specification = Specification.where(null);
        specification = specification
                .and((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("userPlantId"), userPlantId));
        return userCareRepository.findAll(specification, pageable).map(UserCareDtoMapper::mapToDto);
    }

    public Optional<Plant> findByPlantId(long plantId) {
        return plantRepository.findById(plantId);
    }

    public Optional<UserPlant> findByUserPlantId(long userPlantId) {
        return userPlantsRepository.findByUserPlantId(userPlantId);
    }

    @Transactional
    public void createUserPlant(long userId, long plantId){
        UserPlant userPlant = new UserPlant();
        userPlant.setUserId(userId);
        userPlant.setPlant(findByPlantId(plantId).orElseThrow());
        userPlant.setIsBanned(false);
        userPlant.setIsActive(true);
        userPlantsRepository.save(userPlant);
    }

    @Transactional
    public UserPlantsFullDto activeUserPlant(long userPlantsId){
        UserPlant userPlant = userPlantsRepository.findByUserPlantId(userPlantsId).orElseThrow();
        userPlant.setIsActive(!userPlant.getIsActive());
        userPlantsRepository.save(userPlant);
        return UserPlantsFullDtoMapper.mapToDto(userPlant);
    }

    @Transactional
    public UserPlantsFullDto bannedUserPlant(long userPlantsId){
        UserPlant userPlant = userPlantsRepository.findByUserPlantId(userPlantsId).orElseThrow();
        userPlant.setIsBanned(!userPlant.getIsBanned());
        userPlantsRepository.save(userPlant);
        return UserPlantsFullDtoMapper.mapToDto(userPlant);
    }

    public Optional<UserCareCustomDto> findByUserCareCustomId(long userCareCustomId) {
        return Optional.ofNullable(UserCareCustomDtoMapper.mapToDto(userCareCustomRepository.findById(userCareCustomId).orElseThrow()));
    }

    @Transactional
    public void addUserCareCustom(UserCareCustomDto userCareCustomDto){
        userCareCustomRepository.save(UserCareCustomDtoMapper.mapToEntity(userCareCustomDto));
    }

    @Transactional
    public void addUserCare(UserCareDto userCareDto){
        userCareRepository.saveAndFlush(UserCareDtoMapper.mapToEntity(userCareDto));
    }
}
