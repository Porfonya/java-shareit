package ru.practicum.shareit.booking;

import lombok.*;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Setter
@Getter
public class BookingDto {

    private Long id;

    private LocalDateTime start;
    private LocalDateTime end;
    private BookingState status;
    private Long bookerId;
    private Long itemId;
    private User booker;
    private Item item;
}
