package ru.practicum.shareit.booking.dto;

import lombok.*;
import ru.practicum.shareit.booking.state.BookingState;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Setter
@Getter
public class BookingDto {

    private Long id;

    @NotNull
    @FutureOrPresent
    private LocalDateTime start;
    @NotNull
    @Future
    private LocalDateTime end;
    private BookingState status;
    private Long bookerId;
    private Long itemId;
    private User booker;
    private Item item;
}
