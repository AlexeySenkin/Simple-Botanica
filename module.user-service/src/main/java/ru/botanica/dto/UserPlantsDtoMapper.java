package ru.botanica.dto;

import ru.botanica.entities.UserPlant;

public class UserPlantsDtoMapper {


    public UserPlantsDtoMapper() {
        throw new AssertionError("Instantiating utility class");
    }

    public static UserPlantsDto mapToDto(UserPlant userPlant) {
        UserPlantsDto dto = new UserPlantsDto();

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
