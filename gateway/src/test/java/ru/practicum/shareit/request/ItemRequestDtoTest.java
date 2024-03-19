package ru.practicum.shareit.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@JsonTest
class ItemRequestDtoTest {
    private Validator validator;
    @Autowired
    private JacksonTester<ItemRequestDto> json;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Test
    void testSerialize() throws Exception {
        var dto = ItemRequestDto.builder()
                .id(1L)
                .description("desc")
                .requester(null)
                .created(LocalDateTime.now())
                .items(null)
                .build();

        var result = json.write(dto);

        assertThat(result).hasJsonPath("$.id");
        assertThat(result).hasJsonPath("$.description");
        assertThat(result).hasJsonPath("$.requester");
        assertThat(result).hasJsonPath("$.created");
        assertThat(result).hasJsonPath("$.items");

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(dto.getId().intValue());
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo(dto.getDescription());
        assertThat(result).extractingJsonPathStringValue("$.requester").isEqualTo(null);
        assertThat(result).extractingJsonPathStringValue("$.created").startsWith(dto.getCreated().format(dtf));
        assertThat(result).extractingJsonPathStringValue("$.items").isEqualTo(null);

    }

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test()
    public void testValidationWhenNoNameThenThrowException() {
        ItemRequestDto requestDto = new ItemRequestDto();
        requestDto.setDescription("");
        Set<ConstraintViolation<ItemRequestDto>> violations = validator.validate(requestDto);
        assertFalse(violations.isEmpty());
    }

    @Test()
    public void testValidationWhenBlankThenThrowException() {
        ItemRequestDto requestDto = new ItemRequestDto();
        requestDto.setDescription(" ");
        Set<ConstraintViolation<ItemRequestDto>> violations = validator.validate(requestDto);
        assertFalse(violations.isEmpty());
    }

}