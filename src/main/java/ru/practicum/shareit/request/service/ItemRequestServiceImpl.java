package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.ItemRequestMapper;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.request.validation.ItemRequestValidation;
import ru.practicum.shareit.user.validation.UserValidation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemRequestServiceImpl implements ItemRequestService {

    private final ItemRequestRepository itemRequestRepository;
    private final UserValidation userValidation;
    private final ItemRequestValidation itemRequestValidation;

    private final ItemRepository itemRepository;

    @Override
    public ItemRequestDto saveRequest(ItemRequest itemRequest, Long userId) {
        userValidation.isPresent(userId);
        itemRequestValidation.requestValidation(itemRequest);
        itemRequest.setRequesterId(userId);
        itemRequest.setCreated(LocalDateTime.now());
        return ItemRequestMapper.INSTANT.toItemRequestDto(
                itemRequestRepository.save(itemRequest));
    }

    @Override
    public List<ItemRequestDto> findRequestsByOwnerId(Long ownerId) {
        userValidation.isPresent(ownerId);
        List<ItemRequestDto> requests = itemRequestRepository.findAllByRequesterIdOrderByCreatedDesc(ownerId)
                .stream()
                .map(ItemRequestMapper.INSTANT::toItemRequestDto)
                .collect(Collectors.toList());
        for (ItemRequestDto request: requests) {
                request.setItems(itemRepository.findAllByRequestId(request.getId()));
        }
        return requests;
    }
    @Override
    public List<ItemRequestDto> findAll(Long ownerId, Pageable pageable) {

        userValidation.isPresent(ownerId);
        List<ItemRequestDto> result = itemRequestRepository.findAllByRequesterIdNot(ownerId, pageable)
                .stream()
                .map(ItemRequestMapper.INSTANT::toItemRequestDto)
                .collect(Collectors.toList());
        for (ItemRequestDto request: result) {
            request.setItems(itemRepository.findAllByRequestId(request.getId()));
        }
        return result;
    }

    @Override
    public ItemRequestDto getRequestById(Long userId, Long requestId) {
        userValidation.isPresent(userId);
        ItemRequestDto result = ItemRequestMapper.INSTANT.toItemRequestDto(itemRequestValidation.isPresent(requestId));
        result.setItems(itemRepository.findAllByRequestId(result.getId()));
        return result;
    }
}
