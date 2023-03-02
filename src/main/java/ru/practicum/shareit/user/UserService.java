package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserValidation userValidation;



    public User addUser(User user) {
        userValidation.userValidation(user);
        return userRepository.addUser(user);
    }
}
