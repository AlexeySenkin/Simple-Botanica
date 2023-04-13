package ru.botanica.entities.users;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@Table(schema = "simple_botanica", name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false, length = 45, name = "first_name")
    private String firstName;

    @Column(nullable = false, length = 45, name = "last_name")
    private String lastName;

    @Column(nullable = false, length = 45, name = "email")
    private String email;

    @Column(nullable = false, length = 45, name = "phone_number")
    private String phoneNumber;

    @Column(nullable = false, length = 128, name = "address")
    private String address;

    @Column(nullable = false, name = "reg_date")
    private Date regDate;

    @Column(nullable = false, name = "is_banned")
    private Integer isBanned;

    @Column(nullable = false, name = "is_active")
    private Integer isActive;

    @Column(nullable = false, length = 45, name = "user_name")
    private String userName;

}

