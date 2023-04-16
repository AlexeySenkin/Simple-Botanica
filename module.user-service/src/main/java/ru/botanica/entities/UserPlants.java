package ru.botanica.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(schema = "simple_botanica", name = "users_plants")
public class UserPlants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_plant_id")
    private Integer userPlantId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "plant_id")
    private String plantId;

    @Column(nullable = false, name = "is_banned")
    private Integer isBanned;

    @Column(nullable = false, name = "is_active")
    private Integer isActive;

    @OneToMany
    @JoinTable(name = "user_care",
            joinColumns = @JoinColumn(name = "user_plant_id"),
            inverseJoinColumns = @JoinColumn(name = "user_care_id"))
    private Collection<UserCare> userCare;

    @OneToMany
    @JoinTable(name = "user_care",
            joinColumns = @JoinColumn(name = "user_plant_id"),
            inverseJoinColumns = @JoinColumn(name = "user_care_id"))
    private Collection<UserCareCustom> userCareCustom;


    @OneToOne()
    @JoinTable(name = "plants",
            joinColumns = @JoinColumn(name = "plant_id"))
    private Plant plant;


    @Override
    public String toString() {
        return "UserPlants{" +
                "userPlantId=" + userPlantId +
                ", userId='" + userId + '\'' +
                ", plantId='" + plantId + '\'' +
                ", isBanned=" + isBanned +
                ", isActive=" + isActive +
                ", userCareCustom=" + (userCareCustom == null ? null : userCareCustom) +
                ", userCare=" + (userCare == null ? null : userCare) +
                ", plant=" + (plant == null ? null : plant) +
                '}';
    }
}
