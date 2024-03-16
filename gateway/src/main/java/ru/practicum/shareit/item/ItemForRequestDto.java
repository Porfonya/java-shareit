package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;


@Builder
@AllArgsConstructor
@Setter
@Getter
public class ItemForRequestDto {

    private Long id;
    private String name;
    private String description;
    private Long requestId;
    private Boolean available;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemForRequestDto)) return false;
        ItemForRequestDto that = (ItemForRequestDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(requestId, that.requestId) && Objects.equals(available, that.available);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, requestId, available);
    }
}
