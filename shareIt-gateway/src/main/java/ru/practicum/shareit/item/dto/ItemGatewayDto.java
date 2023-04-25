package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.booking.dto.BookingInfoGatewayDto;
import ru.practicum.shareit.item.comment.CommentGatewayDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemGatewayDto {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private LocalDateTime lastBookingDate;
    private LocalDateTime nextBookingDate;
    private BookingInfoGatewayDto lastBooking;
    private BookingInfoGatewayDto nextBooking;
    private Long requestId;
    private List<CommentGatewayDto> comments = new ArrayList<>();
}
