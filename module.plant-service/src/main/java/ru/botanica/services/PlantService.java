package ru.botanica.services;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.botanica.entities.plants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlantService {
    private final PlantRepository plantRepository;
    private final PlantDtoMapper plantDtoMapper;
    private final PlantDtoMap plantDtoMap;

    @PostConstruct
    private void init() {
        int bdSize = (int) plantRepository.count();
        findAll(null, PageRequest.of(0, bdSize + 1));
        for (int i = 1; i <= bdSize; i++) {
            plantDtoMap.getPlant((long) i);
            System.out.println(plantDtoMap.getPlant((long) i));
        }
    }

    public Page<PlantDto> findAll(String title, Pageable pageable) {
        Specification<Plant> specification = createSpecificationsWithFilter(title);
        Page<Plant> plantPage = plantRepository.findAll(specification, pageable);
        addingMissingPlantsToLocalBase(plantPage.map(plantDtoMapper::mapToDto));
        return plantPage.map(plantDtoMapper::mapToDtoWithIdNameShortDescAndFilePath);
    }

    private void addingMissingPlantsToLocalBase(Page<PlantDto> plantDtoPage) {
        for (PlantDto plantDto : plantDtoPage) {
            Long plantDtoId = plantDto.getId();
            if (plantDtoMap.getPlant(plantDtoId) == null || !plantDtoMap.getPlant(plantDtoId).equals(plantDto)) {
                plantDtoMap.addPlant(plantDto);
            }
        }
    }

    private Specification<Plant> createSpecificationsWithFilter(String title) {
        Specification<Plant> specification = Specification.where(null);
        if (title != null) {
            specification = specification.and(PlantSpecifications.nameLike(title));
        }
        return specification;
    }
}
