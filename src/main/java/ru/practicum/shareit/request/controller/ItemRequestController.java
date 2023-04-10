package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.service.ItemRequestService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class ItemRequestController {

    private final ItemRequestService requestService;

    @PostMapping
    public ItemRequestDto addRequest(@RequestBody ItemRequest itemRequest,
                                     @RequestHeader(value = "X-Sharer-User-Id") Long userId) {
        return requestService.saveRequest(itemRequest, userId);
    }

    @GetMapping
    public List<ItemRequestDto> getResponse(@RequestHeader(value = "X-Sharer-User-Id") Long userId) {
        return requestService.findRequestsByOwnerId(userId);

    }

/*    @GetMapping
    public List<ItemResponseForRequestDto> getResponse(@RequestHeader(value = "X-Sharer-User-Id") Long userId) {
        return requestService.findRequestsByOwnerId(userId);

    }*/

    @GetMapping("/all")
    public List<ItemRequestDto> getResponse(@RequestHeader(value = "X-Sharer-User-Id") Long userId,
                                            @RequestParam(value = "from", defaultValue = "0") @Min(0) Integer page,
                                            @RequestParam(value = "size", defaultValue = "20") @Min(1) @Max(50) Integer size) {
        return requestService.findAll(userId, PageRequest.of(page, size));

    }

/*    @GetMapping("/all")
    public ItemResponseForRequestDto getResponse(@RequestHeader(value = "X-Sharer-User-Id") Long userId,
                                                 @RequestParam(value = "from", defaultValue = "0") Integer page,
                                                 @RequestParam(value = "size", defaultValue = "20") Integer size) {
        return requestService.findAll(userId, PageRequest.of(page, size));//, Sort.by(Sort.Direction.ASC, "created")));

    }*/


}
