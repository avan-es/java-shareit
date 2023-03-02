package ru.practicum.shareit.user;

import java.util.List;

public interface UserRepository {
    User addUser (User user);
    User getUserById(Long id);
    User updateUser(UserDto userDto);
    void deleteUser(Long id);
    List<User> getAllUsers();
}
