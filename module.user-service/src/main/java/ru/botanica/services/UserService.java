package ru.botanica.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.botanica.dto.UserDto;
import ru.botanica.dto.UserDtoMapper;
import ru.botanica.entities.User;
import ru.botanica.repositories.UserRepository;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    /**
     * Возвращает пользователя по идентификатору
     *
     * @param id Идентификатор
     * @return Пользователь
     */
    public UserDto findById(long id) {
        return UserDtoMapper.mapToDto(userRepository.findById(id).orElseThrow());
    }

    /**
     * Возвращает идентификатор пользователя по имени пользователя
     *
     * @param userName Имя пользователя
     * @return Идентификатор
     */
    public Long findIdByUserName(String userName) {
        return userRepository.findIdByUserName(userName);
    }


    public void registerNewUser(String userName, String email){
        User user = new User();
        user.setIsActive(true);
        user.setIsBanned(false);
        user.setUserName(userName);
        user.setEmail(email);
        user.setRegDate(new Date());
        userRepository.save(user);
    }
}
