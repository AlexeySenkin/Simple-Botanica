package ru.botanica.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.botanica.services.UserService;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "false")
public class UserController {
    private final UserService userService;

    /**
     * Возвращает пользователя по идентификатору
     *
     * @param id Идентификатор
     * @return Пользователь
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<?> findById(@PathVariable long id) {
        try {
            log.debug("Получение пользователя по id: {}", id);
            return ResponseEntity.ok().body(userService.findById(id));
        } catch (Exception e) {
            log.error("Ошибка получения пользователя по id: {}", id);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Возвращает идентификатор пользователя по имени пользователя
     *
     * @param userName Имя пользователя
     * @return Идентификатор
     */
    @GetMapping("/user")
    public ResponseEntity<?> findIdByUserName(@RequestParam("username") String userName) {
        try {
            log.debug("Получение идентификатора пользователя по имени: {}", userName);
            return ResponseEntity.ok().body(userService.findIdByUserName(userName));
        } catch (Exception e) {
            log.error("Ошибка идентификатора пользователя по имени: {}", userName);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
