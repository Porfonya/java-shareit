package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.Mapper.BookingMapper;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.state.BookingState;
import ru.practicum.shareit.booking.state.State;
import ru.practicum.shareit.checker.Checker;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.UnsupportedBookingStateException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.shareit.booking.state.BookingState.*;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final Checker checker;


    @Override
    @Transactional
    public BookingDto createBooking(BookingDto bookingDto, User user) {

        Item thisItem = checker.checkerItem(bookingDto.getItemId());
        if (user.getId().equals(thisItem.getOwner().getId())) {
            throw new NotFoundException("пользователь и владелец вещи не могут быть одним лицом");
        }
        if (bookingDto.getEnd().isBefore(bookingDto.getStart()) || bookingDto.getEnd().equals(bookingDto.getStart())) {
            throw new ValidationException("Неверное дата начала бронирования");
        }

        if (!thisItem.getAvailable()) {
            throw new ValidationException("Вещь не свободна");
        }
        bookingDto.setStatus(WAITING);
        bookingDto.setItem(thisItem);
        bookingDto.setBooker(user);
        Booking thisBooking = BookingMapper.mapToBooking(bookingDto);
        Booking result = bookingRepository.save(thisBooking);

        return BookingMapper.mapToBookingDto(result);
    }

    @Override
    @Transactional
    public BookingDto approvedBooking(Booking booking, Boolean approved, Long userId) {

        if (!booking.getStatus().equals(APPROVED)) {
            if (booking.getItem().getOwner().getId().equals(userId)) {
                if (!approved) {
                    booking.setStatus(REJECTED);
                } else {
                    booking.setStatus(BookingState.APPROVED);
                }

            } else {
                throw new NotFoundException("User not found for booking id");
            }
            return BookingMapper.mapToBookingDto(bookingRepository.save(booking));
        } else {
            throw new ValidationException("Status is APPROVED");
        }

    }

    @Override
    @Transactional(readOnly = true)
    public BookingDto getBookingById(Booking booking, Long userId) {
        if (booking.getBooker().getId().equals(userId) || booking.getItem().getOwner().getId().equals(userId)) {
            return BookingMapper.mapToBookingDto(booking);
        } else {
            throw new NotFoundException("Нет бронирования для этого пользователя");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingDto> getAllBookingByUser(Long userId, String status) {

        try {
            switch (State.valueOf(status)) {
                case CURRENT:
                    return BookingMapper.mapToBookingDto(
                            bookingRepository.findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(
                                    userId, LocalDateTime.now(), LocalDateTime.now()));
                case FUTURE:
                    return BookingMapper.mapToBookingDto(
                            bookingRepository.findAllByBookerIdAndStartAfterOrderByStartDesc(userId,
                                    LocalDateTime.now()));
                case PAST:
                    return BookingMapper.mapToBookingDto(
                            bookingRepository.findAllByBookerIdAndEndBeforeOrderByStartDesc(userId,
                                    LocalDateTime.now()));
                case REJECTED:
                    return BookingMapper.mapToBookingDto(
                            bookingRepository.findAllByBookerIdAndStatusOrderByStartDesc(userId, REJECTED));

                case WAITING:
                    return BookingMapper.mapToBookingDto(
                            bookingRepository.findAllByBookerIdAndStatusOrderByStartDesc(userId, WAITING));
                default:
                    return BookingMapper.mapToBookingDto(
                            bookingRepository.findAllByBookerIdOrderByStartDesc(userId));
            }
        } catch (IllegalArgumentException e) {
            throw new UnsupportedBookingStateException(String.format("Unknown state: %s", status));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingDto> getAllBookingByOwner(Long userId, String status) {
        try {
            switch (State.valueOf(status)) {
                case CURRENT:
                    return BookingMapper.mapToBookingDto(
                            bookingRepository.findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc(
                                    userId, LocalDateTime.now(), LocalDateTime.now()));
                case FUTURE:
                    return BookingMapper.mapToBookingDto(
                            bookingRepository.findAllByItemOwnerIdAndStartAfterOrderByStartDesc(
                                    userId, LocalDateTime.now()));

                case PAST:
                    return BookingMapper.mapToBookingDto(
                            bookingRepository.findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(
                                    userId, LocalDateTime.now()));
                case REJECTED:

                case WAITING:
                    return BookingMapper.mapToBookingDto(
                            bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(
                                    userId, BookingState.valueOf(status)));
                default:
                    return BookingMapper.mapToBookingDto(
                            bookingRepository.findAllByItemOwnerIdOrderByStartDesc(userId));
            }
        } catch (IllegalArgumentException e) {
            throw new UnsupportedBookingStateException(String.format("Unknown state: %s", status));
        }
    }

}
