package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByBookerIdOrderByStartDesc(Long userId);

    List<Booking> findAllByItemOwnerIdOrderByStartDesc(Long userId);

    List<Booking> findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(Long userId,
                                                                             LocalDateTime start, LocalDateTime end);

    List<Booking> findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc(Long userId,
                                                                                LocalDateTime start, LocalDateTime end);

    List<Booking> findAllByBookerIdAndStartAfterOrderByStartDesc(Long userId, LocalDateTime start);

    List<Booking> findAllByItemOwnerIdAndStartAfterOrderByStartDesc(Long userId, LocalDateTime start);

    List<Booking> findAllByBookerIdAndEndBeforeOrderByStartDesc(Long userId, LocalDateTime end);

    List<Booking> findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(Long userId, LocalDateTime end);

    List<Booking> findAllByBookerIdAndStatusOrderByStartDesc(Long userId, BookingState status);

    List<Booking> findAllByItemOwnerIdAndStatusOrderByStartDesc(Long userId, BookingState status);

    List<Booking> findTop2ByItemIdAndStatusOrderByEndAsc(Long userId, BookingState status);

    List<Booking> findBookingsByBookerIdAndItemIdAndStatusAndEndBefore(Long userId, Long itemId,
                                                                       BookingState state, LocalDateTime end);

    Booking findTop1ByItemIdAndStartBeforeOrderByStartDesc(Long itemId, LocalDateTime start);

    Booking findTop1ByItemIdAndStartAfterOrderByStartAsc(Long itemId, LocalDateTime start);

}