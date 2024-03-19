package ru.practicum.shareit.checker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.CommentRepository;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckerTest {
    @Mock
    UserRepository userRepository;
    @Mock
    BookingRepository bookingRepository;
    @Mock
    ItemRepository itemRepository;
    @Mock
    ItemRequestRepository requestRepository;
    @Mock
    CommentRepository commentRepository;
    @InjectMocks
    Checker checker;

    @Test
    void checkerBookingWithReturn() {
        Booking booking = new Booking();

        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));

        Booking actual = checker.checkerBooking(1L);
        assertEquals(actual, booking);
    }

    @Test
    void checkerUser_thenThrowNotFoundException() {
        when(userRepository.findById(anyLong())).thenThrow(new NotFoundException("Пользователь не найден"));

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> checker.checkerUser(1L));

        assertEquals(exception.getMessage(), "Пользователь не найден");
    }

    @Test
    void checkerUserWithReturn() {
        User user = new User();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        User actual = checker.checkerUserWithReturn(1L);
        assertEquals(actual, user);
    }

    @Test
    void checkerItem_thenThrowNotFoundException() {
        when(itemRepository.findById(anyLong())).thenThrow(new NotFoundException("Инструмент не найден"));

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> checker.checkerItem(1L));

        assertEquals(exception.getMessage(), "Инструмент не найден");
    }
    @Test
    void checkerItemWithReturn() {
        Item item = new Item(1L,
                "newItem",
                "newItemDescription",
                true,
                null,
                new ItemRequest(),
                null);

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        Item actual = checker.checkerItem(1L);
        assertEquals(actual, item);
    }
    @Test
    void checkerItemRequest_thenThrowNotFoundException() {
        when(requestRepository.findById(anyLong())).thenThrow(new NotFoundException("Запрос не найден"));

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> checker.checkerItemRequest(1L));

        assertEquals(exception.getMessage(), "Запрос не найден");
    }
    @Test
    void checkerItemRequestWithReturn() {
        ItemRequest itemRequest = new ItemRequest();

        when(requestRepository.findById(anyLong())).thenReturn(Optional.of(itemRequest));

        ItemRequest actual = checker.checkerItemRequest(1L);
        assertEquals(actual, itemRequest);
    }
}