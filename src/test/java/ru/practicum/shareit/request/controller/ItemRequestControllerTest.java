package ru.practicum.shareit.request.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemRequestController.class)
class ItemRequestControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ItemRequestService requestService;

    Long itemRequestId = 0L;

    Long userId = 0L;

    ItemRequest itemRequest = new ItemRequest();
    ItemRequestDto itemRequestDto = new ItemRequestDto();

    List<ItemRequestDto> itemRequestDtoList =  List.of();

    PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "created"));


    @Test
    void addRequest() throws  Exception {
        when(requestService.saveRequest(itemRequest, userId)).thenReturn(itemRequestDto);
        String result = mockMvc.perform(post("/requests", itemRequest, userId)
                        .header("X-Sharer-User-Id", userId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(itemRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(itemRequestDto), result);
    }

    @Test
    void getUserRequests() throws Exception {
        when(requestService.findRequestsByOwnerId(userId)).thenReturn(itemRequestDtoList);

        mockMvc.perform(get("/requests")
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk());

        verify(requestService).findRequestsByOwnerId(userId);
    }

    @Test
    void getAllRequests() throws Exception {
        when(requestService.findAll(userId, pageRequest)).thenReturn(itemRequestDtoList);

        mockMvc.perform(get("/requests/all")
                        .header("X-Sharer-User-Id", userId)
                        .param("size", "10")
                        .param("from", "0"))
                .andExpect(status().isOk());

        verify(requestService).findAll(userId, pageRequest);
    }

    @Test
    void getRequestById() throws Exception {
        when(requestService.getRequestById(userId, itemRequestId)).thenReturn(itemRequestDto);

        mockMvc.perform(get("/requests/{requestId}", itemRequestId, userId)
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk());

        verify(requestService).getRequestById(itemRequestId, userId);
    }
}