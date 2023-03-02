package ru.practicum.shareit.user.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component("inMemoryUserRepository")
@Slf4j
public class InMemoryUserRepository implements UserRepository {

    private final List<User> users = new ArrayList<>();

    private Long actualId = 0L;

    @Override
    public UserDto addUser(User user) {
        user.setId(getId());
        users.add(user);
        log.info(String.format("Пользователь с ID %s успешно создан.", user.getId()));
        return UserMapper.toUserDto(user);
    }

    @Override
    public User getUserById(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
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
        return UserMapper.toUserDto(userForUpdate);
    }

    @Override
    public void deleteUser(Long id) {
        users.remove(getUserById(id));
        log.info(String.format("Пользователь с ID %s успешно удалён.", id));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return users.stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    private Long getId() {
        return ++actualId;
    }
}
