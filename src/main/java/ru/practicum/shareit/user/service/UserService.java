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



    public UserDto addUser(User user) {
        userValidation.emailValidationForNewUser(UserMapper.toUserDto(user));
        return userRepository.addUser(user);
    }

    public List<UserDto> getUsers() {
        return userRepository.getAllUsers();
    }

    public UserDto updateUser(UserDto userDto) {
        userValidation.isPresent(userDto.getId());
        userValidation.emailValidationForExistUser(userDto);
        return userRepository.updateUser(userDto);
    }

    public UserDto getUserById(Long userId) {
        userValidation.isPresent(userId);
        return UserMapper.toUserDto(userRepository.getUserById(userId));
    }

    public void deleteUser(Long userId) {
        userValidation.isPresent(userId);
        userRepository.deleteUser(userId);
    }
}
