package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exeptions.ModelConflictException;
import ru.practicum.shareit.exeptions.ModelValidationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component("userValidation")
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


    public void userEmailValidation(User user) {
        if (!validateEmail(user.getEmail())) {
            throw new ModelValidationException(String.format("Почтовый адрес '%s' не может быть использован.",
                    user.getEmail()));
        } else if (!userRepository.getAllUsers().isEmpty()) {
            if (userRepository.getAllUsers().stream()
                    .map(User::getEmail).collect(Collectors.toList())
                    .contains(user.getEmail())) {
                throw new ModelConflictException(String.format("Почтовый адрес '%s' уже занят.",
                        user.getEmail()));
            }
        }

    }
}
