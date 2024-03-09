package ru.practicum.shareit.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.request.ItemRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemMapperTest {

    private Item expected;

    private Item expectedTwo;
    private ItemDto expectedDto;

    @BeforeEach
    void setUp() {
        expected = new Item(1L,
                "newItem",
                "newItemDescription",
                true,
                null,
                new ItemRequest(),
                null);

        expectedDto = new ItemDto(1L,
                "newItem",
                "newItemDescription",
                true,
                1L,
                null,
                null,
                new ArrayList<>());
        expectedTwo = new Item(2L,
                "newItemTwo",
                "newItemDescriptionTwo",
                true,
                null,
                null,
                null);
    }

    @Test
    void mapToItemDto() {
        ItemDto result = ItemMapper.mapToItemDto(expected);
        Assertions.assertThat(expected.getId()).isEqualTo(result.getId());
        Assertions.assertThat(expected.getName()).isEqualTo(result.getName());
        Assertions.assertThat(expected.getDescription()).isEqualTo(result.getDescription());
        Assertions.assertThat(expected.getAvailable()).isEqualTo(result.getAvailable());

    }

    @Test
    void mapToItemRequestDto() {
        ItemDto result = ItemMapper.mapToItemRequestDto(expected);
        Assertions.assertThat(expected.getId()).isEqualTo(result.getId());
        Assertions.assertThat(expected.getName()).isEqualTo(result.getName());
        Assertions.assertThat(expected.getDescription()).isEqualTo(result.getDescription());
        Assertions.assertThat(expected.getAvailable()).isEqualTo(result.getAvailable());
        Assertions.assertThat(expected.getItemRequest().getId()).isEqualTo(result.getRequestId());


    }

    @Test
    void mapToItem() {
        Item result = ItemMapper.mapToItem(expectedDto);
        Assertions.assertThat(expectedDto.getId()).isEqualTo(result.getId());
        Assertions.assertThat(expectedDto.getName()).isEqualTo(result.getName());
        Assertions.assertThat(expectedDto.getDescription()).isEqualTo(result.getDescription());
        Assertions.assertThat(expectedDto.getAvailable()).isEqualTo(result.getAvailable());
    }

    @Test
    void testMapToItemDto() {

        List<ItemDto> results = ItemMapper.mapToItemDto(List.of(expected, expectedTwo));
        assertEquals(results.size(), 2);
    }
}