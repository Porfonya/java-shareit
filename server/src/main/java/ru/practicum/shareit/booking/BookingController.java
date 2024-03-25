package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.checker.Checker;
import ru.practicum.shareit.user.User;

import java.util.List;

@RestController
@Slf4j
@Validated
@AllArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {

    private BookingServiceImpl bookingService;
    private Checker checker;


    @PostMapping()
    public BookingDto create(@RequestBody @Validated BookingDto bookingDto,
                             @RequestHeader("X-Sharer-User-Id") Long userId) {
        User user = checker.checkerUserWithReturn(userId);
        return bookingService.createBooking(bookingDto, user);
    }

    @PatchMapping("/{id}")
    public BookingDto updatedBooking(@PathVariable("id") Long bookingId, @RequestParam Boolean approved,
                                     @RequestHeader("X-Sharer-User-Id") Long userId) {
        checker.checkerUser(userId);
        Booking booking = checker.checkerBooking(bookingId);
        return bookingService.approvedBooking(booking, approved, userId);
    }

    @GetMapping("/{id}")
    public BookingDto getBookingById(@PathVariable Long id,
                                     @RequestHeader("X-Sharer-User-Id") Long userId) {
        Booking booking = checker.checkerBooking(id);
        return bookingService.getBookingById(booking, userId);
    }

    @GetMapping()
    public List<BookingDto> getAllBookingsUser(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestParam(required = false, defaultValue = "ALL") String state,
            @RequestParam(name = "from", defaultValue = "0") int from,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        log.info("Выполнение запроса Get метода getAllBookingsUser ");
        checker.checkerUser(userId);
        return bookingService.getAllBookingByUser(userId, state, from, size);

    }

    @GetMapping("/owner")
    public List<BookingDto> getAllBookingsOwner(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestParam(required = false, defaultValue = "ALL") String state,
            @RequestParam(name = "from", defaultValue = "0") int from,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        log.info("Выполнение запроса Get метода getAllBookingsOwner ");
        checker.checkerUser(userId);
        return bookingService.getAllBookingByOwner(userId, state, from, size);
    }

}
