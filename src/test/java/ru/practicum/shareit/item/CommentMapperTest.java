package ru.practicum.shareit.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class CommentMapperTest {
    private Comment comment;
    private CommentDto commentDto;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    User user;
    @BeforeEach
    void setUp(){
         user = new User(1L, "mail@mail.ru", "name");
        comment = Comment.builder()
                .id(1L)
                .text("text")
                .authorId(user.getId())
                .itemId(2L)
                .created(LocalDateTime.now())
                .build();
        commentDto = CommentDto.builder()
                .id(1L)
                .text("text")
                .authorName(user.getName())
                .created(LocalDateTime.now())
                .build();
    }
        @Test
    void mapToCommentDto(){
            CommentDto result = CommentMapper.mapToCommentDto(comment, user);
            Assertions.assertThat(commentDto.getId()).isEqualTo(result.getId());
            Assertions.assertThat(commentDto.getText()).isEqualTo(result.getText());
            Assertions.assertThat(commentDto.getAuthorName()).isEqualTo(result.getAuthorName());
            Assertions.assertThat(commentDto.getCreated().format(dtf)).isEqualTo(result.getCreated().format(dtf));



        }
}