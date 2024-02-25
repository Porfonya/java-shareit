package ru.practicum.shareit.item.comment;

import lombok.*;

import java.time.LocalDateTime;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CommentDto {
    private Long id;
    private String text;
    private LocalDateTime created;
    private String authorName;

}
