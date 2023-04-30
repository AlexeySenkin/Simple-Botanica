package ru.botanica.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.botanica.dto.*;
import ru.botanica.repositories.CareRepository;
import ru.botanica.services.UserPlantsService;
import ru.botanica.services.UserService;

import java.util.Optional;


@CrossOrigin(origins = "*", allowCredentials = "false")
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserPlantsController {
    private final CareRepository careRepository;

    private final UserPlantsService userPlantsService;

    private final UserService userService;

    /**
     * Возвращает полный список растений пользователя с кастомным уходом и журналом ухода по идентификатору
     *
     * @param userId Идентификатор пользователя
     * @param page   Номер страницы
     * @param size   Размер страницы
     * @return JSON Самый полный список растений пользователя UserPlantsFullDto с пагинацией
     */
    @GetMapping("/user_plants_full")
    public Page<UserPlantsFullDto> findUserPlantsFullByUserId(@RequestParam() long userId,
                                                              @RequestParam(required = false, defaultValue = "1") int page,
                                                              @RequestParam(required = false, defaultValue = "8") int size
    ) throws Exception {
        log.debug("Получение растений пользователя FULL по userId: {}", userId);
        Page<UserPlantsFullDto> userPlantsFullDtos = userPlantsService.findFullByUserId(userId, PageRequest.of(page - 1, size));
        log.debug("Получение растений пользователя FULL по userId: {} выполнено", userId);
        return userPlantsFullDtos;
    }

    /**
     * Возвращает список растений пользователя
     *
     * @param userId Идентификатор пользователя
     * @param page   Номер страницы
     * @param size   Размер страницы
     * @return JSON Список растений пользователя UserPlantsDto с пагинацией
     */
    @GetMapping("/user_plants")
    public Page<UserPlantsDto> findUserPlantsByUserId(@RequestParam() long userId,
                                                      @RequestParam(required = false, defaultValue = "1") int page,
                                                      @RequestParam(required = false, defaultValue = "8") int size
    ) throws Exception {
        log.debug("Получение списка растений пользователя по userId: {}", userId);
        Page<UserPlantsDto> userPlantsDtos = userPlantsService.findByUserId(userId, PageRequest.of(page - 1, size));
        log.debug("Получение списка растений пользователя по userId: {} выполнено", userId);

        return userPlantsDtos;
    }

    /**
     * Возвращает растение пользователя
     *
     * @param userPlantId Идентификатор
     * @return Растение пользователя UserPlantsDto
     */
    @GetMapping("/user_plant")
    public Optional<UserPlantsDto> findUserPlantByUserPlantId(@RequestParam() long userPlantId) throws Exception {
        log.debug("Получение растения пользователя по userPlantId: {}", userPlantId);
        Optional<UserPlantsDto> userPlantsDto = userPlantsService.findPlantByUserPlantId(userPlantId);
        log.debug("Получение растения пользователя по userPlantId : {} выполнено", userPlantId);
        return userPlantsDto;
    }

    /**
     * Добавление растения для пользователя
     *
     * @param userId  Идентификатор пользователя
     * @param plantId Идентификатор растения
     * @return responseEntity с кодом и сообщением
     */
    @PostMapping("/add_user_plant")
    public ResponseEntity<?> addUserPlant(@RequestParam() long userId,
                                          @RequestParam() long plantId) throws Exception {
        log.debug("Добавление растения пользователя по userId : {}  и plantId: {}", userId, plantId);

        if (userService.findById(userId) == null) {
            return new ResponseEntity<>(new AppStatus(HttpStatus.BAD_REQUEST.value(),
                    "Пользователь с таким id не существует"), HttpStatus.BAD_REQUEST);
        }
        if (userPlantsService.findByPlantId(plantId).isEmpty()) {
            return new ResponseEntity<>(new AppStatus(HttpStatus.BAD_REQUEST.value(),
                    "Растение с таким id не существует"), HttpStatus.BAD_REQUEST);
        }

        userPlantsService.createUserPlant(userId, plantId);
        log.debug("Добавление растения пользователя userId : {} и plantId: {} выполнено", userId, plantId);

        return new ResponseEntity<>(new AppStatus(HttpStatus.OK.value(),
                " Растение c id = " + plantId +
                        " добавлено пользователю с id = " + userId), HttpStatus.OK);

    }

    /**
     * Временно скрыть растение у пользователя (banned)
     *
     * @param userPlantId Идентификатор растения пользователя
     * @return responseEntity с кодом и сообщением
     */
    @PostMapping("/banned_user_plant")
    public ResponseEntity<?> bannedUserPlant(@RequestParam() long userPlantId) throws Exception {

        log.debug("Скрытие растения пользователя по userPlantId : {}", userPlantId);

        if (userPlantsService.findByUserPlantId(userPlantId).isEmpty()) {
            return new ResponseEntity<>(new AppStatus(HttpStatus.BAD_REQUEST.value(),
                    "Растение пользователя с указанным user_plant_id не существует"), HttpStatus.BAD_REQUEST);
        }

        UserPlantsFullDto userPlantsFullDto = userPlantsService.bannedUserPlant(userPlantId);
        log.debug("Скрытие растения пользователя по userPlantId : {} выполнено", userPlantId);

        return new ResponseEntity<>(new AppStatus(HttpStatus.OK.value(),
                "user_plant_id = " + userPlantsFullDto.getId() +
                        " : is_banned инвертировано для растение c id = " + userPlantsFullDto.getPlantId() +
                        " пользователя с id = " + userPlantsFullDto.getUserId()), HttpStatus.OK);

    }

    /**
     * Постоянно скрыть растение у пользователя (active)
     *
     * @param userPlantId Идентификатор растения пользователя
     * @return responseEntity с кодом и сообщением
     */
    @PostMapping("/active_user_plant")
    public ResponseEntity<?> activeUserPlant(@RequestParam() long userPlantId) throws Exception {
        log.debug("Удаление растения пользователя по userPlantId: {}", userPlantId);

        if (userPlantsService.findByUserPlantId(userPlantId).isEmpty()) {
            return new ResponseEntity<>(new AppStatus(HttpStatus.BAD_REQUEST.value(),
                    "Растение пользователя с указанным user_plant_id не существует"), HttpStatus.BAD_REQUEST);
        }

        UserPlantsFullDto userPlantsFullDto = userPlantsService.activeUserPlant(userPlantId);
        log.debug("Удаление растения пользователя по userPlantId: {} выполнено", userPlantId);

        return new ResponseEntity<>(new AppStatus(HttpStatus.OK.value(),
                "user_plant_id = " + userPlantsFullDto.getId() +
                        " : is_active инвертировано для растение c id = " + userPlantsFullDto.getPlantId() +
                        " пользователя с id = " + userPlantsFullDto.getUserId()), HttpStatus.OK);

    }

    /**
     * Добавление (изменение) пользовательского кстомного ухода
     *
     * @param userCareCustomDto JSON кастомного ухода
     * @return responseEntity с кодом и сообщением
     */
    @PostMapping("/add_custom_care")
    public ResponseEntity<?> editCustomCare(@RequestBody UserCareCustomDto userCareCustomDto) throws Exception {

        log.debug("Добавление (изменение) пользовательского кстомного ухода по userCareCustomDto: {}", userCareCustomDto);
        userPlantsService.addUserCareCustom(userCareCustomDto);
        log.debug("Добавление (изменение) пользовательского кстомного уходао по userCareCustomDto: {} выполнено", userCareCustomDto);

        return new ResponseEntity<>(new AppStatus(HttpStatus.OK.value(),
                "user_plant_id = " + userCareCustomDto.getUserPlantId() +
                        " : данные обновлены"), HttpStatus.OK);
    }

    /**
     * Получение журнала пользовательского ухода
     *
     * @param userPlantId Идентификатор растения пользователя
     * @param page        Номер страницы
     * @param size        Размер страницы
     * @return JSON журнала ухода UserCareDto с пагинацией
     */
    @GetMapping("/user_care")
    public Page<UserCareDto> findUserCareByUserPlantId(@RequestParam() long userPlantId,
                                                       @RequestParam(required = false, defaultValue = "1") int page,
                                                       @RequestParam(required = false, defaultValue = "8") int size
    ) throws Exception {

        log.debug("Получение журнала пользовательского ухода по userPlantId: {}", userPlantId);
        Page<UserCareDto> userCareDtos = userPlantsService.findUserCareByUserPlantId(userPlantId,
                PageRequest.of(page - 1, size, Sort.by("eventDate").descending()));
        log.debug("Получение журнала пользовательского ухода по userPlantId: {} выполнено", userPlantId);

        return userCareDtos;
    }

    /**
     * Добавление в журнал пользовательского ухода
     *
     * @param userPlantId Идентификатор растения пользователя
     * @param careId      Идентификатор ухода
     * @return responseEntity с кодом и сообщением
     */
    @PostMapping("/add_user_care")
    public ResponseEntity<?> addCustomCare(@RequestParam() long userPlantId,
                                           @RequestParam() long careId) throws Exception {
        log.debug("Добавление в журнал пользовательского ухода по userPlantId: {} и careId: {} ", userPlantId, careId);

        if (userPlantsService.findByUserPlantId(userPlantId).isEmpty()) {
            return new ResponseEntity<>(new AppStatus(HttpStatus.BAD_REQUEST.value(),
                    "Растение пользователя с указанным user_plant_id не существует"), HttpStatus.BAD_REQUEST);
        }

        if (careRepository.findByCareId(careId).isEmpty()) {
            return new ResponseEntity<>(new AppStatus(HttpStatus.BAD_REQUEST.value(),
                    " Ухода с указанным care_id не существует"), HttpStatus.BAD_REQUEST);
        }

        userPlantsService.addUserCare(userPlantId, careId);

        log.debug("Добавление в журнал пользовательского ухода по userPlantId: {} и careId: {} выполнено", userPlantId, careId);

        return new ResponseEntity<>(new AppStatus(HttpStatus.OK.value(),
                "user_plant_id = " + userPlantId +
                        " : данные добавлены"), HttpStatus.OK);
    }

    /**
     * Редактирование записи в журнале ухода
     *
     * @param userCareDto JSON записи журнала ухода UserCareDto
     * @return responseEntity с кодом и сообщением
     */
    @PostMapping("/edit_user_care")
    public ResponseEntity<?> editCustomCare(@RequestBody UserCareDto userCareDto) throws Exception {

        log.debug("Редактирование записи в журнале ухода по JSON userCareDto: {} ", userCareDto);
        userPlantsService.editUserCare(userCareDto);
        log.debug("Редактирование записи в журнале ухода по JSON userCareDto: {} выполнено", userCareDto);

        return new ResponseEntity<>(new AppStatus(HttpStatus.OK.value(),
                "user_plant_id = " + userCareDto.getUserPlantId() +
                        " : данные обновлены"), HttpStatus.OK);
    }
}
