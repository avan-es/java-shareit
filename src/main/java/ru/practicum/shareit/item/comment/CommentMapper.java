package ru.practicum.shareit.item.comment;

public enum CommentMapper {
    INSTANT;

    public CommentDto toCommentDto(Comment comment, String authorName) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setText(commentDto.getText());
        commentDto.setAuthorName(authorName);
        commentDto.setCreated(comment.getCreated());
        return commentDto;
    }

    public Comment toComment(CommentDto commentDto, Long itemId, Long authorId) {
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setText(commentDto.getText());
        comment.setItemId(itemId);
        comment.setAuthorId(authorId);
        comment.setCreated(commentDto.getCreated());
        return comment;
    }
}
