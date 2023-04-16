package ru.botanica.dto;

import ru.botanica.entities.UserPlants;

public class UserPlantsDtoMapper {


    public UserPlantsDtoMapper() {
        throw new AssertionError("Instantiating utility class");
    }

    public static UserPlantsDto mapToDto(UserPlants userPlants) {
        UserPlantsDto dto = new UserPlantsDto();

        dto.setUserPlantId(userPlants.getUserPlantId());

        dto.setUserId(userPlants.getUserId());

        dto.setPlantId(userPlants.getPlantId());

        dto.setIsBanned(userPlants.getIsBanned());

        dto.setIsActive(userPlants.getIsActive());

        dto.setPlant(userPlants.getPlant());

        dto.setUserCare(userPlants.getUserCare());

        dto.setUserCareCustom(userPlants.getUserCareCustom());

        return dto;
    }
}
