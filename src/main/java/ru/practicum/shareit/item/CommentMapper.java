package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.user.User;

@RequiredArgsConstructor
public class CommentMapper {
    public static CommentDto mapToCommentDto(Comment comment, User user) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .created(comment.getCreated())
                .authorName(user.getName())
                .build();

    }

}
