package ru.practicum.shareit.item.model;

import lombok.Data;
import ru.practicum.shareit.request.ItemRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class Item {
    private Long id;
    @NotBlank @NotNull
    private String name;
    @NotBlank @NotNull
    private String description;
    @NotBlank @NotNull
    private Boolean available;
    @PositiveOrZero
    private Long owner;
    private ItemRequest request;
}
