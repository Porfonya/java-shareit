package ru.practicum.shareit.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserServiceImpl;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/users")
public class UserController {
    public final UserServiceImpl userService;

    @PostMapping
    public UserDto createUser(@RequestBody @Valid UserDto userDto) {
        log.info("Создан новый пользователь");
        return userService.createUser(userDto);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable("id") Long userId) {
        log.info("Найден пользователь с id{}", userId);
        return userService.getUserById(userId);
    }

    @GetMapping
    public ResponseEntity<Collection<UserDto>> getAllUsers() {
        log.info("Выведены все пользователи");
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long userId, @RequestBody @Valid UserDto userDto) {
        log.info("Пользователь обновлен");
        return new ResponseEntity<>(userService.updateUser(userId, userDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
        log.info("Пользователь удален");
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }
}
