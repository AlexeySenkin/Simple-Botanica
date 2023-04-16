package ru.botanica.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(schema = "simple_botanica", name = "care")
public class Care {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "care_id")
    private Integer careId;

    @Column(name = "care_name")
    private String careName;

    @Column(nullable = false, name = "is_active")
    private Integer isActive;

    @Override
    public String toString() {
        return "Care{" +
                "careId=" + careId +
                ", careName='" + careName + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
