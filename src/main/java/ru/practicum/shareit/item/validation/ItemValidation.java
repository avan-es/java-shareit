package ru.practicum.shareit.item.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exeptions.ModelValidationException;
import ru.practicum.shareit.exeptions.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;

@Component("itemValidation")
public class ItemValidation {

    @Autowired
    @Qualifier("inMemoryItemRepository")
    private ItemRepository itemRepository;

    public void itemValidation (Item item) {
        if (item.getName().isBlank()) {
            throw new ModelValidationException("Имя не может быть пустым.");
        }
        if (item.getDescription() == null) {
            throw new ModelValidationException("Описание не может быть пустым.");
        }
        if (item.getAvailable() == null) {
            throw new ModelValidationException("Статус объекта должен быть 'Доступно' при создании.");
        }
    }

    public void isPresent (Long itemId) {
        if (!itemRepository.getItems().stream()
                .anyMatch(item -> item.getId().equals(itemId))) {
            throw new NotFoundException(String.format("Объект с ID %d не найден.", itemId));
        };
    }
}
