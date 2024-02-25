package ru.practicum.shareit.booking;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.User;

import java.util.List;

@Service
public interface BookingService {

    BookingDto createBooking(BookingDto bookingDto, User user);

    BookingDto approvedBooking(Booking booking, Boolean approved, Long userId);

    BookingDto getBookingById(Booking booking, Long id);

    List<BookingDto> getAllBookingByUser(Long userId, String state);


    List<BookingDto> getAllBookingByOwner(Long userId, String state);
}
