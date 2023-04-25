package ru.botanica.dto;

import ru.botanica.entities.UserPlant;

public class UserPlantsFullDtoMapper {


    public UserPlantsFullDtoMapper() {
        throw new AssertionError("Instantiating utility class");
    }

    public static UserPlantsFullDto mapToDto(UserPlant userPlant) {
        UserPlantsFullDto dto = new UserPlantsFullDto();

        dto.setUserPlantId(userPlant.getUserPlantId());

        dto.setUserId(userPlant.getUserId());

        dto.setPlantId(userPlant.getPlantId());

        dto.setIsBanned(userPlant.getIsBanned());

        dto.setIsActive(userPlant.getIsActive());

        dto.setPlant(userPlant.getPlant());

        dto.setUserCare(userPlant.getUserCare());

        dto.setUserCareCustom(userPlant.getUserCareCustom());

        return dto;
    }
}
