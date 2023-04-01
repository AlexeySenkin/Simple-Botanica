package ru.botanica.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.botanica.entities.plants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlantService {
    private final PlantRepository plantRepository;

    public Page<PlantDto> findAll(String title, Pageable pageable) {
        Specification<Plant> specification = createSpecificationsWithFilter(title);
        Page<Plant> plantPage = plantRepository.findAll(specification, pageable);
        return plantPage.map(PlantDtoMapper::mapToDtoWithIdNameShortDescAndFilePath);
    }

    private Specification<Plant> createSpecificationsWithFilter(String title) {
        Specification<Plant> specification = Specification.where(null);
        if (title != null) {
            specification = specification.and(PlantSpecifications.nameLike(title));
        }
        return specification;
    }

    /**
     * Возвращает растение по идентификатору
     *
     * @param id Идентификатор
     * @return Растение
     */
    public PlantDto findById(long id) {
        Plant plant = plantRepository.findById(id).orElseThrow();
        return PlantDtoMapper.mapToDto(plant);
    }

}