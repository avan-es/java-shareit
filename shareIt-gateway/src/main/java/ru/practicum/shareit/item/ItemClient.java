package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.item.comment.CommentGatewayDto;
import ru.practicum.shareit.item.dto.ItemGatewayDto;

import java.util.Map;

@Service
public class ItemClient extends BaseClient {

    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> addItem(ItemGatewayDto item, Long userId) {
        return post("", userId, item);
    }

    public ResponseEntity<Object> updateItem(Long itemId, ItemGatewayDto itemDto, Long userId) {
        return patch("/" + itemId, userId, itemDto);
    }

    public ResponseEntity<Object> getItem(Long itemId, Long userId) {
        return get("/" + itemId, userId);
    }

    public ResponseEntity<Object> getUsersItems(Long userId, Integer from, Integer size) {
        if (from == null) {
            return get("", userId);
        } else {
            Map<String, Object> parameters = Map.of(
                    "from", from,
                    "size", size);
            return get("?from={from}&size={size}", userId, parameters);
        }
    }

    public ResponseEntity<Object> searchItem(String text, Integer from, Integer size, Long userId) {
        Map<String, Object> parameters;
        if (from == null) {
            parameters = Map.of("text", text);
            return get("/search?text={text}", userId, parameters);
        } else {
            parameters = Map.of(
                    "from", from,
                    "size", size,
                    "text", text);
        }
        return get("/search?text={text}&from={from}&size{size}", userId, parameters);
    }

    public ResponseEntity<Object> getAllItems() {
        return get("/all");
    }

    public ResponseEntity<Object> deleteItem(Long itemId, Long userId) {
        return delete("/" + itemId, userId);
    }

    public ResponseEntity<Object> addComment(CommentGatewayDto commentDto, Long itemId, Long userId) {
        return post("/"+ itemId + "/comment",  userId, commentDto);
    }

}