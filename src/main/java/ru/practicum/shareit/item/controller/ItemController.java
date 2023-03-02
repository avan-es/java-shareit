package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exeptions.ModelValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public Item addItem (@RequestBody Item item,
                         @RequestHeader (value = "X-Sharer-User-Id", required = false) Long userId) {
        if (userId == null) {
            throw new ModelValidationException(String.format("Не указан владелиц для '%s'.", item.getName()));
        }
        return itemService.addItem(item, userId);
    }

    @PatchMapping("/{itemId}")
    public Item updateItem (@RequestBody ItemDto itemDto,
                            @RequestHeader (value = "X-Sharer-User-Id", required = false) Long userId,
                            @PathVariable Long itemId) {
        if (userId == null) {
            throw new ModelValidationException(String.format("Не указан владелиц для '%s'.", itemDto.getName()));
        }
        return itemService.updateItem(itemDto, itemId, userId);
    }

    @GetMapping("/{itemId}")
    public Item getItem(@PathVariable Long itemId) {
        return itemService.getItem(itemId);
    }

    @GetMapping
    public List<Item> getUsersItems(@RequestHeader (value = "X-Sharer-User-Id") Long userId) {
        return itemService.getUsersItems(userId);
    }

    @GetMapping("/search")
    public List<Item> searchItem (@RequestParam (value = "text", required = true) @NotBlank String text) {
        if (!text.isBlank()) {
            return itemService.searchItem(text);
        }
        return new ArrayList<>();
    }

    @GetMapping("/all")
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }
}
