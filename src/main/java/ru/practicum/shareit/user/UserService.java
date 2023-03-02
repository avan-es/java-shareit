package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
