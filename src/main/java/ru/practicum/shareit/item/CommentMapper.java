package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.item.Comment;
import ru.practicum.shareit.item.CommentDto;
import ru.practicum.shareit.user.model.User;

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
