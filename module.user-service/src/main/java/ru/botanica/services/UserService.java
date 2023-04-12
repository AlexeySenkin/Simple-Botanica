package ru.botanica.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.botanica.entities.users.UserDto;
import ru.botanica.entities.users.UserDtoMapper;
import ru.botanica.entities.users.UserRepository;

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



}
