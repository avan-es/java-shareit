package ru.practicum.shareit.request.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.item.dto.ItemDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class ItemResponseForRequestDto {
    private Long id;
    private Long requestId;
    private String description;
    private LocalDateTime created;
    private List<ItemDto> items = new ArrayList<>();
}
