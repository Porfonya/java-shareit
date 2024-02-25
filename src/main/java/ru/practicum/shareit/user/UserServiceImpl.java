package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        if (userDto.getEmail() != null) {
            User convertUser = UserMapper.mapToUser(userDto);
            return UserMapper.mapToUserDto(userRepository.save(convertUser));
        } else {
            throw new ValidationException("Адрес электронной почты должен быть заполнен");
        }
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        User existingUser = userRepository.getReferenceById(id);

        userDto.setId(id);
        userDto.setName(userDto.getName() == null ? existingUser.getName() : userDto.getName());
        userDto.setEmail(userDto.getEmail() == null ? existingUser.getEmail() : userDto.getEmail());
        return UserMapper.mapToUserDto(userRepository.save(UserMapper.mapToUser(userDto)));
    }

    @Override
    public UserDto getUserById(Long id) {
        if (userRepository.existsById(id)) {
            return UserMapper.mapToUserDto(userRepository.getReferenceById(id));
        } else {
            throw new NotFoundException("Error");
        }

    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }
}
