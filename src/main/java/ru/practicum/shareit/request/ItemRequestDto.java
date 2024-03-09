package ru.practicum.shareit.request;

import lombok.*;
import ru.practicum.shareit.item.ItemForRequestDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class ItemRequestDto {
    private Long id;

    @NotNull(message = "Описание запроса вещи не может быть null.")
    @NotBlank(message = "Описание запроса вещи не может быть пустым.")
    private String description;

    private Long requester;
    private LocalDateTime created;
    private List<ItemForRequestDto> items;
}
