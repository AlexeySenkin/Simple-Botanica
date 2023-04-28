package ru.botanica.dto;

import lombok.Data;
import ru.botanica.entities.Care;

@Data
public class UserCareCustomDto
{
        private Long userCareCustomId;

        private Long userPlantId;

        private Long careId;

        private Long userCareCount;

        private Double userCareVolume;

        private Care care;




}
