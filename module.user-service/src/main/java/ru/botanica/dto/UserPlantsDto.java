package ru.botanica.dto;

import lombok.Data;
import ru.botanica.entities.UserCareCustom;

import java.util.Collection;

@Data
public class UserPlantsDto {

    private Long userPlantId;

    private Long plantId;

    private String name;

    private String family;

    private String genus;

    private String description;

    private String shortDescription;

    private String filePath;

    private Collection<UserCareCustom> userCareCustom;


}
