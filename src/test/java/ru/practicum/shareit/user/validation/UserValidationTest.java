package ru.practicum.shareit.user.validation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.practicum.shareit.exeptions.NotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserValidationTest {


    @InjectMocks
    UserValidation userValidation;

    @Mock
    UserValidation userValidationMock;

    @Mock
    UserRepository userRepository;

    @Test
    void isEmailValid() {
        User user = new User();
        user.setId(0L);
        user.setName("User");
        user.setEmail("user@mail.ru");

        userValidationMock.isEmailValid(user);
        verify(userValidationMock, times(1)).isEmailValid(user);
    }

    @Test
    void isPresent_SUCCESS() {
        User user = new User();
        user.setId(0L);
        user.setName("User");
        user.setEmail("user@mail.ru");

       when(userRepository.findById(any()))
                .thenReturn(Optional.ofNullable(user));

       userValidation.isPresent(user.getId());

        verify(userRepository, times(1)).getUserById(user.getId());

    }

    @Test
    void isPresent_FAIL() {
        User user = new User();
        user.setId(0L);
        user.setName("User");
        user.setEmail("user@mail.ru");

        doThrow(NotFoundException.class)
                .when(userRepository).findById(any());

        assertThrows(NotFoundException.class,
                () -> userRepository.findById(any()));

        verify(userRepository, never()).getById(user.getId());

    }

    @Test
    void emailValidationForExistUser() {
        User user = new User();
        user.setId(0L);
        user.setName("User");
        user.setEmail("user@mail.ru");

        when(userRepository.findByEmail(any()))
                .thenReturn(user);

        when(userRepository.getById(any()))
                .thenReturn(user);

        userValidation.emailValidationForExistUser(user);

        verify(userRepository, times(1)).getById(user.getId());

    }
}