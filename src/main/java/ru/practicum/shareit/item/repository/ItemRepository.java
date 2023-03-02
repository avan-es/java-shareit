package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {

    Item addItem (Item item);
    Item getItemById (Long itemId);
    List<Item> getItems ();
    Item updateItem (ItemDto itemDto);
    void deleteItem (Long itemId);

    List<Item> getUsersItems(Long userId);

    List<Item> searchItem(String req);
}
