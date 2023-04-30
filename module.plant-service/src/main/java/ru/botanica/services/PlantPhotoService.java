package ru.botanica.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.botanica.entities.PlantPhoto;
import ru.botanica.repositories.PlantPhotoRepository;

import java.util.Optional;

/**
 * Сервис работы с фотографиями растений
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PlantPhotoService {
    private final PlantPhotoRepository photoRepository;

    public Optional<PlantPhoto> getByPlantId(Long id) {
        return photoRepository.findById(id);
    }

    public PlantPhoto saveOrUpdate(PlantPhoto plantPhoto) {
        return photoRepository.saveAndFlush(plantPhoto);
    }

    public PlantPhoto createPhoto(String filePath, Long id) {
        if (isPathAvailable(id, filePath)) {
            return new PlantPhoto(filePath, id);
        }
        Optional<PlantPhoto> byPlantId = getByPlantId(id);
        return byPlantId.orElse(null);
    }

    public boolean isPathAvailable(Long photoId, String filePath) {
        return !photoRepository.existsByIdNotAndFilePath(photoId, filePath);
    }

}
