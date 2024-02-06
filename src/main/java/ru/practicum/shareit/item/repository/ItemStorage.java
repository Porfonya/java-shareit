package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemStorage {

    Item addItem(Item item);

    void savingTheUserItems(Long userId, Item item);

    List<Item> getFindAllItemByUser(Long userId);

    Item getItemById(Long id);

    List<Item> search(String text);
}
