package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
public class UserDto {
    private Long id;

    @Email(message = "Email должен быть валидным")
    private String email;

    private String name;

}
