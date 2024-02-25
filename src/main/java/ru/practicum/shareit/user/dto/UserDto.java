package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
@Validated
public class UserDto {
    private Long id;

    @Email(message = "Email должен быть валидным")
    private String email;

    private String name;

}
