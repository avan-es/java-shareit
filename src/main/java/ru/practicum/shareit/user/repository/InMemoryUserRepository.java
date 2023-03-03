package ru.practicum.shareit.user.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("inMemoryUserRepository")
@Slf4j
public class InMemoryUserRepository implements UserRepository {

    private final Map<Long, User> users = new HashMap<>();

    private Long actualId = 0L;

    @Override
    public User addUser(User user) {
        user.setId(getId());
        users.put(user.getId(), user);
        log.info(String.format("Пользователь с ID %s успешно создан.", user.getId()));
        return user;
    }

    @Override
    public User getUserById(Long id) {
        return users.get(id);
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
        log.info(String.format("Пользователь с ID %s успешно обновлён.", userForUpdate.getId()));
        return userForUpdate;
    }

    @Override
    public void deleteUser(Long id) {
        users.remove(id);
        log.info(String.format("Пользователь с ID %s успешно удалён.", id));
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    private Long getId() {
        return ++actualId;
    }
}
