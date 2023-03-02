package ru.practicum.shareit.user;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("inMemoryUserRepository")
public class InMemoryUserRepository implements UserRepository{

    private final List<User> users = new ArrayList<>();

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

    }

    @Override
    public List<User> getAllUsers() {
        return users;
    }

    private Long getId() {
        Long lastId = users.stream()
                .mapToLong(User::getId)
                .max()
                .orElse(0);
        return lastId + 1;
    }
}
