package ru.botanica.dto;


import ru.botanica.entities.UserCareCustom;

public class UserCareCustomDtoMapper {


    public UserCareCustomDtoMapper() {
        throw new AssertionError("Instantiating utility class");
    }

    public static UserCareCustomDto mapToDto(UserCareCustom userCareCustom) {

        UserCareCustomDto dto = new UserCareCustomDto();

        dto.setUserCareCustomId(userCareCustom.getUserPlantId());

        dto.setUserPlantId(userCareCustom.getUserPlantId());

        dto.setCareId(userCareCustom.getCare().getCareId());

        dto.setUserCareCount(userCareCustom.getUserCareCount());

        dto.setUserCareVolume(userCareCustom.getUserCareVolume());

        dto.setCare(userCareCustom.getCare());

        return dto;
    }

    public static UserCareCustom mapToEntity(UserCareCustomDto userCareCustomDto) {

        UserCareCustom userCareCustom = new UserCareCustom();

        userCareCustom.setUserCareCustomId(userCareCustomDto.getUserCareCustomId());

        userCareCustom.setUserPlantId(userCareCustomDto.getUserPlantId());

        userCareCustom.setUserCareCount(userCareCustomDto.getUserCareCount());

        userCareCustom.setUserCareVolume(userCareCustomDto.getUserCareVolume());

        userCareCustom.setCare(userCareCustomDto.getCare());

        return userCareCustom;
    }
}
