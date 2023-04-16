package ru.botanica.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.botanica.dto.UserDto;
import ru.botanica.dto.UserDtoMapper;
import ru.botanica.repositories.UserRepository;

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
    public UserDto findById(int id) {
        return UserDtoMapper.mapToDto(userRepository.findById(id).orElseThrow());
    }

}
