package ru.practicum.shareit.checker;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

@Component
@AllArgsConstructor

public class Checker {

    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private ItemRepository itemRepository;
    private ItemRequestRepository requestRepository;

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

    public ItemRequest checkerItemRequest(Long itemRequestId) {
        return requestRepository.findById(itemRequestId).orElseThrow(
                () -> new NotFoundException(String.format("Запрос с %d не найден", itemRequestId)));
    }
}
