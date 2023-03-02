package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {

    ItemDto addItem (Item item);
    Item getItemById (Long itemId);
    List<ItemDto> getItems ();
    ItemDto updateItem (ItemDto itemDto, Long userId);
    void deleteItem (Long itemId);

    List<ItemDto> getUsersItems(Long userId);

    List<ItemDto> searchItem(String req);
}
