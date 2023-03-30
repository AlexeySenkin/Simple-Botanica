package ru.botanica.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.botanica.entities.plants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlantService {
    private final PlantRepository plantRepository;
    private final PlantDtoMapper plantDtoMapper;
    private final PlantDtoMap plantDtoMap;


    public Page<PlantDto> findAll(String title, Pageable pageable) {
//          TODO: придумать костыль получше
//        Костыль, но он улучшает поиск, добавляя все возможные варианты даже если название введено не полностью
        if (title == null) {
            return plantRepository.findAllByNameContains(title, pageable).map(plantDtoMapper::mapWithIdNameShortDescAndFilePath);
        } else {
            String resultTitle = "%" + title + "%";
            return plantRepository.findAllByNameContains(resultTitle, pageable).map(plantDtoMapper::mapWithIdNameShortDescAndFilePath);
        }
    }
}
