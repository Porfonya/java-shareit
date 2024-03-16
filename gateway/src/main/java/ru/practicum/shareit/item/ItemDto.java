package ru.practicum.shareit.item;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ItemDto {
    private Long id;
    @NotNull(message = "Имя не может быть пустым")
    @NotBlank(message = "Имя не может быть пустым")
    private String name;
    @NotNull(message = "Поле описание не может быть пустым")
    private String description;


    @NotNull(message = "Статус не может быть пустым")
    private Boolean available;
    private Long requestId;
    private ItemBooking lastBooking;
    private ItemBooking nextBooking;
    private List<CommentDto> comments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemDto)) return false;
        ItemDto itemDto = (ItemDto) o;
        return Objects.equals(id, itemDto.id) && Objects.equals(name, itemDto.name)
                && Objects.equals(description, itemDto.description)
                && Objects.equals(available, itemDto.available)
                && Objects.equals(requestId, itemDto.requestId)
                && Objects.equals(lastBooking, itemDto.lastBooking)
                && Objects.equals(nextBooking, itemDto.nextBooking)
                && Objects.equals(comments, itemDto.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, available, requestId, lastBooking, nextBooking, comments);
    }
}
