package ru.practicum.shareit.user;

import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class UserDtoTest {
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Autowired
    private JacksonTester<UserDto> json;

    @Test
    void testSerialize() throws Exception {
        var dto = new UserDto(
                1L,
                "email@mail.ru",
                "User name"

        );

        var result = json.write(dto);

        AssertionsForInterfaceTypes.assertThat(result).hasJsonPath("$.id");
        AssertionsForInterfaceTypes.assertThat(result).hasJsonPath("$.email");
        AssertionsForInterfaceTypes.assertThat(result).hasJsonPath("$.name");


        AssertionsForInterfaceTypes.assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(dto.getId().intValue());
        AssertionsForInterfaceTypes.assertThat(result).extractingJsonPathStringValue("$.email").isEqualTo(dto.getEmail());
        AssertionsForInterfaceTypes.assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo(dto.getName());

    }


    @DisplayName("Email validation")
    @ParameterizedTest
    @CsvSource({
            "email, 1, Email должен быть валидным",
            "@email, 1, Email должен быть валидным",
            "email@, 1, Email должен быть валидным",
            "email@@dd, 1, Email должен быть валидным",
            "'ema il@dd', 1, Email должен быть валидным",
            "'name@email.com', 0, OK"
    })
    void emailValidation(String email, int expectSize, String expectedMessage) {
        if ("null".equals(email)) {
            email = null;
        }
        var dto = new UserDto();
        dto.setEmail(email);
        Set<ConstraintViolation<UserDto>> violations = validator.validate(dto);
        Assertions.assertEquals(expectSize, violations.size());

        if (!violations.isEmpty()) {
            assertThat(violations.iterator().next().getMessageTemplate())
                    .contains(expectedMessage);
        }
    }


}