package ru.botanica.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class UserPlantsShortDto {

    private Long userPlantId;

    private Long userId;

    private Long plantId;

    private Boolean isBanned;

    private Boolean isActive;

    public UserPlantsShortDto() {
    }

    @Override
    public String toString() {
        return "UserPlantsShortDto{" +
                "userPlantId=" + userPlantId +
                ", userId=" + userId +
                ", plantId=" + plantId +
                ", isBanned=" + isBanned +
                ", isActive=" + isActive +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPlantsShortDto that = (UserPlantsShortDto) o;
        return Objects.equals(userPlantId, that.userPlantId) && Objects.equals(userId, that.userId) && Objects.equals(plantId, that.plantId) && Objects.equals(isBanned, that.isBanned) && Objects.equals(isActive, that.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userPlantId, userId, plantId, isBanned, isActive);
    }
}
