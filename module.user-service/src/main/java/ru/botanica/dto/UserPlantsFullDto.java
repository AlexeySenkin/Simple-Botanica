package ru.botanica.dto;

import lombok.Data;
import ru.botanica.entities.Plant;
import ru.botanica.entities.UserCare;
import ru.botanica.entities.UserCareCustom;

import java.util.Collection;

@Data
public class UserPlantsFullDto {

    private Long userPlantId;

    private Long userId;

    private Long plantId;

    private Boolean isBanned;

    private Boolean isActive;

    private Plant plant;

    private Collection<UserCareCustom> userCareCustom;

    private Collection<UserCare> userCare;

}
