package ru.botanica.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.botanica.dto.*;

import ru.botanica.entities.*;
import ru.botanica.exceptions.ServerHandleException;
import ru.botanica.repositories.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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

    private final PlantCareRepository plantCareRepository;


    /**
     * Возвращает полный список растений пользователя с кастомным уходом и журналом ухода по идентификатору
     *
     * @param userId   Идентификатор пользователя
     * @param pageable пагинация
     * @return JSON Самый полный список растений пользователя UserPlantsFullDto с пагинацией
     */
    public Page<UserPlantsFullDto> findFullByUserId(long userId, Pageable pageable) throws Exception {
        try {
            Specification<UserPlant> specification = Specification.where(null);
            specification = specification.and((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("userId"), userId)).and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"), 1));
            return userPlantsRepository.findAll(specification, pageable).map(UserPlantsFullDtoMapper::mapToDto);
        } catch (Exception e) {
            throw new ServerHandleException("Не удалось полный список растений пользователя с кастомным уходом и журналом ухода по идентификатору");
        }
    }


    /**
     * Возвращает список растений пользователя
     *
     * @param userId   Идентификатор пользователя
     * @param pageable пагинация
     * @return JSON Список растений пользователя UserPlantsDto с пагинацией
     */
    public Page<UserPlantsDto> findByUserId(long userId, Pageable pageable) throws Exception {
        try {
            Specification<UserPlant> specification = Specification.where(null);
            specification = specification.and((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("userId"), userId)).and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"), 1));
            return userPlantsRepository.findAll(specification, pageable).map(UserPlantsDtoMapper::mapToDto);
        } catch (Exception e) {
            throw new ServerHandleException("Не удалось получить список растений пользователя с кастомным уходом и журналом ухода по идентификатору");
        }
    }

    /**
     * Возвращает растение пользователя
     *
     * @param userPlantId Идентификатор
     * @return Растение пользователя UserPlantsDto
     */
    public Optional<UserPlantsDto> findPlantByUserPlantId(long userPlantId) throws Exception {
        try {
            return Optional.of(UserPlantsDtoMapper.mapToDto(userPlantsRepository.findByUserPlantId(userPlantId).orElseThrow()));
        } catch (Exception e) {
            throw new ServerHandleException("Не удалось получить растение пользователя с кастомным уходом и журналом ухода по идентификатору");
        }
    }

    /**
     * Получение журнала пользовательского ухода
     *
     * @param userPlantId Идентификатор растения пользователя
     * @param pageable    пагинация
     * @return JSON журнала ухода UserCareDto с пагинацией
     */
    public Page<UserCareDto> findUserCareByUserPlantId(long userPlantId, Pageable pageable) throws Exception {
        try {
            Specification<UserCare> specification = Specification.where(null);
            specification = specification.and((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("userPlantId"), userPlantId));
            return userCareRepository.findAll(specification, pageable).map(UserCareDtoMapper::mapToDto);
        } catch (Exception e) {
            throw new ServerHandleException("Не удалось получить журнал пользовательского ухода");
        }
    }

    /**
     * Получение растения пользователя
     *
     * @param plantId Идентификатор пользователя
     * @return responseEntity с кодом и сообщением
     */
    public Optional<Plant> findByPlantId(long plantId) throws Exception {
        try {
            return plantRepository.findById(plantId);
        } catch (Exception e) {
            throw new ServerHandleException("Не удалось получить растение пользователя");
        }
    }

    /**
     * Получение всех растений пользователя
     *
     * @param userPlantId Идентификатор пользователя
     * @return responseEntity с кодом и сообщением
     */
    public Optional<UserPlant> findByUserPlantId(long userPlantId) throws Exception {
        try {
            return userPlantsRepository.findByUserPlantId(userPlantId);
        } catch (Exception e) {
            throw new ServerHandleException("Не удалось получить список всех растений пользователя");
        }
    }

    /**
     * Добавление растения для пользователя
     *
     * @param userId  Идентификатор пользователя
     * @param plantId Идентификатор растения
     */
    @Transactional
    public void createUserPlant(long userId, long plantId) throws Exception {
        try {
            UserPlant userPlant = new UserPlant();
            userPlant.setUserId(userId);
            userPlant.setPlant(findByPlantId(plantId).orElseThrow());
            userPlant.setIsBanned(false);
            userPlant.setIsActive(true);
            userPlantsRepository.save(userPlant);

            Collection<PlantCare> plantCares = plantCareRepository.findAllByPlantId(plantId);

            Collection<UserCareCustom> userCareCustoms = new ArrayList<>();

            for (PlantCare plantCare : plantCares) {
                UserCareCustom userCareCustom = new UserCareCustom();
                userCareCustom.setUserPlantId(userPlant.getUserPlantId());
                userCareCustom.setUserCareCount(plantCare.getCareCount());
                userCareCustom.setUserCareVolume(plantCare.getCareVolume());
                userCareCustom.setCare(plantCare.getCare());
                userCareCustoms.add(userCareCustom);
            }

            userPlant.setUserCareCustom(userCareCustoms);
            userPlantsRepository.saveAndFlush(userPlant);
        } catch (Exception e) {
            throw new ServerHandleException("Не удалось добавление растения для пользователя");
        }
    }

    /**
     * Постоянно скрыть растение у пользователя (active)
     *
     * @param userPlantId Идентификатор растения пользователя
     * @return responseEntity с кодом и сообщением
     */
    @Transactional
    public UserPlantsFullDto activeUserPlant(long userPlantId) throws Exception {
        try {
            UserPlant userPlant = userPlantsRepository.findByUserPlantId(userPlantId).orElseThrow();
            userPlant.setIsActive(!userPlant.getIsActive());
            userPlantsRepository.save(userPlant);
            return UserPlantsFullDtoMapper.mapToDto(userPlant);
        } catch (Exception e) {
            throw new ServerHandleException("Не удалось удалить растения для пользователя");
        }
    }

    /**
     * Временно скрыть растение у пользователя (banned)
     *
     * @param userPlantId Идентификатор растения пользователя
     * @return responseEntity с кодом и сообщением
     */
    @Transactional
    public UserPlantsFullDto bannedUserPlant(long userPlantId) throws Exception {
        try {
            UserPlant userPlant = userPlantsRepository.findByUserPlantId(userPlantId).orElseThrow();
            userPlant.setIsBanned(!userPlant.getIsBanned());
            userPlantsRepository.save(userPlant);
            return UserPlantsFullDtoMapper.mapToDto(userPlant);
        } catch (Exception e) {
            throw new ServerHandleException("Не удалось скрыть (забанить) растения для пользователя");
        }
    }

