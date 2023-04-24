package ru.botanica.dto;

import lombok.Data;

import java.util.Date;
import java.util.Objects;

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

    public UserDto() {
    }

    @Override
    public String toString() {
        return "UserDto{" +
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
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(userId, userDto.userId) && Objects.equals(firstName, userDto.firstName) && Objects.equals(lastName, userDto.lastName) && Objects.equals(email, userDto.email) && Objects.equals(phoneNumber, userDto.phoneNumber) && Objects.equals(address, userDto.address) && Objects.equals(regDate, userDto.regDate) && Objects.equals(isBanned, userDto.isBanned) && Objects.equals(isActive, userDto.isActive) && Objects.equals(userName, userDto.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, firstName, lastName, email, phoneNumber, address, regDate, isBanned, isActive, userName);
    }
}
