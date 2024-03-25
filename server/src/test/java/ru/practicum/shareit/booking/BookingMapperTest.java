package ru.practicum.shareit.booking;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemBooking;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookingMapperTest {
    private Booking newBooking;
    private BookingDto newBookingDto;
    Clock clock = Clock.fixed(Instant.parse("2014-12-22T10:15:30.00Z"), ZoneId.of("UTC"));


    @BeforeEach
    void setUp() {
        Item item = new Item(1L,
                "newItem",
                "newItemDescription",
                true,
                null,
                new ItemRequest(),
                null);

        User user = new User(1L,
                "newUswe@mail.ru",
                "NewUser");

        newBooking = Booking.builder()
                .id(1L)
                .start(LocalDateTime.now(clock))
                .end(LocalDateTime.now(clock).plusDays(1))
                .status(BookingState.WAITING)
                .booker(user)
                .item(item)
                .build();
        newBookingDto = BookingDto.builder()
                .id(1L)
                .start(LocalDateTime.now(clock))
                .end(LocalDateTime.now(clock).plusDays(1))
                .status(BookingState.WAITING)
                .booker(user)
                .item(item)
                .build();
    }

    @Test
    void mapToBookingDto() {

        BookingDto result = BookingMapper.mapToBookingDto(newBooking);

        assertEquals(result.getId(), newBooking.getId());
        assertEquals(result.getStart(), newBookingDto.getStart());
        assertEquals(result.getEnd(), newBookingDto.getEnd());
        assertEquals(result.getStatus(), newBookingDto.getStatus());
        assertEquals(result.getBooker(), newBookingDto.getBooker());
        assertEquals(result.getItem(), newBookingDto.getItem());

    }

    @Test
    void mapToBooking() {
        Booking result = BookingMapper.mapToBooking(newBookingDto);

        assertEquals(result.getId(), newBookingDto.getId());
        assertEquals(result.getStart(), newBookingDto.getStart());
        assertEquals(result.getEnd(), newBookingDto.getEnd());
        assertEquals(result.getStatus(), newBookingDto.getStatus());
        assertEquals(result.getBooker(), newBookingDto.getBooker());
        assertEquals(result.getItem(), newBookingDto.getItem());

    }

    @Test
    void testMapToBookingDto() {
        List<BookingDto> result = BookingMapper.mapToBookingDto(List.of(newBooking));
        assertEquals(result.size(), 1);
    }

    @Test
    void mapToItemBookingDto() {
        List<ItemBooking> result = BookingMapper.mapToItemBookingDto(List.of(newBooking));
        assertEquals(result.size(), 1);

    }

    @Test
    void mapToItemBooking() {
        ItemBooking result = BookingMapper.mapToItemBooking(newBooking);
        assertEquals(result.getId(), newBooking.getId());
        assertEquals(result.getBookerId(), newBooking.getBooker().getId());
    }
}