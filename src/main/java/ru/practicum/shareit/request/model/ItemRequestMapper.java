package ru.practicum.shareit.request.model;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemResponseForRequestDto;

import java.util.List;

public enum ItemRequestMapper {
    INSTANT;

    public ItemRequestDto toItemRequestDto(ItemRequest itemRequest){
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setId(itemRequest.getId());
        itemRequestDto.setDescription(itemRequest.getDescription());
        itemRequestDto.setRequestorId(itemRequest.getRequesterId());
        itemRequestDto.setCreated(itemRequest.getCreated());
        return itemRequestDto;
    }

    public ItemRequest toItemRequest (ItemRequestDto itemRequestDto){
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setId(itemRequestDto.getId());
        itemRequest.setDescription(itemRequestDto.getDescription());
        itemRequest.setRequesterId(itemRequestDto.getRequestorId());
        itemRequest.setCreated(itemRequestDto.getCreated());
        return itemRequest;
    }

    public ItemResponseForRequestDto toResponseWithItems(ItemRequest request, List<ItemDto> responses) {
        ItemResponseForRequestDto response = new ItemResponseForRequestDto();
        response.setId(request.getId());
        response.setRequestId(request.getId());
        response.setDescription(request.getDescription());
        response.setCreated(request.getCreated());
        response.setItems(responses);
        return response;
    }

    public ItemResponseForRequestDto toResponseWithoutItems(ItemRequest request) {
        ItemResponseForRequestDto response = new ItemResponseForRequestDto();
        response.setId(request.getId());
        response.setRequestId(request.getId());
        response.setDescription(request.getDescription());
        response.setCreated(request.getCreated());
        return response;
    }
}
