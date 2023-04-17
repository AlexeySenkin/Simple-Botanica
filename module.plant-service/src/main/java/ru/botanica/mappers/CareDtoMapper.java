package ru.botanica.mappers;

import org.springframework.stereotype.Component;
import ru.botanica.dtos.CareDto;
import ru.botanica.entities.Care;

@Component
public final class CareDtoMapper {
    public static Care mapToEntity(CareDto careDto) {
        Care care = new Care();
        care.setId(careDto.getId());
        care.setName(careDto.getName());
        care.setActive(careDto.isActive());
        return care;
    }

    public static CareDto mapToDto(Care care) {
        CareDto careDto = new CareDto();
        careDto.setId(care.getId());
        careDto.setName(care.getName());
        careDto.setActive(care.isActive());
        return careDto;
    }
}