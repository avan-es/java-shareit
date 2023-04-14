package ru.practicum.shareit.booking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.enums.BookingState;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingMapper;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.dto.ItemBookingDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.dto.UserBookingDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserMapper;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookingController.class)
class BookingControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookingService bookingService;
    @MockBean
    private UserService userService;
    @MockBean
    private ItemService itemService;

    private Long ownerId = 0L;
    private Long bookerId = 0L;


    private Long itemId = 0L;

    private Long bookingId = 0L;

    private Booking booking= new Booking();
    private BookingDto bookingDto = new BookingDto();

    private ItemBookingDto itemBookingDto = new ItemBookingDto(itemId, "Item");

    private UserBookingDto userBookingDto = new UserBookingDto(bookerId);

    private User user = new User();

    private UserDto userDto = new UserDto();

    private PageRequest pageRequest = PageRequest.of(0, 10);


    @BeforeEach
    void setUp() {
        booking.setId(0L);
        booking.setBookerId(bookerId);
        booking.setStatus(String.valueOf(BookingState.WAITING));
        booking.setStart(LocalDateTime.now());
        booking.setEnd(LocalDateTime.now().plusDays(1));
        booking.setItemId(0L);

        bookingDto = BookingMapper.INSTANT.toBookingDto(booking, itemBookingDto, userBookingDto);

        user.setId(ownerId);
        user.setName("User");
        user.setEmail("user@meil.ru");

        userDto = UserMapper.INSTANT.toUserDto(user);
    }

    @Test
    void addBooking() throws Exception {
        when(bookingService.saveBooking(booking, bookerId))
                .thenReturn(bookingDto);

        mockMvc.perform(post("/bookings", bookerId, bookingDto)
                        .header("X-Sharer-User-Id", bookerId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(bookingDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void updateItem() throws Exception {
        when(bookingService.acceptBooking(bookingId, ownerId, true)).thenReturn(bookingDto);
        String result = mockMvc.perform(MockMvcRequestBuilders.patch("/bookings/{bookingId}", bookingId)
                        .header("X-Sharer-User-Id", ownerId)
                        .param("approved", "true")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(bookingDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertEquals(objectMapper.writeValueAsString(bookingDto), result);
        verify(bookingService).acceptBooking(bookingId, ownerId, true);
    }

    @Test
    void getAllBooking() throws Exception{
        List<Booking> bookingDtos = List.of();
        when(bookingService.getAllItems()).thenReturn(bookingDtos);

        mockMvc.perform(get("/bookings/all", bookingId)
                        .header("X-Sharer-User-Id", bookerId))
                .andExpect(status().isOk());

        verify(bookingService).getAllItems();
    }

    @Test
    void getAllUsersBookings() throws Exception{
        List<BookingDto> bookingDtos = List.of();
        when(userService.getUserById(bookerId)).thenReturn(userDto);
        when(bookingService.getBookingByState(bookerId, BookingState.REJECTED.toString(), pageRequest, false)).thenReturn(bookingDtos);

        mockMvc.perform(get("/bookings")
                        .header("X-Sharer-User-Id", bookerId)
                        .param("state", "REJECTED")
                        .param("size", "10")
                        .param("from", "0"))
                .andExpect(status().isOk());

        verify(bookingService).getBookingByState(bookerId, BookingState.REJECTED.toString(), pageRequest, false);
    }

    @Test
    void getAllOwnerBookings() throws Exception {
        List<BookingDto> bookingDtos = List.of();
        when(userService.getUserById(ownerId)).thenReturn(userDto);
        when(bookingService.getBookingByState(ownerId, BookingState.REJECTED.toString(), pageRequest, true)).thenReturn(bookingDtos);

        mockMvc.perform(get("/bookings/owner")
                        .header("X-Sharer-User-Id", ownerId)
                        .param("state", "REJECTED")
                        .param("size", "10")
                        .param("from", "0"))
                .andExpect(status().isOk());

        verify(bookingService).getBookingByState(ownerId, BookingState.REJECTED.toString(), pageRequest, true);
    }

    @Test
    void getBooking() throws Exception {
        when(bookingService.getBooking(bookingId, bookerId)).thenReturn(bookingDto);

        mockMvc.perform(get("/bookings/{bookingId}", bookingId)
                        .header("X-Sharer-User-Id", bookerId))
                .andExpect(status().isOk());

        verify(bookingService).getBooking(bookingId, bookerId);
    }
}