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
@Table(schema = "simple_botanica", name = "user_plants")
public class UserPlant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_plant_id")
    private Long userPlantId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "plant_id")
    private Long plantId;

    @Column(nullable = false, name = "is_banned")
    private Boolean isBanned;

    @Column(nullable = false, name = "is_active")
    private Boolean isActive;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_plant_id")
    private Collection<UserCare> userCare;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_plant_id")
    private Collection<UserCareCustom> userCareCustom;
//
//
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
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
               // ", userCare=" + (userCare == null ? null : userCare) +
                ", plant=" + (plant == null ? null : plant) +
                '}';
    }
}
