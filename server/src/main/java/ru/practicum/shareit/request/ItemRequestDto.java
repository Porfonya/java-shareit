package ru.practicum.shareit.request;

import lombok.*;
import ru.practicum.shareit.item.ItemForRequestDto;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ItemRequestDto {
    private Long id;

    private String description;

    private Long requester;
    private LocalDateTime created;
    private List<ItemForRequestDto> items;
}
