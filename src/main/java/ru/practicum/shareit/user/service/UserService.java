package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.validation.UserValidation;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserMapper;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserValidation userValidation;



    public User addUser(User user) {
        userValidation.emailValidation(UserMapper.toUserDto(user));
        return userRepository.addUser(user);
    }

    public List<User> getUsers() {
        return userRepository.getAllUsers();
    }

    public User updateUser(UserDto userDto, Long userId) {
        userValidation.isPresent(userId);
        userDto.setId(userId);
        userValidation.emailIsFree(userDto);
        return userRepository.updateUser(userDto);
    }

    public User getUserById(Long userId) {
        userValidation.isPresent(userId);
        return userRepository.getUserById(userId);
    }

    public void deleteUser(Long userId) {
        userValidation.isPresent(userId);
        userRepository.deleteUser(userId);
    }
}
