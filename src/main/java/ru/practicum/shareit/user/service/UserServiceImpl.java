package ru.practicum.shareit.user.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserStorage userStorage;

    @Override
    public UserDto createUser(UserDto userDto) {
        User convertUser = UserMapper.mapToUser(userDto);
        return UserMapper.mapToUserDto(userStorage.create(convertUser));
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        User existingUser = userStorage.getById(id);

        userDto.setId(id);
        userDto.setName(userDto.getName() == null ? existingUser.getName() : userDto.getName());
        userDto.setEmail(userDto.getEmail() == null ? existingUser.getEmail() : userDto.getEmail());
        return UserMapper.mapToUserDto(userStorage.update(id, UserMapper.mapToUser(userDto)));
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userStorage.getById(id);
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public void deleteUser(Long id) {
        userStorage.deleteById(id);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userStorage.getUsers();
        return users.stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }
}
