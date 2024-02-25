package ru.practicum.shareit.item.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.comment.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

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
