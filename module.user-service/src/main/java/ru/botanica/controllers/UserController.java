package ru.botanica.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.botanica.entities.users.UserDto;
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
    public UserDto findById(@PathVariable long id) {
        return userService.findById(id);
    }

    @GetMapping("/user")
    public Long findIdByUserName(@RequestParam("username") String userName) {
        return userService.findIdByUserName(userName);
    }

}
