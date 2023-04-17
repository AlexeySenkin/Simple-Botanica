package ru.botanica.entities.care;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

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
}
