package ru.botanica.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.botanica.dto.AppStatus;
import ru.botanica.dto.UserPlantsDto;
import ru.botanica.dto.UserPlantsShortDto;
import ru.botanica.services.UserPlantsService;
import ru.botanica.services.UserService;


@CrossOrigin(origins = "*", allowCredentials = "false")
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserPlantsController {

    private final UserPlantsService userPlantsService;

    private final UserService userService;

    @GetMapping("/user_plants")
    public Page<UserPlantsDto> findUserPlantsByUserId(@RequestParam() int userId,
                                                      @RequestParam(required = false, defaultValue = "1") int page,
                                                      @RequestParam(required = false, defaultValue = "8") int size
    )
    // @PathVariable long id)
    {
        return userPlantsService.findAllByUserId(userId, PageRequest.of(page - 1, size));
    }


    @PostMapping("/add_user_plant")
    public ResponseEntity<?> addUserPlant(@RequestParam() long userId,
                                          @RequestParam() long plantId)
    {

        if (userService.findById(userId) == null) {
            return new ResponseEntity<>(new AppStatus(HttpStatus.BAD_REQUEST.value(), "Пользователь с таким id не существует"), HttpStatus.BAD_REQUEST);
        }
        if (userPlantsService.findByPlantId(plantId).isEmpty()) {
            return new ResponseEntity<>(new AppStatus(HttpStatus.BAD_REQUEST.value(), "Растение с таким id не существует"), HttpStatus.BAD_REQUEST);
        }

        UserPlantsShortDto userPlantsShortDto = new UserPlantsShortDto();
        userPlantsShortDto.setUserId(userId);
        userPlantsShortDto.setPlantId(plantId);
        userPlantsService.createUserPlant(userPlantsShortDto);

        return new ResponseEntity<>(new AppStatus(HttpStatus.OK.value(), " Растение c id = " + userPlantsShortDto.getPlantId() + " добавлено пользователю с id = " +  userPlantsShortDto.getUserId()), HttpStatus.OK);

    }
    @PostMapping("/banned_user_plant")
    public ResponseEntity<?>  bannedUserPlant(@RequestParam() long userPlantId)
    {
        if (userPlantsService.findByUserPlantId(userPlantId).isEmpty()) {
            return new ResponseEntity<>(new AppStatus(HttpStatus.BAD_REQUEST.value(), "Растение пользователя с указанным user_plant_id не существует"), HttpStatus.BAD_REQUEST);
        }
        UserPlantsShortDto userPlantsShortDto = new UserPlantsShortDto();
        userPlantsShortDto.setUserPlantId(userPlantId);
        userPlantsService.bannedUserPlant(userPlantsShortDto);
        return new ResponseEntity<>(new AppStatus(HttpStatus.OK.value(), " is_banned инвертировано для растение c id = " + userPlantsShortDto.getPlantId() + " пользователя с id = " +  userPlantsShortDto.getUserId()), HttpStatus.OK);

    }

    @PostMapping("/active_user_plant")
    public ResponseEntity<?> activeUserPlant(@RequestParam() long userPlantId)
    {
        if (userPlantsService.findByUserPlantId(userPlantId).isEmpty()) {
            return new ResponseEntity<>(new AppStatus(HttpStatus.BAD_REQUEST.value(), "Растение пользователя с указанным user_plant_ не существует"), HttpStatus.BAD_REQUEST);
        }
        UserPlantsShortDto userPlantsShortDto = new UserPlantsShortDto();
        userPlantsShortDto.setUserPlantId(userPlantId);
        userPlantsService.activeUserPlant(userPlantsShortDto);
        return new ResponseEntity<>(new AppStatus(HttpStatus.OK.value(), "is_sctive инвертировано для растение c id = " + userPlantsShortDto.getPlantId() + " пользователя с id = " +  userPlantsShortDto.getUserId()), HttpStatus.OK);

    }


}
