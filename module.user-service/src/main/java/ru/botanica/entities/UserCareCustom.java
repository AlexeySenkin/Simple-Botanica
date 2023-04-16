package ru.botanica.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(schema = "simple_botanica", name = "user_care_custom")
public class UserCareCustom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_care_custom_id")
    private Long userCareCustomId;

    @Column(name = "user_plant_id")
    private Long userPlantId;


    @Column(name = "care_id")
    private Long careId;

    @Column(name = "user_care_count")
    private Integer userCareCount;

    @Column(name = "user_care_volume")
    private Double userCareVolume;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    private Care care;

    @Override
    public String toString() {
        return "UserCareCustom{" +
                "userCareCustomId=" + userCareCustomId +
                ", userPlantId=" + userPlantId +
                ", careId=" + careId +
                ", userCareCount=" + userCareCount +
                ", userCareVolume=" + userCareVolume +
                ", care=" + (care == null ?  null : care) +
                '}';
    }
}
