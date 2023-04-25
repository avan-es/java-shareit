package ru.practicum.shareit.item.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentGatewayDto {
    private Long id;
    private String text;
    private String authorName;
    private LocalDateTime created;
}
