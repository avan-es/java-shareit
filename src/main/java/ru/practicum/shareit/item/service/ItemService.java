package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.item.validation.ItemValidation;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.validation.UserValidation;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserValidation userValidation;
    private final ItemValidation itemValidation;
    public ItemDto addItem(Item item, Long userId) {
        userValidation.isPresent(userId);
        itemValidation.itemValidation(item);
        item.setOwner(userId);
        return itemRepository.addItem(item);
    }

    public ItemDto updateItem(ItemDto itemDto, Long itemId, Long userId) {
        userValidation.isPresent(userId);
        itemValidation.isPresent(itemId);
        itemDto.setId(itemId);
        return itemRepository.updateItem(itemDto, userId);
    }

    public ItemDto getItem(Long itemId) {
        itemValidation.isPresent(itemId);
        return ItemMapper.toItemDto(itemRepository.getItemById(itemId));
    }

    public List<ItemDto> getUsersItems(Long userId) {
        userValidation.isPresent(userId);
        return itemRepository.getUsersItems(userId);

    }

    public List<ItemDto> searchItem(String req) {
        return itemRepository.searchItem(req);
    }

    public List<ItemDto> getAllItems() {
        return itemRepository.getItems();
    }

    public void deleteItem(Long itemId) {
        itemValidation.isPresent(itemId);
        itemRepository.deleteItem(itemId);
    }
}
