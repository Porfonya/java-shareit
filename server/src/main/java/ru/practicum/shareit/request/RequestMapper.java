package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemForRequestDto;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class RequestMapper {
    public static ItemRequestDto mapToRequestDto(ItemRequest itemRequest) {
        return new ItemRequestDto.ItemRequestDtoBuilder()
                .id(itemRequest.getId())
                .description(itemRequest.getDescription())
                .requester(itemRequest.getRequester())
                .created(itemRequest.getCreated())
                .build();
    }

    public static ItemForRequestDto mapToForRequestDto(Item item) {
        return new ItemForRequestDto(item.getId(), item.getName(), item.getDescription(),
                item.getItemRequest().getId(), item.getAvailable());

    }

    public static List<ItemForRequestDto> mapToForRequestDtoList(Iterable<Item> items) {
        List<ItemForRequestDto> itemForRequestDtos = new ArrayList<>();
        for (Item item : items) {
            itemForRequestDtos.add(RequestMapper.mapToForRequestDto(item));
        }
        return itemForRequestDtos;
    }

    public static List<ItemRequestDto> itemRequestDtoList(Iterable<ItemRequest> requests) {
        List<ItemRequestDto> itemRequestDtos = new ArrayList<>();
        for (ItemRequest request : requests) {
            itemRequestDtos.add(mapToRequestDto(request));
        }
        return itemRequestDtos;
    }
}
