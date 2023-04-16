package ru.botanica.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.botanica.dto.UserPlantsDtoMapper;
import ru.botanica.dto.UserPlantsDto;
import ru.botanica.repositories.UserPlantsRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserPlantsService {
    private final UserPlantsRepository userPlantsRepository;

    public UserPlantsDto findById(int id) {
        return UserPlantsDtoMapper.mapToDto(userPlantsRepository.findById(id).orElseThrow());
    }

}
