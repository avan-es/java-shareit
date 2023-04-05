package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public BookingDto addBooking(@RequestBody Booking booking,
                                 @RequestHeader(value = "X-Sharer-User-Id") Long userId) {
        return bookingService.saveBooking(booking, userId);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto updateItem(@RequestHeader (value = "X-Sharer-User-Id") Long userId,
                              @PathVariable Long bookingId,
                              @RequestParam Boolean approved) {
        return bookingService.acceptBooking(userId, bookingId, approved);
    }

    @GetMapping("/all")
    public List<Booking> getAllBooking() {
        return bookingService.getAllItems();
    }

    @GetMapping
    public List<BookingDto> getAllUsersBookings(@RequestHeader(value = "X-Sharer-User-Id") Long userId,
                                          @RequestParam(name = "state", defaultValue = "ALL") String state) {
        return bookingService.getUsersBookingByState(userId, state);
    }

    @GetMapping("/owner")
    public List<BookingDto> getAllOwnerBookings(@RequestHeader(value = "X-Sharer-User-Id") Long userId,
                                             @RequestParam(name = "state", defaultValue = "ALL") String state) {
        return bookingService.getOwnerBookingByState(userId, state);
    }

    @GetMapping("/{bookingId}")
    public BookingDto getBooking(@PathVariable Long bookingId,
                                 @RequestHeader(value = "X-Sharer-User-Id") Long userId) {
        return bookingService.getBooking(bookingId, userId);
    }
}