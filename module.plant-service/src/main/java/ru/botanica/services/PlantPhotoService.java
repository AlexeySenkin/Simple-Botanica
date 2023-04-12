package ru.botanica.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.botanica.entities.photos.PlantPhoto;
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

    public PlantPhoto saveOrUpdate(Long plantId, String filePath) {
        PlantPhoto plantPhoto = new PlantPhoto();
        Optional<PlantPhoto> plantPhotoOpt = getByPlantId(plantId);
        if (plantPhotoOpt.isPresent()) {
            plantPhoto = plantPhotoOpt.get();
        } else {
            plantPhoto.setId(plantId);
            plantPhoto.setFilePath(filePath);
        }

        return photoRepository.saveAndFlush(plantPhoto);
    }

}
