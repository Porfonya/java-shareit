package ru.practicum.shareit.booking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Page<Booking> findAllByBookerIdOrderByStartDesc(Long userId, Pageable page);

    Page<Booking> findAllByItemOwnerIdOrderByStartDesc(Long userId, Pageable page);

    Page<Booking> findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(Long userId,
                                                                             LocalDateTime start, LocalDateTime end, Pageable page);

    Page<Booking> findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc(Long userId,
                                                                                LocalDateTime start, LocalDateTime end, Pageable page);

    Page<Booking> findAllByBookerIdAndStartAfterOrderByStartDesc(Long userId, LocalDateTime start, Pageable page);

    Page<Booking> findAllByItemOwnerIdAndStartAfterOrderByStartDesc(Long userId, LocalDateTime start, Pageable page);

    Page<Booking> findAllByBookerIdAndEndBeforeOrderByStartDesc(Long userId, LocalDateTime end, Pageable page);

    Page<Booking> findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(Long userId, LocalDateTime end, Pageable page);

    Page<Booking> findAllByBookerIdAndStatusOrderByStartDesc(Long userId, BookingState status, Pageable page);

    Page<Booking> findAllByItemOwnerIdAndStatusOrderByStartDesc(Long userId, BookingState status, Pageable page);

    List<Booking> findTop2ByItemIdAndStatusOrderByEndAsc(Long userId, BookingState status);

    List<Booking> findBookingsByBookerIdAndItemIdAndStatusAndEndBefore(Long userId, Long itemId,
                                                                       BookingState state, LocalDateTime end);

    Booking findTop1ByItemIdAndStartBeforeOrderByStartDesc(Long itemId, LocalDateTime start);

    Booking findTop1ByItemIdAndStartAfterOrderByStartAsc(Long itemId, LocalDateTime start);

}