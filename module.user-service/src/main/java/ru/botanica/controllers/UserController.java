package ru.botanica.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import ru.botanica.services.UserService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

}