//    public Optional<UserCareCustomDto> findByUserCareCustomId(long userCareCustomId) throws Exception  {
//        return Optional.ofNullable(UserCareCustomDtoMapper.mapToDto(userCareCustomRepository.findById(userCareCustomId).orElseThrow()));
//    }

    /**
     * Добавление (изменение) пользовательского кстомного ухода
     *
     * @param userCareCustomDto JSON кастомного ухода
     */
    @Transactional
    public void addUserCareCustom(UserCareCustomDto userCareCustomDto) throws Exception {
        try {
            userCareCustomRepository.save(UserCareCustomDtoMapper.mapToEntity(userCareCustomDto));
        } catch (Exception e) {
            throw new ServerHandleException("Не удалось добавление (изменение) пользовательского кстомного ухода");
        }
    }

    /**
     * Добавление в журнал пользовательского ухода
     *
     * @param userPlantId Идентификатор растения пользователя
     * @param careId      Идентификатор ухода
     */
    @Transactional
    public void addUserCare(Long userPlantId, Long careId) throws Exception {
        try {
            UserCareDto userCareDto = new UserCareDto();
            userCareDto.setUserPlantId(userPlantId);
            userCareDto.setEventDate(new Date());
            userCareDto.setCareVolume(100.0);
            userCareDto.setPrims(0);
            userCareDto.setCare(careRepository.findByCareId(careId).orElseThrow());
            userCareRepository.saveAndFlush(UserCareDtoMapper.mapToEntity(userCareDto));
        } catch (Exception e) {
            throw new ServerHandleException("Не удалось добавление в журнал пользовательского ухода");
        }
    }

    /**
     * Редактирование записи в журнале ухода
     *
     * @param userCareDto JSON записи журнала ухода UserCareDto
     */
    @Transactional
    public void editUserCare(UserCareDto userCareDto) throws Exception {
        try {
            userCareRepository.saveAndFlush(UserCareDtoMapper.mapToEntity(userCareDto));
        } catch (Exception e) {
            throw new ServerHandleException("Не удалось редактирование записи в журнале ухода");
        }

    }
}
