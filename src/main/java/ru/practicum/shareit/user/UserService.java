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
        userValidation.userEmailValidation(UserMapper.toUserDto(user));
        return userRepository.addUser(user);
    }

    public List<User> getUsers() {
        return userRepository.getAllUsers();
    }
}
