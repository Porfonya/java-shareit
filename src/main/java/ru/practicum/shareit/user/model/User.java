package ru.practicum.shareit.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Setter
@Getter
@Table(name = "USERS", schema = "public")
public class User {
    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @NotNull(message = "Имя не может быть пустым")
    @Column(name = "name", nullable = false)
    private String name;


}
