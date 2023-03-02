package ru.practicum.shareit.item.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exeptions.ForbiddenException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component("inMemoryItemRepository")
@Slf4j
public class InMemoryItemRepository implements ItemRepository {

    private final List<Item> items = new ArrayList<>();
    private Long actualId = 0L;
    @Override
    public ItemDto addItem(Item item) {
        item.setId(getId());
        items.add(item);
        log.info(String.format("Объект с ID %s успешно создан.", item.getId()));
        return ItemMapper.toItemDto(item);
    }

    @Override
    public Item getItemById(Long itemId) {
        return items.stream()
                .filter(item -> item.getId().equals(itemId))
                .findAny()
                .orElse(null);
    }

    @Override
    public List<ItemDto> getItems() {
        return items.stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto updateItem(ItemDto itemDto, Long userId) {
        Item itemForUpdate = getItemById(itemDto.getId());
        isUserIsOwner(itemForUpdate, userId);
        if (itemDto.getName() != null && !itemDto.getName().isBlank()){
            itemForUpdate.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null && !itemDto.getDescription().isBlank()){
            itemForUpdate.setDescription(itemDto.getDescription());
        }
        if ((Optional.ofNullable(itemDto.getAvailable()).isPresent())){
            itemForUpdate.setAvailable(itemDto.getAvailable());
        }
        log.info(String.format("Объект с ID %s успешно обновлён.", itemForUpdate.getId()));
        return ItemMapper.toItemDto(itemForUpdate);
    }

    @Override
    public void deleteItem(Long itemId) {
        items.remove(getItemById(itemId));
        log.info(String.format("Объект с ID %s успешно удалён.", itemId));
    }

    @Override
    public List<ItemDto> getUsersItems(Long userId) {
        return items.stream()
                .filter(item -> item.getOwner().equals(userId))
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> searchItem(String req) {
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
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());

    }

    private Long getId() {
        return ++actualId;
    }

    private void isUserIsOwner (Item item, Long userId) {
        if(!item.getOwner().equals(userId)) {
            log.error(String.format("Ошибка обновления. Пользователь с ID %s не является владельцем объекта '%s'.",
                    userId, item.getName()));
            throw new ForbiddenException(String.format("Вы не являетесь владельцем объекта '%s'.", item.getName()));
        }
    }
}
