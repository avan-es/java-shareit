package ru.practicum.shareit.item.model;

import ru.practicum.shareit.item.dto.ItemBookingDto;
import ru.practicum.shareit.item.dto.ItemBookingForOwnerDto;
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

    public ItemBookingDto toItemBookingDto(Item item) {
        ItemBookingDto itemBookingDto = new ItemBookingDto();
        itemBookingDto.setId(item.getId());
        itemBookingDto.setName(item.getName());
        return itemBookingDto;
    }

    public ItemBookingForOwnerDto toItemBookingForOwnerDto(Item item) {
        ItemBookingForOwnerDto itemBookingForOwnerDto = new ItemBookingForOwnerDto();
        itemBookingForOwnerDto.setId(item.getId());
        itemBookingForOwnerDto.setName(item.getName());
        return itemBookingForOwnerDto;
    }
}
