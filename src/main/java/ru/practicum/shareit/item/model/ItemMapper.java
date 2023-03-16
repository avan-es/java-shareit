package ru.practicum.shareit.item.model;

import ru.practicum.shareit.item.dto.ItemDto;

public enum ItemMapper {
    INSTANT;

    public ItemDto toItemDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getAvailable());
        return itemDto;
    }
}
