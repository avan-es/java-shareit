package ru.practicum.shareit.item.repository;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exeptions.ForbiddenException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component("inMemoryItemRepository")
public class InMemoryItemRepository implements ItemRepository {

    private final List<Item> items = new ArrayList<>();
    private Long actualId = 0L;
    @Override
    public Item addItem(Item item) {
        item.setId(getId());
        items.add(item);
        return item;
    }

    @Override
    public Item getItemById(Long itemId) {
        return items.stream()
                .filter(item -> item.getId().equals(itemId))
                .findAny()
                .orElse(null);
    }

    @Override
    public List<Item> getItems() {
        return items;
    }

    @Override
    public Item updateItem(ItemDto itemDto) {
        Item itemForUpdate = getItemById(itemDto.getId());
        isUserIsOwner(itemForUpdate, itemDto);
        if (itemDto.getName() != null && !itemDto.getName().isBlank()){
            itemForUpdate.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null && !itemDto.getDescription().isBlank()){
            itemForUpdate.setDescription(itemDto.getDescription());
        }
        if ((Optional.ofNullable(itemDto.getAvailable()).isPresent())){
            itemForUpdate.setAvailable(itemDto.getAvailable());
        }
        return itemForUpdate;
    }

    @Override
    public void deleteItem(Long itemId) {

    }

    @Override
    public List<Item> getUsersItems(Long userId) {
        return items.stream()
                .filter(item -> item.getOwner().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> searchItem(String req) {
        List<Item> searchByName = items.stream()
                .filter(item -> item.getName().toLowerCase().contains(req.toLowerCase()))
                .collect(Collectors.toList())
                .stream().filter(item -> item.getAvailable())
                .collect(Collectors.toList());
        List<Item> searchByDescription = items.stream()
                .filter(item -> item.getDescription().toLowerCase().contains(req.toLowerCase()))
                .collect(Collectors.toList())
                .stream().filter(item -> item.getAvailable())
                .collect(Collectors.toList());
        List<Item> result = new ArrayList<>();
        result.addAll(searchByName);
        result.addAll(searchByDescription);
        return result.stream()
                .distinct()
                .collect(Collectors.toList());

    }

    private Long getId() {
        return ++actualId;
    }

    private void isUserIsOwner (Item item, ItemDto itemDto) {
        if(!item.getOwner().equals(itemDto.getOwner())) {
            throw new ForbiddenException(String.format("Вы не являетесь владельцем объекта '%s'.", itemDto.getName()));
        }
    }
}
