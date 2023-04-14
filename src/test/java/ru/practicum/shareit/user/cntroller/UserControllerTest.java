package ru.practicum.shareit.user.cntroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jfr.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.shareit.exeptions.ErrorHandler;
import ru.practicum.shareit.exeptions.ModelValidationException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserMapper;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;


//@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    UserService userService;
    long userId = 1L;
    User user = new User();
    UserDto userDto = new UserDto();
    List<User> users = List.of();
    List<UserDto> userDtos = List.of();

    @BeforeEach
    void serUp() {
        user.setId(userId);
        user.setName("User1");
        user.setEmail("user1@mail.ru");

        userDto.setId(userId);
        userDto.setName("User1");
        userDto.setEmail("user1@mail.ru");
    }

    @Test
    void addUser() throws Exception {
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
    void updateUser() throws Exception{
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
    void getUsers() throws Exception{
        when(userService.getAllUsers()).thenReturn(userDtos);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());

        verify(userService).getAllUsers();
    }

    @Test
    void getUser() throws Exception{
        when(userService.getUserById(userId)).thenReturn(userDto);

        mockMvc.perform(get("/users/{id}", userId))
                .andExpect(status().isOk());

        verify(userService).getUserById(userId);
    }

    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(delete("/users/{id}", userId))
                .andExpect(status().isOk());
        verify(userService).deleteUser(userId);
    }
/*

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserController userController;

    private JacksonTester<User> jsonUser;

    private JacksonTester<UserDto> jsonUserDto;

    private JacksonTester<List<UserDto>> jsonUsersDto;


    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        // We would need this line if we would not use the MockitoExtension
        // MockitoAnnotations.initMocks(this);
        // Here we can't use @AutoConfigureJsonTesters because there isn't a Spring context
        JacksonTester.initFields(this, new ObjectMapper());
        // MockMvc standalone approach
        mvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new ErrorHandler())
                .build();
    }

    @Test
    @DisplayName("Add User - SUCCESS (Status.OK)")
    void addUser_whenInvoked_thenOK() throws Exception {
        User userForSave = new User();
        userForSave.setId(0L);
        userForSave.setName("User");
        userForSave.setEmail("user@email.ru");

        when(userController.addUser(userForSave)).thenReturn(UserMapper.INSTANT.toUserDto(userForSave));

        MockHttpServletResponse response = mvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON).content(
                                jsonUser.write(userForSave).getJson()
                        )).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), jsonUser.write(userForSave).getJson());
    }

    @Test
    @DisplayName("Add not valid User - FAIL (Status.BAD_REQUEST)")
    void addUser_whenUserNotValid_thenValidationException() throws Exception {
        User userForSave = new User();
        userForSave.setId(0L);
        userForSave.setName("User");

        MockHttpServletResponse response = mvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON).content(
                                jsonUser.write(userForSave).getJson()
                        )).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Get Users - SUCCESS (Status.OK)")
    void getUsers_whenInvoked_thenOK() throws Exception {

        List<UserDto> users = new ArrayList<>();
        for (int i = 0; i<5; i++) {
            UserDto user = new UserDto();
            user.setId(((long) i));
            user.setName("User" + i);
            user.setEmail("user" + i + "@mail.ru");
            users.add(user);
        }

        given(userService.getAllUsers())
                .willReturn(users);

        MockHttpServletResponse response = mvc.perform(
                get("/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), jsonUsersDto.write(users).getJson());
    }

    @Test
    @DisplayName("Update User - SUCCESS (Status.OK)")
    void updateUser_whenUserIsPresent_thenOK() throws Exception{

        MockHttpServletResponse response = mvc.perform(
                        get("/users/{userId}", 0L)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
    }*/
}