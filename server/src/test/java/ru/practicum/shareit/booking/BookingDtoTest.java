package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
class BookingDtoTest {
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    @Autowired
    private JacksonTester<BookingDto> json;

    @Test
    void testSerialize() throws Exception {
        var dto = new BookingDto(
                1L,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(2L),
                BookingState.APPROVED,
                2L,
                1L,
                new User(),
                new Item()
        );

        var result = json.write(dto);
        assertThat(result).hasJsonPath("$.id");
        assertThat(result).hasJsonPath("$.start");
        assertThat(result).hasJsonPath("$.end");
        assertThat(result).hasJsonPath("$.status");
        assertThat(result).hasJsonPath("$.itemId");

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(dto.getId().intValue());
        assertThat(result).extractingJsonPathStringValue("$.start").startsWith(dto.getStart().format(dtf));
        assertThat(result).extractingJsonPathStringValue("$.end").startsWith(dto.getEnd().format(dtf));
        assertThat(result).extractingJsonPathStringValue("$.status").isEqualTo(String.valueOf(dto.getStatus()));
        assertThat(result).extractingJsonPathNumberValue("$.itemId").isEqualTo(dto.getItemId().intValue());
    }
}