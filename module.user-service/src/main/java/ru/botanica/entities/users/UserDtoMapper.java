package ru.botanica.entities.users;

public final class UserDtoMapper {

    public UserDtoMapper() {
        throw new AssertionError("Instantiating utility class");
    }

    public static UserDto mapToDto(User user) {
        UserDto dto = new UserDto();
        dto.setUserId(user.getUserId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setAddress(user.getAddress());
        dto.setRegDate(user.getRegDate());
        dto.setIsBanned(user.getIsBanned());
        dto.setIsActive(user.getIsActive());
        dto.setUserName(user.getUserName());
        return dto;
    }

}
