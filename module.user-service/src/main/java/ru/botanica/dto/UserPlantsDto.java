package ru.botanica.dto;

import lombok.Data;
import ru.botanica.entities.Plant;
import ru.botanica.entities.UserCare;
import ru.botanica.entities.UserCareCustom;

import java.util.Collection;
import java.util.Objects;

@Data
public class UserPlantsDto {

    private Long userPlantId;

    private Long userId;

    private Long plantId;

    private Boolean isBanned;

    private Boolean isActive;

    private Plant plant;

    private Collection<UserCareCustom> userCareCustom;

    private Collection<UserCare> userCare;

    public UserPlantsDto() {
    }

    @Override
    public String toString() {
        return "UserPlantsDto{" +
                "userPlantId=" + userPlantId +
                ", userId=" + userId +
                ", plantId=" + plantId +
                ", isBanned=" + isBanned +
                ", isActive=" + isActive +
                ", plant=" + plant +
                ", userCareCustom=" + userCareCustom +
                ", userCare=" + userCare +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPlantsDto that = (UserPlantsDto) o;
        return Objects.equals(userPlantId, that.userPlantId) && Objects.equals(userId, that.userId) && Objects.equals(plantId, that.plantId) && Objects.equals(isBanned, that.isBanned) && Objects.equals(isActive, that.isActive) && Objects.equals(plant, that.plant) && Objects.equals(userCareCustom, that.userCareCustom) && Objects.equals(userCare, that.userCare);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userPlantId, userId, plantId, isBanned, isActive, plant, userCareCustom, userCare);
    }
}
