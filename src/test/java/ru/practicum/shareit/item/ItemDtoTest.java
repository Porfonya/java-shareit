package ru.practicum.shareit.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.practicum.shareit.user.UserDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

@JsonTest
class ItemDtoTest {
    private Validator validator;
    @Autowired
    private JacksonTester<ItemDto> json;

    @Test
    void testSerialize() throws Exception {
        var dto = ItemDto.builder()
                .id(1L)
                .name("name")
                .description("description")
                .available(null)
                .requestId(null)
                .lastBooking(null)
                .nextBooking(null)
                .build();

        var result = json.write(dto);

        assertThat(result).hasJsonPath("$.id");
        assertThat(result).hasJsonPath("$.name");
        assertThat(result).hasJsonPath("$.description");
        assertThat(result).hasJsonPath("$.available");
        assertThat(result).hasJsonPath("$.requestId");
        assertThat(result).hasJsonPath("$.lastBooking");
        assertThat(result).hasJsonPath("$.nextBooking");



        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(dto.getId().intValue());
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo(dto.getName());
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo(dto.getDescription());
        assertThat(result).extractingJsonPathStringValue("$.available").isEqualTo(null);
        assertThat(result).extractingJsonPathStringValue("$.requestId").isEqualTo(null);
        assertThat(result).extractingJsonPathStringValue("$.lastBooking").isEqualTo(dto.getLastBooking());
        assertThat(result).extractingJsonPathStringValue("$.nextBooking").isEqualTo(dto.getNextBooking());



    }

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test()
    public void testValidationWhenNoNameThenThrowException() {
        ItemDto itemDto = new ItemDto();
        itemDto.setName("");

        Set<ConstraintViolation<ItemDto>> violations = validator.validate(itemDto);
        assertFalse(violations.isEmpty());
    }

    @Test()
    public void testValidationWhenNameBlankThenThrowException() {
        ItemDto itemDto = new ItemDto();
        itemDto.setName(" ");
        Set<ConstraintViolation<ItemDto>> violations = validator.validate(itemDto);
        assertFalse(violations.isEmpty());
    }

    @Test()
    public void testValidationWhenNoDescriptionThenThrowException() {
        ItemDto itemDto = new ItemDto();
        itemDto.setDescription("");

        Set<ConstraintViolation<ItemDto>> violations = validator.validate(itemDto);
        assertFalse(violations.isEmpty());
    }

}