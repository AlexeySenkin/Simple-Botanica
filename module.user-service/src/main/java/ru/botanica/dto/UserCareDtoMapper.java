package ru.botanica.dto;


import ru.botanica.entities.UserCare;

public class UserCareDtoMapper {


    public UserCareDtoMapper() {
        throw new AssertionError("Instantiating utility class");
    }

    public static UserCareDto mapToDto(UserCare userCare) {

        UserCareDto dto = new UserCareDto();

        dto.setUserCareId(userCare.getUserCareId());

        dto.setUserPlantId(userCare.getUserPlantId());

        dto.setEventDate(userCare.getEventDate());

        dto.setCareVolume(userCare.getCareVolume());

        dto.setPrims(userCare.getPrims());

        dto.setCare(userCare.getCare());

        return dto;
    }

    public static UserCare mapToEntity(UserCareDto userCareDto) {

        UserCare userCare = new UserCare();

        userCare.setUserCareId(userCareDto.getUserCareId());

        userCare.setUserPlantId(userCareDto.getUserPlantId());

        userCare.setEventDate(userCareDto.getEventDate());

        userCare.setCareVolume(userCareDto.getCareVolume());

        userCare.setPrims(userCareDto.getPrims());

        userCare.setCare(userCareDto.getCare());

        return userCare;
    }
}
