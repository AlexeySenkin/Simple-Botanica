package ru.botanica.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import ru.botanica.dto.UserPlantsDto;
import ru.botanica.services.UserPlantsService;



@CrossOrigin(origins = "*", allowCredentials = "false")
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserPlantsController {

    private final UserPlantsService userPlantsService;

    @GetMapping("/user_plants")
    public Page<UserPlantsDto> findUserPlantsByUserId(@RequestParam() int userId,
                                                      @RequestParam(required = false, defaultValue = "1") int page,
                                                      @RequestParam(required = false, defaultValue = "8") int size
                                                      )
                                                     // @PathVariable long id)
    {
        return userPlantsService.findAllByUserId(userId, PageRequest.of(page - 1, size));
    }
}
