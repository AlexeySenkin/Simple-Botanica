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
    private Integer userCareId;

    @Column(name = "user_plant_id")
    private Integer userPlantId;

    @Column(nullable = false, name = "event_date")
    private Date eventDate;

    @Column(name = "care_id")
    private Integer careId;

    @Column(name = "care_volume")
    private Integer careVolume;

    @Column(name = "prim")
    private Integer prims;

    @OneToOne()
    @JoinTable(name = "care",
            joinColumns = @JoinColumn(name = "care_id"))
    private Care care;


    @Override
    public String toString() {
        return "UserCare{" +
                "userCareId=" + userCareId +
                ", userPlantId=" + userPlantId +
                ", eventDate=" + eventDate +
                ", careId=" + careId +
                ", careVolume=" + careVolume +
                ", prims=" + prims +
                ", care=" + (care == null ? null : care) +
                '}';
    }
}
