package ru.botanica.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {

    private Integer userId;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String address;

    private Date regDate;

    private Integer isBanned;

    private Integer isActive;

    private String userName;

}
