package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.ItemRequestMapper;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.request.validation.RequestValidation;
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
    private final RequestValidation requestValidation;

    private final ItemRepository itemRepository;

    @Override
    public ItemRequestDto saveRequest(ItemRequest itemRequest, Long userId) {
        userValidation.isPresent(userId);
        requestValidation.requestValidation(itemRequest);
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
           /* List<Item> items = itemRepository.findAllByRequestId(request.getId());
            if (!items.isEmpty()) {*/
                request.setItems(itemRepository.findAllByRequestId(request.getId()));
           // }
        }
        return requests;
    }
    @Override
    public List<ItemRequestDto> findAll(Long ownerId, Pageable pageable) {
        userValidation.isPresent(ownerId);
        List<ItemRequestDto> requests = itemRequestRepository.findAllByRequesterIdOrderByCreatedDesc(ownerId)
                .stream()
                .map(ItemRequestMapper.INSTANT::toItemRequestDto)
                .collect(Collectors.toList());
        for (ItemRequestDto request: requests) {
            request.setItems(itemRepository.findAllByRequestId(request.getId(), pageable));
        }
        //List<ItemDto> responses = itemRepository.findAllByRequestId(ownerId,pageable);
  //      ItemResponseForRequestDto response = ItemRequestMapper.INSTANT.toResponseWithItems(request, responses);
/*        Page<Request> responses = requestRepository.findAll(pageable);
        do {
            responses.getContent().forEach(RequestMapper.INSTANT::toItemRequestDto);
            if (responses.hasNext()){
                pageable = PageRequest.of(pageable.getPageNumber() + 1, pageable.getPageSize(), pageable.getSort());
            } else {
                pageable = null;
            }
        } while (pageable != null);*/
        //ItemResponseForRequestDto response = RequestMapper.INSTANT.toResponse(request, responses);


        //return List.of(responses);

        return requests;
    }
}
