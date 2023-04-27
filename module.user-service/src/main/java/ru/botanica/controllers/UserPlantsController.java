package ru.botanica.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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


    @GetMapping("/user_plants_full")
    public Page<UserPlantsFullDto> findUserPlantsFullByUserId(@RequestParam() long userId,
                                                              @RequestParam(required = false, defaultValue = "1") int page,
                                                              @RequestParam(required = false, defaultValue = "8") int size
    ) {
        return userPlantsService.findFullByUserId(userId, PageRequest.of(page - 1, size));
    }

    @GetMapping("/user_plants")
    public Page<UserPlantsDto> findUserPlantsByUserId(@RequestParam() long userId,
                                                      @RequestParam(required = false, defaultValue = "1") int page,
                                                      @RequestParam(required = false, defaultValue = "8") int size
    ) {
        return userPlantsService.findByUserId(userId, PageRequest.of(page - 1, size));
    }


    @GetMapping("/user_plant")
    public Optional<UserPlantsDto> findUserPlantByUserPlantId(@RequestParam() long userPlantId) {
        return userPlantsService.findPlantByUserPlantId(userPlantId);
    }

    @PostMapping("/add_user_plant")
    public ResponseEntity<?> addUserPlant(@RequestParam() long userId,
                                          @RequestParam() long plantId) {

        if (userService.findById(userId) == null) {
            return new ResponseEntity<>(new AppStatus(HttpStatus.BAD_REQUEST.value(),
                    "Пользователь с таким id не существует"), HttpStatus.BAD_REQUEST);
        }
        if (userPlantsService.findByPlantId(plantId).isEmpty()) {
            return new ResponseEntity<>(new AppStatus(HttpStatus.BAD_REQUEST.value(),
                    "Растение с таким id не существует"), HttpStatus.BAD_REQUEST);
        }

        userPlantsService.createUserPlant(userId, plantId);

        return new ResponseEntity<>(new AppStatus(HttpStatus.OK.value(),
                " Растение c id = " + plantId +
                        " добавлено пользователю с id = " + userId), HttpStatus.OK);

    }

    @PostMapping("/banned_user_plant")
    public ResponseEntity<?> bannedUserPlant(@RequestParam() long userPlantId) {
        if (userPlantsService.findByUserPlantId(userPlantId).isEmpty()) {
            return new ResponseEntity<>(new AppStatus(HttpStatus.BAD_REQUEST.value(),
                    "Растение пользователя с указанным user_plant_id не существует"), HttpStatus.BAD_REQUEST);
        }
        UserPlantsFullDto userPlantsFullDto = userPlantsService.bannedUserPlant(userPlantId);
        return new ResponseEntity<>(new AppStatus(HttpStatus.OK.value(),
                "user_plant_id = " + userPlantsFullDto.getId() +
                        " : is_banned инвертировано для растение c id = " + userPlantsFullDto.getPlantId() +
                        " пользователя с id = " + userPlantsFullDto.getUserId()), HttpStatus.OK);

    }

    @PostMapping("/active_user_plant")
    public ResponseEntity<?> activeUserPlant(@RequestParam() long userPlantId) {
        if (userPlantsService.findByUserPlantId(userPlantId).isEmpty()) {
            return new ResponseEntity<>(new AppStatus(HttpStatus.BAD_REQUEST.value(),
                    "Растение пользователя с указанным user_plant_id не существует"), HttpStatus.BAD_REQUEST);
        }
        UserPlantsFullDto userPlantsFullDto = userPlantsService.activeUserPlant(userPlantId);
        return new ResponseEntity<>(new AppStatus(HttpStatus.OK.value(),
                "user_plant_id = " + userPlantsFullDto.getId() +
                        " : is_active инвертировано для растение c id = " + userPlantsFullDto.getPlantId() +
                        " пользователя с id = " + userPlantsFullDto.getUserId()), HttpStatus.OK);

    }

    @PostMapping("/add_custom_care")
    public ResponseEntity<?> editCustomCare(@RequestBody UserCareCustomDto userCareCustomDto) {
        userPlantsService.addUserCareCustom(userCareCustomDto);
        return new ResponseEntity<>(new AppStatus(HttpStatus.OK.value(),
                "user_plant_id = " + userCareCustomDto.getUserPlantId() +
                        " : данные обновлены"), HttpStatus.OK);
    }

    @GetMapping("/user_care")
    public Page<UserCareDto> findUserCareByUserPlantId(@RequestParam() long userPlantId,
                                                       @RequestParam(required = false, defaultValue = "1") int page,
                                                       @RequestParam(required = false, defaultValue = "8") int size) {
        return userPlantsService.findUserCareByUserPlantId(userPlantId, PageRequest.of(page - 1, size));
    }

    @PostMapping("/add_user_care")
    public ResponseEntity<?> addCustomCare(@RequestParam() long userPlantId,
                                           @RequestParam() long careId) {
        if (userPlantsService.findByUserPlantId(userPlantId).isEmpty()) {
            return new ResponseEntity<>(new AppStatus(HttpStatus.BAD_REQUEST.value(),
                    "Растение пользователя с указанным user_plant_id не существует"), HttpStatus.BAD_REQUEST);
        }

        if (careRepository.findByCareId(careId).isEmpty()) {
            return new ResponseEntity<>(new AppStatus(HttpStatus.BAD_REQUEST.value(),
                    " Ухода с указанным care_id не существует"), HttpStatus.BAD_REQUEST);
        }

        userPlantsService.addUserCare(userPlantId, careId);
        return new ResponseEntity<>(new AppStatus(HttpStatus.OK.value(),
                "user_plant_id = " + userPlantId +
                        " : данные добавлены"), HttpStatus.OK);
    }

    @PostMapping("/edit_user_care")
    public ResponseEntity<?> editCustomCare(@RequestBody UserCareDto userCareDto) {
        userPlantsService.editUserCare(userCareDto);
        return new ResponseEntity<>(new AppStatus(HttpStatus.OK.value(),
                "user_plant_id = " + userCareDto.getUserPlantId() +
                        " : данные обновлены"), HttpStatus.OK);
    }
}
