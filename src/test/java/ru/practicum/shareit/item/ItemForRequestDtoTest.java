package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.practicum.shareit.booking.BookingDto;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@JsonTest
class ItemForRequestDtoTest {
    @Autowired
    private JacksonTester<ItemForRequestDto> json;

    @Test
    void testSerialize() throws Exception {
        var dto = ItemForRequestDto.builder()
                .id(1L)
                .name("name")
                .description("desc")
                .requestId(null)
                .available(null)
                .build();

        var result = json.write(dto);

        assertThat(result).hasJsonPath("$.id");
        assertThat(result).hasJsonPath("$.name");
        assertThat(result).hasJsonPath("$.description");
        assertThat(result).hasJsonPath("$.requestId");
        assertThat(result).hasJsonPath("$.available");
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(dto.getId().intValue());
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo(dto.getName());
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo(dto.getDescription());
        assertThat(result).extractingJsonPathStringValue("$.requestId").isEqualTo(null);
        assertThat(result).extractingJsonPathStringValue("$.available").isEqualTo(null);
    }

}