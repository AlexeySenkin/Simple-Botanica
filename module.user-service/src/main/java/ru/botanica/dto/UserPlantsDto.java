package ru.botanica.dto;

import lombok.Data;
import ru.botanica.entities.Plant;
import ru.botanica.entities.UserCare;
import ru.botanica.entities.UserCareCustom;

import java.util.Collection;

@Data
public class UserPlantsDto {

    private Integer userPlantId;

    private String userId;

    private String plantId;

    private Integer isBanned;

    private Integer isActive;

    private Plant plant;

    private Collection<UserCareCustom> userCareCustom;

    private Collection<UserCare> userCare;

}
