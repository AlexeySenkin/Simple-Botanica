package ru.botanica.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.botanica.dto.UserDto;
import ru.botanica.dto.UserDtoMapper;
import ru.botanica.entities.User;
import ru.botanica.exceptions.ServerHandleException;
import ru.botanica.repositories.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    public UserDto findById(long id)  throws Exception  {
        try {
            return UserDtoMapper.mapToDto(userRepository.findById(id).orElseThrow());
        } catch (Exception e) {
            throw new ServerHandleException("Не удалось получить пользователя по идентификатору");
        }

    }

    /**
     * Возвращает идентификатор пользователя по имени пользователя
     *
     * @param userName Имя пользователя
     * @return Идентификатор
     */
    public Long findIdByUserName(String userName)  throws Exception  {
        try {
            return userRepository.findIdByUserName(userName);
        } catch (Exception e) {
            throw new ServerHandleException("Не удалось получить идентификатор пользователя по имени пользователя");
        }

    }

    /**
     * Возвращает список всех пользователей
     *
     * @return Список пользователей
     */
    public List<UserDto> findAll()  throws Exception {
        try {
            return userRepository.findAll().stream().map(UserDtoMapper::mapToDto).collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServerHandleException("Не удалось получить список всех пользователей");
        }

    }

    /**
     *  Регистрация нового пользователя
     *
     * @param userName Имя пользователя
     * @param email email
     */
    public void registerNewUser(String userName, String email) throws Exception {
        try {
            User user = new User();
            user.setIsActive(true);
            user.setIsBanned(false);
            user.setUserName(userName);
            user.setEmail(email);
            user.setRegDate(new Date());
            userRepository.save(user);
        } catch (Exception e) {
            throw new ServerHandleException("Не удалась регистрация нового пользователя");
        }

    }
}
