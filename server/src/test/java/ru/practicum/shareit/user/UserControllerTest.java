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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserServiceImpl userService;
    @InjectMocks
    private UserController userController;
    private UserDto expected;

    @BeforeEach
    void setUp() {
        expected = new UserDto(1L, "newUser@mail.ru", "NewUser");
    }

    @Test
    void createUser() {


        when(userService.createUser(expected)).thenReturn(expected);
        UserDto actual = userController.createUser(expected);
        assertEquals(expected, actual);
    }


}