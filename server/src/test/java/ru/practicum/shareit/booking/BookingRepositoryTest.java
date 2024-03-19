package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class BookingRepositoryTest {

    private final BookingRepository bookingRepository;

    private static final Pageable PAGE_FOR_BOOKINGS =
            PageRequest.of(0, 10, Sort.by("start").descending());


    @Test
    @SneakyThrows
    void save_whenAllFieldsExists_thenBookingSaved() {
        User user = new User(1L, "email@mail.ru", "User name");
        Item item = new Item(1L, "name", "description", true,
                null, null, null);


        Booking booking = Booking.builder()
                .id(1L)
                .start(LocalDateTime.now())
                .end(LocalDateTime.now().plusMinutes(5))
                .build();

        booking.setStatus(BookingState.WAITING);
        booking.setBooker(user);
        booking.setItem(item);

        assertNotNull(booking);
    }

    @Test
    void findAllByBookerIdOrderByStartDesc() {
        Long bookerId = 1L;
        assertDoesNotThrow(() -> bookingRepository.findAllByBookerIdOrderByStartDesc(bookerId, PAGE_FOR_BOOKINGS));

    }

    @Test
    void findAllByItemOwnerIdOrderByStartDesc() {
        Long ownerId = 1L;
        assertDoesNotThrow(() -> bookingRepository.findAllByItemOwnerIdOrderByStartDesc(ownerId, PAGE_FOR_BOOKINGS));
    }

    @Test
    void findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc() {
        Long bookerId = 1L;
        LocalDateTime start = LocalDateTime.now().plusDays(2);
        LocalDateTime end = LocalDateTime.now().plusDays(3);
        assertDoesNotThrow(() -> bookingRepository.findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(bookerId,
                start, end, PAGE_FOR_BOOKINGS));
    }

    @Test
    void findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc() {
        Long ownerId = 1L;
        LocalDateTime start = LocalDateTime.now().plusDays(2);
        LocalDateTime end = LocalDateTime.now().plusDays(3);
        assertDoesNotThrow(() -> bookingRepository.findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc(
                ownerId, start, end, PAGE_FOR_BOOKINGS));
    }

    @Test
    void findAllByBookerIdAndStartAfterOrderByStartDesc() {
        Long bookerId = 1L;
        LocalDateTime start = LocalDateTime.now().plusDays(2);
        assertDoesNotThrow(() -> bookingRepository.findAllByBookerIdAndStartAfterOrderByStartDesc(
                bookerId, start, PAGE_FOR_BOOKINGS));
    }

    @Test
    void findAllByItemOwnerIdAndStartAfterOrderByStartDesc() {
        Long ownerId = 1L;
        LocalDateTime start = LocalDateTime.now().plusDays(2);
        assertDoesNotThrow(() -> bookingRepository.findAllByBookerIdAndStartAfterOrderByStartDesc(ownerId,
                start, PAGE_FOR_BOOKINGS));
    }

    @Test
    void findAllByBookerIdAndEndBeforeOrderByStartDesc() {
        Long bookerId = 1L;
        LocalDateTime end = LocalDateTime.now().plusDays(3);
        assertDoesNotThrow(() -> bookingRepository.findAllByBookerIdAndEndBeforeOrderByStartDesc(bookerId, end,
                PAGE_FOR_BOOKINGS));
    }

    @Test
    void findAllByItemOwnerIdAndEndBeforeOrderByStartDesc() {
        Long ownerId = 1L;
        LocalDateTime end = LocalDateTime.now().plusDays(3);
        assertDoesNotThrow(() -> bookingRepository.findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(ownerId,
                end, PAGE_FOR_BOOKINGS));
    }

    @Test
    void findAllByBookerIdAndStatusOrderByStartDesc() {
        Long bookerId = 1L;
        BookingState bookingState = BookingState.WAITING;
        assertDoesNotThrow(() -> bookingRepository.findAllByBookerIdAndStatusOrderByStartDesc(bookerId,
                bookingState, PAGE_FOR_BOOKINGS));
    }

    @Test
    void findAllByItemOwnerIdAndStatusOrderByStartDesc() {
        Long ownerId = 1L;
        BookingState bookingState = BookingState.WAITING;
        assertDoesNotThrow(() -> bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(ownerId,
                bookingState, PAGE_FOR_BOOKINGS));
    }

    @Test
    void findTop2ByItemIdAndStatusOrderByEndAsc() {
        Long itemId = 1L;
        BookingState bookingState = BookingState.WAITING;
        assertDoesNotThrow(() -> bookingRepository.findTop2ByItemIdAndStatusOrderByEndAsc(itemId, bookingState));
    }

    @Test
    void findBookingsByBookerIdAndItemIdAndStatusAndEndBefore() {
        Long bookerId = 1L;
        Long itemId = 1L;
        BookingState bookingState = BookingState.WAITING;
        LocalDateTime end = LocalDateTime.now().plusDays(3);
        assertDoesNotThrow(() -> bookingRepository.findBookingsByBookerIdAndItemIdAndStatusAndEndBefore(bookerId,
                itemId, bookingState, end));
    }

    @Test
    void findTop1ByItemIdAndStartBeforeOrderByStartDesc() {
        Long itemId = 1L;
        LocalDateTime start = LocalDateTime.now().plusDays(2);
        assertDoesNotThrow(() -> bookingRepository.findTop1ByItemIdAndStartBeforeOrderByStartDesc(itemId, start));
    }

    @Test
    void findTop1ByItemIdAndStartAfterOrderByStartAsc() {
        Long itemId = 1L;
        LocalDateTime start = LocalDateTime.now().plusDays(2);

        assertDoesNotThrow(() -> bookingRepository.findTop1ByItemIdAndStartAfterOrderByStartAsc(itemId, start));
    }

}