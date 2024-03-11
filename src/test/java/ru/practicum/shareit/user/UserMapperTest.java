package ru.practicum.shareit.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class UserMapperTest {
    private User newUser;

    private UserDto newUserDto;

    @BeforeEach
    void setUp() {
        newUser = new User(1L, "newUser@mail.ru", "NewUser");
        newUserDto = new UserDto(1L, "newUser@mail.ru", "NewUser");
    }

    @Test
    void mapToUserDto() {
        UserDto expected = new UserDto(1L, "newUser@mail.ru", "NewUser");

        UserDto result = UserMapper.mapToUserDto(newUser);
        Assertions.assertThat(expected.getId()).isEqualTo(result.getId());
        Assertions.assertThat(expected.getEmail()).isEqualTo(result.getEmail());
        Assertions.assertThat(expected.getName()).isEqualTo(result.getName());

    }

    @Test
    void mapToUser() {
        User expected = new User(1L, "newUser@mail.ru", "NewUser");

        User result = UserMapper.mapToUser(newUserDto);
        Assertions.assertThat(expected.getId()).isEqualTo(result.getId());
        Assertions.assertThat(expected.getEmail()).isEqualTo(result.getEmail());
        Assertions.assertThat(expected.getName()).isEqualTo(result.getName());
    }

}