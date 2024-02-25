package ru.practicum.shareit.booking.Mapper;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.item.dto.ItemBooking;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class BookingMapper {
    public static BookingDto mapToBookingDto(Booking booking) {
        return BookingDto.builder()
                .id(booking.getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .status(booking.getStatus())
                .booker(booking.getBooker())
                .item(booking.getItem())
                .build();
    }


    public static Booking mapToBooking(BookingDto bookingDto) {


        return Booking.builder()
                .id(bookingDto.getId())
                .start(bookingDto.getStart())
                .end(bookingDto.getEnd())
                .status(bookingDto.getStatus())
                .item(bookingDto.getItem())
                .booker(bookingDto.getBooker())
                .build();
    }

    public static List<BookingDto> mapToBookingDto(Iterable<Booking> bookings) {
        List<BookingDto> valuesDtos = new ArrayList<>();
        for (Booking booking : bookings) {
            valuesDtos.add(mapToBookingDto(booking));
        }
        return valuesDtos;
    }

    public static List<ItemBooking> mapToItemBookingDto(Iterable<Booking> bookings) {
        List<ItemBooking> valuesDtos = new ArrayList<>();
        for (Booking booking : bookings) {
            valuesDtos.add(mapToItemBooking(booking));
        }
        return valuesDtos;
    }

    public static ItemBooking mapToItemBooking(Booking booking) {
        return ItemBooking.builder()
                .id(booking.getId())
                .bookerId(booking.getBooker().getId())
                .build();
    }
}
