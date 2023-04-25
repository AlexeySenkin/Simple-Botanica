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

    /**
     * Сохраняет фото в БД
     *
     * @param plantId  идентификатор растения
     * @param filePath путь к фото
     * @return фото из БД
     */
    public PlantPhoto saveOrUpdate(Long plantId, String filePath) {
        PlantPhoto plantPhoto = new PlantPhoto();
        plantPhoto.setId(plantId);
        Optional<PlantPhoto> photoById = getByPlantId(plantId);
        if (photoById.isPresent() && photoById.get().getFilePath().equals(filePath)) {
            /**
             * Возвращает уже существующее фото при существовании такой же фотографии для растения в БД
             */
            return photoById.get();
        } else if (photoById.isPresent() && isPathAvailable(photoById.get().getId(), filePath)) {
            /**
             * Возвращает новое фото, если путь свободен и фотография в БД существует, но путь к ней отличается
             */
            plantPhoto.setFilePath(filePath);
            return photoRepository.saveAndFlush(plantPhoto);
        } else if (photoById.isPresent() && !isPathAvailable(photoById.get().getId(), filePath)) {
            /**
             * Возвращает старую фотографию, не сумев записать новую из-за занятого пути
             */
//            TODO: определиться, как уведомлять об ошибках фронт
            log.warn("Данный путь к фотографии занят, filePath= {} для id= {}, но фото уже существует, filePath= {}",
                    filePath, photoById.get().getId(), photoById.get().getFilePath());
            return photoById.get();
        }
        if (photoById.isEmpty() && isPathAvailable(null, filePath)) {
            plantPhoto.setFilePath(filePath);
            return photoRepository.saveAndFlush(plantPhoto);
        } else if (photoById.isEmpty() && !isPathAvailable(null, filePath)) {
            plantPhoto.setFilePath(null);
            log.warn("Путь к фото был занят, фото путь отсутствует изначально, присвоено null, filePath= {}, id= {}",
                    filePath, plantId);
            return photoRepository.saveAndFlush(plantPhoto);
        }
        log.error("Неожиданная необработанная ошибка, filePath= {}, id= {}", filePath, plantId);
        plantPhoto.setFilePath(filePath);
        return null;
    }

    public PlantPhoto createPhoto(String filePath, Long id) {
        if (isPathAvailable(id, filePath)) {
            return new PlantPhoto(filePath, id);
        }
        Optional<PlantPhoto> byPlantId = getByPlantId(id);
        if (byPlantId.isPresent()) {
            return byPlantId.get();
        }
        return null;
    }

    public boolean isPathAvailable(Long photoId, String filePath) {
        return !photoRepository.existsByIdNotAndFilePath(photoId, filePath);
    }

}
