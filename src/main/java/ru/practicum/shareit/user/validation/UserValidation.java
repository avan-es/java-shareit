package ru.practicum.shareit.user.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exeptions.ModelConflictException;
import ru.practicum.shareit.exeptions.ModelValidationException;
import ru.practicum.shareit.exeptions.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserMapper;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component("userValidation")
@Slf4j
public class UserValidation {

    @Autowired
    @Qualifier("inMemoryUserRepository")
    private UserRepository userRepository;

    private Pattern pattern;
    private Matcher matcher;
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public UserValidation() {
        this.pattern = Pattern.compile(EMAIL_PATTERN);;
    }

    private boolean validateEmail(final String hex) {
        matcher = pattern.matcher(hex);
        return matcher.matches();
    }


    public void emailValidation(UserDto user) {
        if (!validateEmail(user.getEmail())) {
            log.error(String.format("Пользователь не создан. Ошибка в адресе почты: %s.", user.getEmail()));
            throw new ModelValidationException(String.format("Почтовый адрес '%s' не может быть использован.",
                    user.getEmail()));
        } else if (!userRepository.getAllUsers().isEmpty()) {
            if (userRepository.getAllUsers().stream()
                    .map(UserDto::getEmail).collect(Collectors.toList())
                    .contains(user.getEmail())) {
                log.error(String.format("Пользователь не создан. Почта %s уже занята.", user.getEmail()));
                throw new ModelConflictException(String.format("Почтовый адрес '%s' уже занят.",
                        user.getEmail()));
            }
        }

    }

    public void emailIsFree(UserDto user) {
        if (user.getEmail() != null) {
            if (!validateEmail(user.getEmail())) {
                log.error(String.format("Пользователь не создан. Ошибка в адресе почты: %s.", user.getEmail()));
                throw new ModelValidationException(String.format("Почтовый адрес '%s' не может быть использован.",
                        user.getEmail()));
            }
            if (!userRepository.getAllUsers().isEmpty()) {
                User userForUpdate = userRepository.getUserById(user.getId());
                if (!userForUpdate.getEmail().equals(user.getEmail())) {
                    if (userRepository.getAllUsers().stream()
                            .map(UserDto::getEmail).collect(Collectors.toList())
                            .contains(user.getEmail())) {
                        log.error(String.format("Пользователь не создан. Почта %s уже занята.", user.getEmail()));
                        throw new ModelConflictException(String.format("Почтовый адрес '%s' уже занят.",
                                user.getEmail()));
                    }
                }
            }
        }

    }

    public void isPresent (Long userId) {
        if (!userRepository.getAllUsers().stream()
                .anyMatch(user -> user.getId().equals(userId))) {
            log.error(String.format("Пользователь с ID %s не существует.", userId));
            throw new NotFoundException(String.format("Пользователь с ID %d не найден.", userId));
        };
    }
}
