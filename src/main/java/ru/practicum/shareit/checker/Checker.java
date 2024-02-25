package ru.practicum.shareit.checker;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

@Component
@AllArgsConstructor

public class Checker {

    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private ItemRepository itemRepository;

    public Booking checkerBooking(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException(String.format("Бронь с %d не найден", bookingId)));
    }

    public void checkerUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь с %d не найден", userId)));
    }

    public User checkerUserWithReturn(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь с %d не найден", userId)));
    }

    public Item checkerItem(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException(String.format("Инструмент с %d не найден", itemId)));
    }
}
