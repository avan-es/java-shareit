package ru.practicum.shareit.user.cntroller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserController userController;

    @Test
    void getUsers_whenInvoked_thenOK() {
        List<UserDto> expectedUsers = List.of(new UserDto());
        Mockito
                .when(userService.getAllUsers())
                .thenReturn(expectedUsers);

        List<UserDto> response = userController.getUsers();

//        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedUsers, response);
    }
}