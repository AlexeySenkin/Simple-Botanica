package ru.botanica.mappers;

import org.springframework.stereotype.Component;
import ru.botanica.dtos.CareDto;
import ru.botanica.dtos.CareDtoShort;
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

    public static Care mapToEntity(CareDtoShort careDtoShort, boolean isActive) {
        Care care = new Care();
        care.setId(careDtoShort.getId());
        care.setName(careDtoShort.getName());
//        Хотя сейчас короткая Дто используется только для списка, где все значения true, мы не знаем, как эта Дто
//        будет использоваться в будущем. Потому добавлен выбор для isActive
        care.setActive(isActive);
        return care;
    }

    public static CareDto mapToDto(Care care) {
        CareDto careDto = new CareDto();
        careDto.setId(care.getId());
        careDto.setName(care.getName());
        careDto.setActive(care.isActive());
        return careDto;
    }

    public static CareDtoShort matToDtoShort(Care care) {
        CareDtoShort careDtoShort = new CareDtoShort();
        careDtoShort.setId(care.getId());
        careDtoShort.setName(care.getName());
        return careDtoShort;
    }
}