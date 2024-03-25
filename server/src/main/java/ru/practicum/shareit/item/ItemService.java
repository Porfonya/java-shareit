package ru.practicum.shareit.item;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.user.User;

import java.util.List;

@Service
@Transactional(readOnly = true)
public interface ItemService {
    @Transactional
    ItemDto createItem(Long userId, ItemDto itemDto);

    @Transactional
    ItemDto updateItem(User user, Item item, ItemDto itemDto);

    @Transactional
    List<ItemDto> search(String text);

    @Transactional(readOnly = true)
    ItemDto getItemByUserId(Item item, User user);

    @Transactional(readOnly = true)
    List<ItemDto> getAllItemsByUserId(Long userId);

    @Transactional
    CommentDto createComment(Item item, User user, CommentDto commentDto);
}
