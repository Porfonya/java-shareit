package ru.practicum.shareit.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * TODO Sprint add-controllers.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    @Email(message = "Email должен быть валидным")
    @NotNull(message = "Email должен быть не пустым")
    private String email;

    @NotNull(message = "Имя не может быть пустым")
    private String name;


}
