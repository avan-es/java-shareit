package ru.practicum.shareit.user.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.dto.UserBookingDto;
import ru.practicum.shareit.user.dto.UserDto;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {
    private User user = new User();

    private UserDto userDto = new UserDto();

    private User userFromDto = new User();

    private UserDto userDtoFromUser = new UserDto();

    private UserBookingDto userBookingDto = new UserBookingDto();

    @BeforeEach
    void serUp() {
        user.setId(0L);
        user.setName("User1");
        user.setEmail("user1@mail.ru");

        userDto.setId(0L);
        userDto.setName("User1");
        userDto.setEmail("user1@mail.ru");
    }

    @Test
    void toUserDto() {
        userDtoFromUser = UserMapper.INSTANT.toUserDto(user);
        assertAll(
                () -> assertEquals(userDtoFromUser.getId(), user.getId()),
                () -> assertEquals(userDtoFromUser.getName(), user.getName()),
                () -> assertEquals(userDtoFromUser.getEmail(), user.getEmail())
        );
    }

    @Test
    void toUserBookingDto() {
        userBookingDto = UserMapper.INSTANT.toUserBookingDto(user);
        assertEquals(userBookingDto.getId(), user.getId());
    }

    @Test
    void toUser() {
        userFromDto = UserMapper.INSTANT.toUser(userDto);
        assertAll(
                () -> assertEquals(userFromDto.getId(), userDto.getId()),
                () -> assertEquals(userFromDto.getName(), userDto.getName()),
                () -> assertEquals(userFromDto.getEmail(), userDto.getEmail())
        );
    }
}