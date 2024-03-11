package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@DataJpaTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class CommentRepositoryTest {
    private final CommentRepository commentRepository;
    private Comment comment;

    @BeforeEach
    void setUp() {
        comment = Comment.builder()
                .id(any())
                .text("text")
                .itemId(any())
                .authorId(any())
                .created(LocalDateTime.now())
                .build();

    }

    @Test
    void save_whenAuthorAndTextAndItemExists_thenCommentSaved() {
        assertDoesNotThrow(() -> commentRepository.save(comment));
    }

    @Test
    void findAllByItemId_whenComments_thenReturnListOfNotNull() {
        comment.setItemId(any());
        List<Comment> commentList = commentRepository.findAllByItemIdOrderByCreatedDesc(any());
        assertNotNull(commentList);
    }
}



