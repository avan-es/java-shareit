package ru.practicum.shareit.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String email;
}
