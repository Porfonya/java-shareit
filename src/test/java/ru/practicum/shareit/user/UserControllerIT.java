package ru.practicum.shareit.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.BookingServiceImpl;
import ru.practicum.shareit.checker.Checker;
import ru.practicum.shareit.item.CommentRepository;
import ru.practicum.shareit.item.ItemController;
import ru.practicum.shareit.item.ItemServiceImpl;
import ru.practicum.shareit.request.ItemRequestServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
@ExtendWith(MockitoExtension.class)
class UserControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private UserServiceImpl userService;
    @MockBean
    private BookingServiceImpl bookingService;
    @MockBean
    private ItemController itemController;
    @MockBean
    private ItemServiceImpl itemService;
    @MockBean
    private ItemRequestServiceImpl itemRequestService;
    @MockBean
    CommentRepository commentRepository;

    @MockBean
    Checker checker;

    @SneakyThrows
    @Test
    void getUserById() {
        Long userId = 1L;
        mockMvc.perform(get("/users/{id}", userId))
                .andExpect(status().isOk());
        verify(userService, times(1)).getUserById(userId);
    }

    @SneakyThrows
    @Test
    void getAllUsers() {
        mockMvc.perform(get("/users")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        verify(userService, times(1)).getAllUsers();
    }

    @SneakyThrows
    @Test
    void createUser() {
        UserDto userDto = new UserDto();

        Mockito.when(userService.createUser(userDto))
                .thenReturn(userDto);
        String result = mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertEquals(mapper.writeValueAsString(userDto), result);
    }

    @SneakyThrows
    @Test
    void createUser_whenUserIdIsNotValid_thenReturnBadRequest() {
        UserDto userDto = new UserDto();
        userDto.setEmail("user .ru");
        Mockito.when(userService.createUser(userDto))
                .thenReturn(userDto);
        mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
        verify(userService, never()).createUser(userDto);

    }

    @SneakyThrows
    @Test
    void updateUser_whenUserIsNotValid_thenReturnBadRequest() {
        Long userId = 1L;
        UserDto userToUpdate = new UserDto();
        userToUpdate.setEmail("user .ru");
        mockMvc.perform(put("/users/{id}", userId)
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(userToUpdate)))
                .andExpect(status().isMethodNotAllowed());
        verify(userService, never()).updateUser(userId, userToUpdate);
    }

    @SneakyThrows
    @Test
    void deleteUserById() {
        Long userId = 1L;
        UserDto userToDelete = new UserDto();
        mockMvc.perform(delete("/users/{id}", userId)
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(userToDelete)))
                .andExpect(status().isOk());
        verify(userService, times(1)).deleteUser(userId);
    }

}