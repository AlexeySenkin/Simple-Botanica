package ru.botanica.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.botanica.dto.UserDto;
import ru.botanica.services.UserService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    /**
     * Возвращает пользователя по идентификатору
     *
     * @param id Идентификатор
     * @return Пользователь
     */
    @GetMapping("/user/{id}")
    public UserDto findById(@PathVariable int id) {
        return userService.findById(id);
    }



}
