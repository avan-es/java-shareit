package ru.practicum.shareit.item.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "items")
@Data
public class Item {
    @Id
    private Long id;

    @NotBlank @NotNull
    private String name;

    @NotBlank @NotNull
    private String description;

    @Column(name = "is_available")
    @NotBlank @NotNull
    private Boolean available;

    @Column(name = "owner_id")
    @PositiveOrZero
    private Long owner;

    @Column(name = "request_id")
    private Long request;
}
