package ru.practicum.shareit.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemForRequestDto;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestMapperTest {
    private ItemRequest newItemRequest;
    private ItemRequestDto newItemRequestDto;
    private Item newItem;
    Clock clock = Clock.fixed(Instant.parse("2014-12-22T10:15:30.00Z"), ZoneId.of("UTC"));

    @BeforeEach
    void setUp() {
        newItemRequest = ItemRequest.builder()
                .id(1L)
                .description("description")
                .requester(1L)
                .created(LocalDateTime.now(clock))
                .build();

        newItemRequestDto = ItemRequestDto.builder()
                .id(1L)
                .description("description")
                .requester(1L)
                .created(LocalDateTime.now(clock))
                .build();
        newItem = Item.builder()
                .id(1L)
                .name("newItem")
                .description("description")
                .itemRequest(newItemRequest)
                .available(true)
                .build();


    }

    @Test
    void mapToRequestDto() {

        ItemRequestDto result = RequestMapper.mapToRequestDto(newItemRequest);

        assertEquals(result.getId(), newItemRequestDto.getId());
        assertEquals(result.getDescription(), newItemRequestDto.getDescription());
        assertEquals(result.getRequester(), newItemRequestDto.getRequester());
        assertEquals(result.getCreated(), newItemRequestDto.getCreated());

    }

    @Test
    void mapToForRequestDto() {
        ItemForRequestDto result = RequestMapper.mapToForRequestDto(newItem);
        assertEquals(result.getId(), newItem.getId());
        assertEquals(result.getName(), newItem.getName());
        assertEquals(result.getDescription(), newItem.getDescription());
        assertEquals(result.getRequestId(), newItem.getItemRequest().getId());
        assertEquals(result.getAvailable(), newItem.getAvailable());
    }

    @Test
    void mapToForRequestDtoList() {
        List<ItemForRequestDto> result = RequestMapper.mapToForRequestDtoList(List.of(newItem));
        assertEquals(result.size(), 1);
    }

    @Test
    void itemRequestDtoList() {
        List<ItemRequestDto> result = RequestMapper.itemRequestDtoList(List.of(newItemRequest));
        assertEquals(result.size(), 1);
    }
}