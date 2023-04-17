package ru.practicum.shareit.item.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.item.comment.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemController.class)
class ItemControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ItemService itemService;

    @MockBean
    private UserService userService;

    Long itemId = 0L;

    Long userId = 0L;

    Item item1ByUser1 = new Item();

    ItemDto itemDto1ByUser1 = new ItemDto();

    UserDto userDto = new UserDto();

    CommentDto commentDto = new CommentDto();

    PageRequest pageRequest = PageRequest.of(0, 10);

    @BeforeEach
    void setUp() {
        item1ByUser1.setName("Item 1");
        item1ByUser1.setDescription("By user 1");
        item1ByUser1.setOwner(userId);
        item1ByUser1.setAvailable(true);
        item1ByUser1.setId(itemId);

        itemDto1ByUser1 = ItemMapper.INSTANT.toItemDto(item1ByUser1);
    }

    @Test
    void addItem() throws Exception {
        when(itemService.saveItem(item1ByUser1, userId)).thenReturn(itemDto1ByUser1);
        String result = mockMvc.perform(post("/items", item1ByUser1, userId)
                .header("X-Sharer-User-Id", userId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(item1ByUser1)))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(itemDto1ByUser1), result);
    }

    @Test
    void updateItem() throws Exception {
        when(itemService.updateItem(itemDto1ByUser1, itemId, userId)).thenReturn(itemDto1ByUser1);
        String result = mockMvc.perform(patch("/items/{itemId}", itemId)
                        .header("X-Sharer-User-Id", userId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(item1ByUser1)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(itemDto1ByUser1), result);
    }

    @Test
    void getItem() throws Exception {
        when(itemService.getItemById(itemId, userId)).thenReturn(itemDto1ByUser1);

        mockMvc.perform(get("/items/{itemId}", itemId)
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk());

        verify(itemService).getItemById(itemId, userId);
    }

    @Test
    void getUsersItems() throws Exception {
        List<ItemDto> itemDtoList = List.of();
        when(userService.getUserById(userId)).thenReturn(userDto);
        when(itemService.getUsersItems(userId, pageRequest)).thenReturn(itemDtoList);

        mockMvc.perform(get("/items", itemId)
                        .header("X-Sharer-User-Id", userId)
                        .param("size", "10")
                        .param("from", "0"))
                .andExpect(status().isOk());

        verify(itemService).getUsersItems(userId, pageRequest);
    }

    @Test
    void searchItem() throws Exception {
        List<ItemDto> itemDtoList = new ArrayList<>();
        when(itemService.searchItem("text", pageRequest)).thenReturn(itemDtoList);

        mockMvc.perform(get("/items/search")
                        .param("size", "10")
                        .param("from", "0")
                        .param("text", "text"))
                .andExpect(status().isOk());

        verify(itemService).searchItem("text", pageRequest);
    }

    @Test
    void searchItem_EmptyRequest() throws Exception {
        List<ItemDto> itemDtoList = new ArrayList<>();
        when(itemService.searchItem("", pageRequest)).thenReturn(itemDtoList);

        mockMvc.perform(get("/items/search")
                        .param("size", "10")
                        .param("from", "0")
                        .param("text", ""))
                .andExpect(status().isOk());

        verify(itemService, never()).searchItem("", pageRequest);
    }

    @Test
    void getAllItem() throws Exception {
        List<ItemDto> itemDtoList = new ArrayList<>();
        when(itemService.getAllItems()).thenReturn(itemDtoList);

        mockMvc.perform(get("/items/all"))
                .andExpect(status().isOk());

        verify(itemService).getAllItems();
    }

    @Test
    void deleteItem() throws Exception {
        mockMvc.perform(delete("/items/{itemId}", itemId)
                .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk());

        verify(itemService).deleteItem(itemId, userId);
    }

    @Test
    void addComment() throws Exception {
        commentDto.setText("Comment");
        when(itemService.saveComment(commentDto,itemId, userId)).thenReturn(commentDto);
        String result = mockMvc.perform(post("/items/{itemId}/comment", itemId, commentDto, userId)
                        .header("X-Sharer-User-Id", userId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(commentDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(itemService).saveComment(commentDto, itemId, userId);
        assertEquals(objectMapper.writeValueAsString(commentDto), result);
    }

    @Test
    void addComment_FAIL_emptyComment() throws Exception {
        commentDto.setText("");
        when(itemService.saveComment(commentDto,itemId, userId)).thenReturn(commentDto);
        mockMvc.perform(post("/items/{itemId}/comment", itemId, commentDto, userId)
                        .header("X-Sharer-User-Id", userId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(commentDto)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(itemService, never()).saveComment(commentDto, itemId, userId);
    }

}