package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.dto.ItemGatewayBookingDto;
import ru.practicum.shareit.user.dto.UserBookingGatewayDto;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookingGatewayDto {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private UserBookingGatewayDto booker;
    private String status;
    private ItemGatewayBookingDto item;


    public BookingGatewayDto(Long bookingId, LocalDateTime start, LocalDateTime end, String status, Long bookerId,
                      Long itemId, String itemName) {
        this.id = bookingId;
        this.start = start;
        this.end = end;
        this.booker = new UserBookingGatewayDto(bookerId);
        this.status = status;
        this.item = new ItemGatewayBookingDto(itemId, itemName);
    }
}
