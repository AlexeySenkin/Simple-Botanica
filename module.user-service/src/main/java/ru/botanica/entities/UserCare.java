package ru.botanica.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(schema = "simple_botanica", name = "user_care")
public class UserCare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_care_id")
    private Long userCareId;

    @Column(name = "user_plant_id")
    private Long userPlantId;

    @Column(nullable = false, name = "event_date")
    private Date eventDate;

//    @Column(name = "care_id")
//    private Long careId;

    @Column(name = "care_volume")
    private Double careVolume;

    @Column(name = "prim")
    private Integer prims;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "care_id")
    private Care care;


    @Override
    public String toString() {
        return "UserCare{" +
                "userCareId=" + userCareId +
                ", userPlantId=" + userPlantId +
                ", eventDate=" + eventDate +
            //    ", careId=" + careId +
                ", careVolume=" + careVolume +
                ", prims=" + prims +
                ", care=" + (care == null ? null : care) +
                '}';
    }
}
