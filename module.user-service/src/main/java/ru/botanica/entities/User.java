package ru.botanica.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(schema = "simple_botanica", name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(nullable = true, length = 45, name = "first_name")
    private String firstName;

    @Column(nullable = true, length = 45, name = "last_name")
    private String lastName;

    @Column(nullable = false, length = 45, name = "email")
    private String email;

    @Column(nullable = true, length = 45, name = "phone_number")
    private String phoneNumber;

    @Column(nullable = true, length = 128, name = "address")
    private String address;

    @Column(nullable = false, name = "reg_date")
    private Date regDate;

    @Column(nullable = false, name = "is_banned")
    private Integer isBanned;

    @Column(nullable = false, name = "is_active")
    private Integer isActive;

    @Column(nullable = false, length = 45, name = "user_name")
    private String userName;

    @OneToMany
    @JoinTable(name = "users_plants",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "plant_id"))
    private Collection<UserPlants> userPlants;


    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", regDate=" + regDate +
                ", isBanned=" + isBanned +
                ", isActive=" + isActive +
                ", userName='" + userName + '\'' +
                ", userPlants=" + (userPlants == null ? null : userPlants) +
                '}';
    }
}

