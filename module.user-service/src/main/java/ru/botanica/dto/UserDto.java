package ru.botanica.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {

    private Long userId;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String address;

    private Date regDate;

    private Boolean isBanned;

    private Boolean isActive;

    private String userName;

}
