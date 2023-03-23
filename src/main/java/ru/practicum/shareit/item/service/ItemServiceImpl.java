package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exeptions.ForbiddenException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.validation.ItemValidation;
import ru.practicum.shareit.user.validation.UserValidation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService{

    private final ItemRepository itemRepository;
    private final UserValidation userValidation;
    private final ItemValidation itemValidation;

    @Override
    public ItemDto saveItem(Item item, Long userId) {
        itemValidation.itemValidation(item);
        userValidation.isPresent(userId);
        item.setOwner(userId);
        return ItemMapper.INSTANT.toItemDto(itemRepository.save(item));
    }

    @Override
    public ItemDto updateItem(ItemDto itemDto, Long itemId, Long userId) {
        itemValidation.isPresent(itemId);
        userValidation.isPresent(userId);
        itemDto.setId(itemId);
        Item itemForUpdate = itemRepository.getById(itemId);
        isUserIsOwner(itemForUpdate, userId);
        if (itemDto.getName() != null && !itemDto.getName().isBlank()) {
            itemForUpdate.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null && !itemDto.getDescription().isBlank()) {
            itemForUpdate.setDescription(itemDto.getDescription());
        }
        if ((Optional.ofNullable(itemDto.getAvailable()).isPresent())) {
            itemForUpdate.setAvailable(itemDto.getAvailable());
        }
        log.info(String.format("Объект с ID %s успешно обновлён.", itemForUpdate.getId()));
        return ItemMapper.INSTANT.toItemDto(itemRepository.save(itemForUpdate));
    }

    @Override
    public ItemDto getItemById(Long itemId) {
        itemValidation.isPresent(itemId);
        return ItemMapper.INSTANT.toItemDto(itemRepository.getById(itemId));
    }

    @Override
    public List<ItemDto> getUsersItems(Long userId) {
        return itemRepository.getAllByOwner(userId).stream()
                .map(ItemMapper.INSTANT::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> getAllItems() {
        return itemRepository.findAll().stream()
                .map(ItemMapper.INSTANT::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> searchItem(String text) {
        List<Item> searchByName = itemRepository.searchByNameContainingIgnoreCase(text).stream()
                .filter(Item::getAvailable)
                .collect(Collectors.toList());
        List<Item> searchByDescription = itemRepository.searchByDescriptionContainingIgnoreCase(text).stream()
                .filter(Item::getAvailable)
                .collect(Collectors.toList());
        List<Item> result = new ArrayList<>();
        result.addAll(searchByName);
        result.addAll(searchByDescription);
        return result.stream()
                .distinct()
                .collect(Collectors.toList())
                .stream()
                .map(ItemMapper.INSTANT::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteItem(Long itemId, Long userId) {
        itemValidation.isPresent(itemId);
        isUserIsOwner(itemRepository.getById(itemId), userId);
        itemRepository.deleteById(itemId);
        log.info(String.format("Объект с ID %s успешно удалён.", itemId));
    }

    private void isUserIsOwner(Item item, Long userId) {
        if (!item.getOwner().equals(userId)) {
            log.error(String.format("Ошибка обновления. Пользователь с ID %s не является владельцем объекта '%s'.",
                    userId, item.getName()));
            throw new ForbiddenException(String.format("Вы не являетесь владельцем объекта '%s'.", item.getName()));
        }
    }
}
