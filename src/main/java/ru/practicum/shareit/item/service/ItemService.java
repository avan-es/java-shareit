package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
    public Item addItem(Item item, Long userId) {
        userValidation.isPresent(userId);
        itemValidation.itemValidation(item);
        item.setOwner(userId);
        return itemRepository.addItem(item);
    }

    public Item updateItem(ItemDto itemDto, Long itemId, Long userId) {
        userValidation.isPresent(userId);
        itemValidation.isPresent(itemId);
        itemDto.setId(itemId);
        itemDto.setOwner(userId);
        return itemRepository.updateItem(itemDto);
    }

    public Item getItem(Long itemId) {
        itemValidation.isPresent(itemId);
        return itemRepository.getItemById(itemId);
    }

    public List<Item> getUsersItems(Long userId) {
        userValidation.isPresent(userId);
        return itemRepository.getUsersItems(userId);

    }

    public List<Item> searchItem(String req) {
        return itemRepository.searchItem(req);
    }

    public List<Item> getAllItems() {
        return itemRepository.getItems();
    }
}
