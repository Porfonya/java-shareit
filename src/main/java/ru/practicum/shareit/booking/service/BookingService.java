package ru.practicum.shareit.booking.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Service
public interface BookingService {

    BookingDto createBooking(BookingDto bookingDto, User user);

    BookingDto approvedBooking(Booking booking, Boolean approved, Long userId);

    BookingDto getBookingById(Booking booking, Long id);

    List<BookingDto> getAllBookingByUser(Long userId, String state);


    List<BookingDto> getAllBookingByOwner(Long userId, String state);
}
