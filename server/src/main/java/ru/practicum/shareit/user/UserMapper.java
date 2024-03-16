package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserMapper {
    public static UserDto mapToUserDto(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getName());
    }

    public static User mapToUser(UserDto userDto) {

        return new User(userDto.getId(), userDto.getEmail(), userDto.getName());
    }

}
