package ru.botanica.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class CareDtoShort {
    private Long id;
    private String name;

    public CareDtoShort() {
    }

    @Override
    public String toString() {
        return "CareDtoShort{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
