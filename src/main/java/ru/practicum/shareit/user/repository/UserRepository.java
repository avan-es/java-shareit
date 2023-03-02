package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserRepository {

    UserDto addUser(User user);

    User getUserById(Long id);

    UserDto updateUser(UserDto userDto);

    void deleteUser(Long id);

    List<UserDto> getAllUsers();
}
