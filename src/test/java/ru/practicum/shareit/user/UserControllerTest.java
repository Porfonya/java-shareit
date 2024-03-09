package ru.practicum.shareit.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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


        when(userService.createUser(expected)).thenReturn(expected);
        UserDto actual = userController.createUser(expected);
        assertEquals(expected, actual);
    }

    @Test
    void getUserById_whenInvoked_thenResponseStatusOkWithUserBody() {

        when(userService.getUserById(expected.getId())).thenReturn(expected);
        UserDto actual = userController.getUserById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    void updateUser() {
        UserDto dto = new UserDto();
        Long userId = 1L;
        when(userService.updateUser(userId, dto)).thenReturn(updateUser);

        ResponseEntity<UserDto> response = userController.updateUser(userId, dto);

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