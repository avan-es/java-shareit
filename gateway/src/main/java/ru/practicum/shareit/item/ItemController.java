package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.BadRequest;
import ru.practicum.shareit.item.comment.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoFroRequest;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

//@Controller
@RestController

@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {

    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> addItem(@RequestBody ItemDto item,
                                          @RequestHeader(value = "X-Sharer-User-Id") Long userId) {
        return itemClient.addItem(item, userId);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> updateItem(@RequestBody ItemDto itemDto,
                              @RequestHeader (value = "X-Sharer-User-Id") Long userId,
                              @PathVariable Long itemId) {
        return itemClient.updateItem(userId, itemDto, itemId);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItem(@PathVariable Long itemId,
                                          @RequestHeader (value = "X-Sharer-User-Id") Long userId) {
        ResponseEntity<Object> response = itemClient.getItem(itemId, userId);
        System.out.println(response.getBody());
        return itemClient.getItem(itemId, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getUsersItems(@RequestHeader (value = "X-Sharer-User-Id") Long userId,
                                       @RequestParam(value = "from", defaultValue = "0") @Min(0) Integer from,
                                       @RequestParam(value = "size", defaultValue = "20") @Min(1) @Max(50) Integer size) {
        return itemClient.getUsersItems(from, size, userId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchItem(@RequestParam String text,
                                    @RequestParam(value = "from", defaultValue = "0") @Min(0) Integer from,
                                    @RequestParam(value = "size", defaultValue = "20") @Min(1) @Max(50) Integer size,
                                    @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemClient.searchItem(from, size, userId, text);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllItems() {
        return itemClient.getAllItems();
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Object> deleteItem(@PathVariable Long itemId,
                           @RequestHeader (value = "X-Sharer-User-Id") Long userId) {
        return itemClient.deleteItem(itemId, userId);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(@RequestBody CommentDto commentDto,
                                 @PathVariable Long itemId,
                                 @RequestHeader (value = "X-Sharer-User-Id") Long userId) {
        if (commentDto.getText().isBlank()) {
            throw new BadRequest("Текст отзыва не может быть пустым.");
        }
        return itemClient.addComment(userId, itemId, commentDto);
    }
}
