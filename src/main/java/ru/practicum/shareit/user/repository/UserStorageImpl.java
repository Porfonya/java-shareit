package ru.practicum.shareit.user.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.Conflict;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.model.User;

import java.util.*;

@Component
@Slf4j
public class UserStorageImpl implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private Long id = 0L;


    @Override
    public User create(User user) {
        if (user.getEmail() == null) {
            throw new ValidationException("Незаполненный почтовый ящик");
        }
        for (Map.Entry<Long, User> entry : users.entrySet()) {
            User thisUser = entry.getValue();
            if (user.getEmail().equals(thisUser.getEmail())) {
                throw new Conflict("Почтовый ящик уже есть в системе");
            }
        }
        user.setId(++id);
        users.put(id, user);
        return users.get(id);
    }

    @Override
    public User update(Long id, User user) {

        if (isEmailValidate(id, user.getEmail())) {
            throw new Conflict("Такая почта уже существует");
        }
        users.put(id, user);
        return users.get(id);
    }

    @Override
    public User getById(Long id) {
        if (id == null || !users.containsKey(id)) {
            throw new NotFoundException("с таким id пользователя не существует");
        }
        return users.get(id);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null || !users.containsKey(id)) {
            throw new NotFoundException("с таким id пользователя не существует");
        }
        users.remove(id);
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    private boolean isEmailValidate(Long id, String email) {
        return users.values()
                .stream()
                .filter(user -> !Objects.equals(user.getId(), id))
                .anyMatch(user -> user.getEmail().equalsIgnoreCase(email));
    }
}
