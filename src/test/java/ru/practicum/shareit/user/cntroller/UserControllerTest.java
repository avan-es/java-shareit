package ru.practicum.shareit.user.cntroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserService userService;

    @Test
    void addUser() throws Exception {
        long userId = 1L;

        User user = new User();
        user.setId(userId);
        user.setName("User1");
        user.setEmail("user1@mail.ru");

        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setName("User1");
        userDto.setEmail("user1@mail.ru");

        when(userService.saveUser(user)).thenReturn(userDto);
        String result = mockMvc.perform(post("/users", user)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(user), result);
    }

    @Test
    void updateUser() throws Exception {
        long userId = 1L;
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setName("User1");
        userDto.setEmail("user1@mail.ru");

        when(userService.updateUser(userDto)).thenReturn(userDto);
        String result = mockMvc.perform(patch("/users/{id}", userId, userDto)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(userDto), result);
    }

    @Test
    void getUsers() throws Exception {
        List<UserDto> userDtos = List.of();

        when(userService.getAllUsers()).thenReturn(userDtos);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());

        verify(userService).getAllUsers();
    }

    @Test
    void getUser() throws Exception {
        long userId = 1L;
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setName("User1");
        userDto.setEmail("user1@mail.ru");

        when(userService.getUserById(userId)).thenReturn(userDto);

        mockMvc.perform(get("/users/{id}", userId))
                .andExpect(status().isOk());

        verify(userService).getUserById(userId);
    }

    @Test
    void deleteUser() throws Exception {
        long userId = 1L;

        mockMvc.perform(delete("/users/{id}", userId))
                .andExpect(status().isOk());
        verify(userService).deleteUser(userId);
    }

}