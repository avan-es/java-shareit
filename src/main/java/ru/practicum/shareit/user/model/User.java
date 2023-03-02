package ru.practicum.shareit.user.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class User {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String email;
}