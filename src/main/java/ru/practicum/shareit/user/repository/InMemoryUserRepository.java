package ru.practicum.shareit.user.repository;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;

@Component("inMemoryUserRepository")
public class InMemoryUserRepository implements UserRepository {

    private final List<User> users = new ArrayList<>();

    private Long actualId = 0L;

    @Override
    public User addUser(User user) {
        user.setId(getId());
        users.add(user);
        return user;
    }

    @Override
    public User getUserById(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    @Override
    public User updateUser(UserDto userDto) {
        User userForUpdate = getUserById(userDto.getId());
        if (userDto.getName() != null) {
            if (!userDto.getName().isBlank()) {
                userForUpdate.setName(userDto.getName());
            }
        }
        if (userDto.getEmail() != null) {
            userForUpdate.setEmail(userDto.getEmail());
        }
        return userForUpdate;
    }

    @Override
    public void deleteUser(Long id) {
        users.remove(getUserById(id));
    }

    @Override
    public List<User> getAllUsers() {
        return users;
    }

    private Long getId() {
        return ++actualId;
    }
}
