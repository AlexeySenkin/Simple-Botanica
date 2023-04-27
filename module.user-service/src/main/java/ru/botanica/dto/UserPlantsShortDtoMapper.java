package ru.botanica.dto;

import ru.botanica.entities.UserPlant;

public class UserPlantsShortDtoMapper {

    public UserPlantsShortDtoMapper() {
        throw new AssertionError("Instantiating utility class");
    }

    public static UserPlantsShortDto mapToDto(UserPlant userPlant) {
        UserPlantsShortDto dto = new UserPlantsShortDto();

        dto.setUserPlantId(userPlant.getUserPlantId());

        dto.setUserId(userPlant.getUserId());

        dto.setPlantId(userPlant.getPlant().getId());

        dto.setIsBanned(userPlant.getIsBanned());

        dto.setIsActive(userPlant.getIsActive());

        return dto;
    }
}
