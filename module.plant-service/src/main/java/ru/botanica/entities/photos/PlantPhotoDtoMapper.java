package ru.botanica.entities.photos;

import org.springframework.stereotype.Component;

@Component
public final class PlantPhotoDtoMapper {

    public static PlantPhoto mapToEntity(PlantPhotoDto photoDto) {
        PlantPhoto photo = new PlantPhoto();
        photo.setId(photoDto.getId());
        photo.setFilePath(photoDto.getFilePath());
        return photo;
    }

    public static PlantPhotoDto mapToDto(PlantPhoto photo) {
        PlantPhotoDto photoDto = new PlantPhotoDto();
        photoDto.setId(photo.getId());
        photoDto.setFilePath(photo.getFilePath());
        return photoDto;
    }
}
