package ru.practicum.shareit.user.validation;

import org.junit.jupiter.api.BeforeEach;
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
    private UserValidation userValidation;

    @Mock
    private UserValidation userValidationMock;

    @Mock
    private UserRepository userRepository;

    private User user = new User();

    @BeforeEach
    void setUp() {
        user.setId(0L);
        user.setName("User");
        user.setEmail("user@mail.ru");
    }

    @Test
    void isEmailValid() {
        userValidationMock.isEmailValid(user);
        verify(userValidationMock, times(1)).isEmailValid(user);
    }

    @Test
    void isPresent_SUCCESS() {
       when(userRepository.findById(any()))
                .thenReturn(Optional.ofNullable(user));

       userValidation.isPresent(user.getId());

        verify(userRepository, times(1)).getUserById(user.getId());

    }

    @Test
    void isPresent_FAIL() {

        doThrow(NotFoundException.class)
                .when(userRepository).findById(any());

        assertThrows(NotFoundException.class,
                () -> userRepository.findById(any()));

        verify(userRepository, never()).getById(user.getId());

    }

    @Test
    void emailValidationForExistUser() {
        when(userRepository.findByEmail(any()))
                .thenReturn(user);

        when(userRepository.getById(any()))
                .thenReturn(user);

        userValidation.emailValidationForExistUser(user);

        verify(userRepository, times(1)).getById(user.getId());

    }
}