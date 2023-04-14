package ru.practicum.shareit.request.service;

import net.bytebuddy.pool.TypePool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import ru.practicum.shareit.exeptions.ModelValidationException;
import ru.practicum.shareit.exeptions.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.ItemRequestMapper;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.request.validation.ItemRequestValidation;
import ru.practicum.shareit.user.validation.UserValidation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemRequestServiceImplTest {

    @InjectMocks
    private ItemRequestServiceImpl itemRequestService;

    @Mock
    private ItemRequestRepository itemRequestRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserValidation userValidation;

    @Mock
    private ItemRequestValidation itemRequestValidation;

    private ItemRequest itemRequest = new ItemRequest();

    private ItemRequestDto itemRequestDto = new ItemRequestDto();

    private Long userId = 0L;

    private Long iteRequestId = 0L;


    private PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "created"));

    private List<ItemRequestDto> itemRequestDtoList = new ArrayList<>();


    private Slice<ItemRequestDto> itemRequestDtoSlice;


    @BeforeEach
    void setUp () {
        itemRequest.setRequesterId(userId);
        itemRequest.setCreated(LocalDateTime.now());
        itemRequest.setDescription("Test item");
        itemRequest.setId((Long) 0L);

        itemRequestDto = ItemRequestMapper.INSTANT.toItemRequestDto(itemRequest);
        itemRequestDto.setItems(new ArrayList<Item>());

        itemRequestDtoList.add(itemRequestDto);
    }

    @Test
    void saveRequest_whenUserAndItemRequestIsCorrect_thenSave() {
        when(itemRequestRepository.save(itemRequest))
                .thenReturn(itemRequest);

        ItemRequestDto actualItemRequestDto = itemRequestService.saveRequest(itemRequest, userId);

        verify(itemRequestRepository).save(itemRequest);
        assertAll(
                () -> assertEquals(itemRequest.getId(), actualItemRequestDto.getId()),
                () -> assertEquals(itemRequest.getRequesterId(), actualItemRequestDto.getRequestorId()),
                () -> assertEquals(itemRequest.getDescription(), actualItemRequestDto.getDescription()),
                () -> assertEquals(itemRequest.getCreated(), actualItemRequestDto.getCreated())
        );
    }

    @Test
    void saveRequest_whenUserNotPresent_thenNotFoundException() {
        when(userValidation.isPresent(userId))
                .thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class,
                () -> itemRequestService.saveRequest(itemRequest, userId));

        verify(itemRequestRepository, never()).save(itemRequest);
    }

    @Test
    void saveRequest_whenItemRequestNotValid_thenModelValidationException() {

        doThrow(ModelValidationException.class)
                .when(itemRequestValidation).requestValidation(itemRequest);

        assertThrows(ModelValidationException.class,
                () -> itemRequestService.saveRequest(itemRequest, userId));

        verify(itemRequestRepository, never()).save(itemRequest);
    }

    @Test
    void findRequestsByOwnerId() {
        when(itemRequestRepository.findAllByRequesterIdOrderByCreatedDesc(userId)
                .stream()
                .map(ItemRequestMapper.INSTANT::toItemRequestDto)
                .collect(Collectors.toList()))
                .thenReturn(itemRequestDtoList);

        List<ItemRequest> itemRequests = itemRequestRepository.findAllByRequesterIdOrderByCreatedDesc(userId);

        verify(itemRequestRepository).findAllByRequesterIdOrderByCreatedDesc(userId);

    }

    @Test
    void findAll() throws Exception{

        when(itemRequestRepository.findAllByRequesterIdNot(userId, pageRequest))
                .thenReturn(null);

        Slice<ItemRequest> itemRequests = itemRequestRepository.findAllByRequesterIdNot(userId, pageRequest);

        verify(itemRequestRepository).findAllByRequesterIdNot(userId, pageRequest);
    }

    @Test
    void getRequestById() {
        when(itemRequestRepository.getById(iteRequestId)).thenReturn(itemRequest);

        ItemRequestDto actualItemRequest = ItemRequestMapper.INSTANT.toItemRequestDto(itemRequestRepository.getById(iteRequestId));

        verify(itemRequestRepository).getById(iteRequestId);
    }
}