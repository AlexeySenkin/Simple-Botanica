package ru.botanica.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Getter
@Setter
@Component
public class CareDto {
    private Long id;
    private String name;
    private boolean isActive;

    public CareDto() {
    }

//    TODO:
    @Override
    public String toString() {
        return "CareDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isActive=" + isActive +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CareDto careDto = (CareDto) o;
        return isActive == careDto.isActive && Objects.equals(id, careDto.id) && Objects.equals(name, careDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, isActive);
    }
}
