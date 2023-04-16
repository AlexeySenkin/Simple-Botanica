package ru.botanica.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.botanica.dto.UserPlantsDto;
import ru.botanica.services.UserPlantsService;

@CrossOrigin(origins = "*", allowCredentials = "false")
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserPlantsController {

    private final UserPlantsService userPlantsService;

    @GetMapping("/user_plants/{id}")
    public UserPlantsDto findById(@PathVariable int id) {
        return userPlantsService.findById(id);
    }
}
