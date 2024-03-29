package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.checker.Checker;
import ru.practicum.shareit.user.User;

import java.util.List;


@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private ItemServiceImpl itemService;
    private Checker checker;

    @PostMapping
    public ItemDto create(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestBody ItemDto itemDto) {

        return itemService.createItem(userId, itemDto);
    }

    @PatchMapping("/{id}")
    public ItemDto updateItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                              @PathVariable Long id,
                              @RequestBody ItemDto itemDto) {
        log.info("Выполнеине запроса PatchMapping");
        User user = checker.checkerUserWithReturn(userId);
        log.info("Проверили юзера");
        Item item = checker.checkerItem(id);
        return itemService.updateItem(user, item, itemDto);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemDto>> search(@RequestParam String text) {
        log.info("Выполнеине запроса Get запроса search ");
        return new ResponseEntity<>(itemService.search(text), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ItemDto getItemByUserId(@PathVariable Long id, @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Выполнеине запроса Get запроса getItemByUserIdWithBookings ");
        User user = checker.checkerUserWithReturn(userId);
        Item item = checker.checkerItem(id);
        return itemService.getItemByUserId(item, user);
    }

    @GetMapping
    public List<ItemDto> getAllItemsByUserIdBookings(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Выполнеине запроса Get запроса getAllItemsByUserIdWithBookings ");

        return itemService.getAllItemsByUserId(userId);
    }

    @PostMapping("{itemId}/comment")
    public CommentDto addCommentById(@PathVariable Long itemId, @RequestHeader("X-Sharer-User-Id") Long userId,
                                     @RequestBody CommentDto comment) {
        User user = checker.checkerUserWithReturn(userId);
        Item item = checker.checkerItem(itemId);
        return itemService.createComment(item, user, comment);
    }


}
