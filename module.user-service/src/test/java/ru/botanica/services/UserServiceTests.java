package ru.botanica.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.botanica.entities.users.User;
import ru.botanica.entities.users.UserDto;
import ru.botanica.entities.users.UserRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {UserService.class})
@ExtendWith(MockitoExtension.class)

public class UserServiceTests {

    @MockBean
    private UserRepository userRepository;
    @Autowired
    private UserService userService;


    /**
     * Тест возвращения пользователя по идентификатору
     * <p>
     * Кейс в случае существования пользователя с таким идентификатором.
     */
    @Test
    void testFindById() {
        int id = 1;

        User user = new User();

        user.setUserId(1);
        user.setFirstName("first_name");
        user.setLastName("last_name");
        user.setEmail("email");
        user.setPhoneNumber("phone");
        user.setAddress("address");
        user.setRegDate(Date.valueOf(LocalDate.of(2020, Month.JANUARY, 1)));
        user.setIsBanned(0);
        user.setIsActive(1);
        user.setUserName("user_name");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        UserDto userDto = userService.findById(id);

        assertAll(
                () -> assertEquals(user.getUserId(), userDto.getUserId()),
                () -> assertEquals(user.getFirstName(), userDto.getFirstName()),
                () -> assertEquals(user.getLastName(), userDto.getLastName()),
                () -> assertEquals(user.getEmail(), userDto.getEmail()),
                () -> assertEquals(user.getPhoneNumber(), userDto.getPhoneNumber()),
                () -> assertEquals(user.getAddress(), userDto.getAddress()),
                () -> assertEquals(user.getRegDate(), userDto.getRegDate()),
                () -> assertEquals(user.getIsBanned(), userDto.getIsBanned()),
                () -> assertEquals(user.getIsActive(), userDto.getIsActive()),
                () -> assertEquals(user.getUserName(), userDto.getUserName())
        );
    }

    /**
     * Тест возвращения пользователя по идентификатору
     * <p>
     * Кейс в случае отсутствия пользователя с таким идентификатором.
     */
    @Test
    void testFindByIdWhenIllegalId() {
        int id = 1;

        assertThrows(NoSuchElementException.class, () -> userService.findById(id));
    }

}
