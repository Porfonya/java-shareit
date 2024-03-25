package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/items")
@Valid
public class ItemController {
    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> create(@RequestHeader("X-Sharer-User-Id") Long userId,
                                         @Valid @RequestBody ItemDto itemDto) {
        log.info("Выполнеине запроса PostMapping, создание ItemDto");
        return itemClient.createItem(userId, itemDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                                             @PathVariable Long id,
                                             @RequestBody ItemDto itemDto) {
        log.info("Выполнеине запроса PatchMapping");

        return itemClient.updateItem(userId, id, itemDto);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(@RequestHeader("X-Sharer-User-Id") Long userId,
                                         @RequestParam(value = "text", required = false)
                                         @Valid String text) {
        log.info("Выполнеине запроса Get запроса search ");
        return itemClient.search(userId, text);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getItemByUserId(@PathVariable Long id,
                                                  @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Выполнеине запроса Get запроса getItemByUserIdWithBookings ");

        return itemClient.getItemByUserId(id, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getAllItemsByUserIdBookings(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Выполнеине запроса Get запроса getAllItemsByUserIdWithBookings ");

        return itemClient.getAllItemsByUserId(userId);
    }

    @PostMapping("{itemId}/comment")
    public ResponseEntity<Object> addCommentById(@PathVariable Long itemId,
                                                 @RequestHeader("X-Sharer-User-Id") Long userId,
                                                 @RequestBody CommentDto comment) {
        log.info("Выполнеине запроса PostMapping создание комментария");
        return itemClient.createComment(itemId, userId, comment);
    }


}
