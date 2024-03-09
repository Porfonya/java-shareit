package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.practicum.shareit.checker.Checker;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserServiceImpl userService;
    @Mock
    Checker checker;


    @Test
    void createUser_whenUserEmailValid_thenSavedUser() {
        User expectedUser = new User(1L, "newUser@mail.ru", "NewUser");
        UserDto expectedUserDto = new UserDto(1L, "newUser@mail.ru", "NewUser");
        when(userRepository.save(expectedUser)).thenReturn(expectedUser);

        UserDto actualUser = userService.createUser(UserMapper.mapToUserDto(expectedUser));
        assertEquals(expectedUserDto.getEmail(), actualUser.getEmail());
    }

    @Test
    void createUser_whenUserEmailNotValid_thenThrowValidationException() {
        User expectedUser = new User();
        when(userRepository.save(expectedUser)).thenThrow(
                new ValidationException("Адрес электронной почты должен быть заполнен"));

        ValidationException validationException = assertThrows(ValidationException.class,
                () -> userService.createUser(UserMapper.mapToUserDto(expectedUser)));
        assertEquals(validationException.getMessage(), "Адрес электронной почты должен быть заполнен");
    }


    @Test
    void getUserById_whenUserFound_thenThrowNotFoundException() {
        long userId = 0L;
        User expectedUser = new User();
        expectedUser.setEmail("test@mail.ru");
        when(userRepository.findById(userId)).thenThrow(new NotFoundException("Такой пользователь с Id %d не найден" + userId));
        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> userService.getUserById(userId));

        assertEquals(notFoundException.getMessage(), "Такой пользователь с Id 0 не найден");
    }

    @Test
    void getAll_whenDataExists_thenReturnEmptyCollection() {
        when(userService.getAllUsers()).thenReturn(Collections.emptyList());

        Collection<UserDto> users = userService.getAllUsers();

        assertEquals(users.size(), 0);
    }


    @Test
    void update_whenUserNotExists_thenThrowEntityNotFoundException() {
        long userId = 0L;
        User expectedUser = new User();
        expectedUser.setEmail("update@mail.ru");
        when(userRepository.findById(userId)).thenThrow(
                new NotFoundException("Такой пользователь с Id %d не найден" + userId));

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> userService.updateUser(userId, UserMapper.mapToUserDto(expectedUser)));

        assertEquals(notFoundException.getMessage(), "Такой пользователь с Id 0 не найден");
    }

    @Test
    void delete_whenUserExists_thenDeleteUser() {
        long userId = 0L;
        User expectedUser = new User();
        expectedUser.setEmail("test@mail.ru");
        when(checker.checkerUserWithReturn(userId)).thenReturn(expectedUser);
        userService.deleteUser(userId);
        verify(userRepository, times(1))
                .deleteById(0L);
    }


}
