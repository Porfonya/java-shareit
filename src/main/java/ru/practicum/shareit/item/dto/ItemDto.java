package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * TODO Sprint add-controllers.
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor

public class ItemDto {
    private Long id;
    @NotNull(message = "Имя не может быть пустым")
    @NotBlank(message = "Имя не может быть пустым")
    private String name;
    @NotNull(message = "Поле описание не может быть пустым")
    private String description;


    @NotNull(message = "Статус не может быть пустым")
    private Boolean available;
    private User owner;
    private Boolean isRequested;
}
