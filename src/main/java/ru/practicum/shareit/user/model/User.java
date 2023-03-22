package ru.practicum.shareit.user.model;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String email;

}
