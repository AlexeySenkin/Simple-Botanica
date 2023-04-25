package ru.botanica.dto;

import ru.botanica.entities.UserPlant;

public class UserPlantsDtoMapper {


    public UserPlantsDtoMapper() {
        throw new AssertionError("Instantiating utility class");
    }

    public static UserPlantsDto mapToDto(UserPlant userPlant) {
        UserPlantsDto dto = new UserPlantsDto();

        dto.setUserPlantId(userPlant.getUserPlantId());

        dto.setPlantId(userPlant.getPlantId());

        dto.setName(userPlant.getPlant().getName());

        dto.setFamily(userPlant.getPlant().getFamily());

        dto.setGenus(userPlant.getPlant().getGenus());

        dto.setDescription(userPlant.getPlant().getDescription());

        dto.setShortDescription(userPlant.getPlant().getShortDescription());

        dto.setFilePath(userPlant.getPlant().getPhoto().getFilePath());

        dto.setUserCareCustom(userPlant.getUserCareCustom());

        return dto;
    }
}
