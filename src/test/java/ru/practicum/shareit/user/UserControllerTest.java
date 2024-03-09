package ru.practicum.shareit.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserServiceImpl userService;
    @InjectMocks
    private UserController userController;
    private UserDto expected;

    private UserDto updateUser;

    @BeforeEach
    void setUp() {
        expected = new UserDto(1L, "newUser@mail.ru", "NewUser");
        updateUser = new UserDto(2L, "updateUser@mail.ru", "updateUser");
    }

    @Test
    void createUser() {


        when(userService.createUser(any())).thenReturn(expected);
        UserDto actual = userController.createUser(any());
        assertEquals(expected, actual);
    }

    @Test
    void getUserById_whenInvoked_thenResponseStatusOkWithUserBody() {

        when(userService.getUserById(any())).thenReturn(expected);
        UserDto actual = userController.getUserById(any());
        assertEquals(expected, actual);
    }

    @Test
    void getAllUsers_whenInvoked_thenResponseStatusOkWithUsersCollectionInBody() {
        List<UserDto> expectedUsers = List.of(expected);
        when(userService.getAllUsers()).thenReturn(expectedUsers);

        ResponseEntity<Collection<UserDto>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedUsers, response.getBody());
    }

    @Test
    void updateUser() {

        when(userService.updateUser(1L, updateUser)).thenReturn(updateUser);

        ResponseEntity<UserDto> response = userController.updateUser(1L, updateUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updateUser, response.getBody());

    }

    @Test
    void deleteUserById() {

        ResponseEntity<String> response = userController.deleteUserById(any());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Пользователь удален", response.getBody());
    }

}