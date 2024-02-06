package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserStorage {
    User create(User user);

    User update(Long id, User user);

    User getById(Long id);

    void deleteById(Long id);

    List<User> getUsers();
}
