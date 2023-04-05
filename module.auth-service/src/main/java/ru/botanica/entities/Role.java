package ru.botanica.entities;

import lombok.Data;
import javax.persistence.*;


@Entity
@Data
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(name = "role_name")
    private String name;

    @Column(name = "is_active")
    private boolean isActive;

}
