package ru.botanica.dto;

import lombok.Data;
import ru.botanica.entities.Care;

import java.util.Date;

@Data
public class UserCareDto
{
        private Long userCareId;

        private Long userPlantId;

        private Date eventDate;

        private Double careVolume;


        private Integer prims;

        private Care care;

}
