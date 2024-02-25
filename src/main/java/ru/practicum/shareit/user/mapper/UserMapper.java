package ru.practicum.shareit.user.mapper;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

@RequiredArgsConstructor
public class UserMapper {
    public static UserDto mapToUserDto(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getName());
    }

    public static User mapToUser(UserDto userDto) {

        return new User(userDto.getId(), userDto.getEmail(), userDto.getName());
    }

}
