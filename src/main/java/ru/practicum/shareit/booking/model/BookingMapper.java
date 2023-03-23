package ru.practicum.shareit.booking.model;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;


public enum BookingMapper {
    INSTANT;

    public BookingDto toBookingDto(Booking booking, Item item, User user) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setStart(booking.getStart());
        bookingDto.setEnd(booking.getEnd());
        bookingDto.setItem(item);
        bookingDto.setBooker(user);
        bookingDto.setStatus(booking.getStatus());
        return bookingDto;
    }
}
