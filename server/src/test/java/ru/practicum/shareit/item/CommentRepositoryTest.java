package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.booking.BookingController;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;

@DataJpaTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class CommentRepositoryTest {
    private final CommentRepository commentRepository;
    private Comment comment;
    @Mock
    ItemController itemController;
    @Mock
    BookingController bookingController;

    @Test
    void save_whenAuthorAndTextAndItemExists_thenCommentSaved() {
        comment = Comment.builder()
                .id(any())
                .text("text")
                .itemId(any())
                .authorId(any())
                .created(LocalDateTime.now())
                .build();
        assertDoesNotThrow(() -> commentRepository.save(comment));
    }


}



