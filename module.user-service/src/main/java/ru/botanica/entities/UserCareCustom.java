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
    private Integer userCareCustomId;

    @Column(name = "user_plant_id")
    private Integer userPlantId;


    @Column(name = "care_id")
    private Integer careId;

    @Column(name = "care_care_count")
    private Integer careCareCount;

    @Column(name = "care_care_volume")
    private Integer careCareVolume;

    @OneToOne()
    @JoinTable(name = "care",
            joinColumns = @JoinColumn(name = "care_id"))
    private Care care;


}
