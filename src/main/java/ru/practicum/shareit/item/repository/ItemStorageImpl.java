package ru.practicum.shareit.item.repository;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Data
@Slf4j
public class ItemStorageImpl implements ItemStorage {

    private final Map<Long, Item> items = new HashMap<>();
    private final Map<Long, Item> usersItems = new HashMap<>();
    private Long id = 0L;

    @Override
    public Item addItem(Item item) {

        item.setId(++id);
        items.put(id, item);
        log.info("Создан инструмент с id = {} ", item.getId());
        return items.get(id);
    }

    @Override
    public void savingTheUserItems(Long userId, Item item) {

        usersItems.put(userId, item);
        log.info("Item c id {} добавлен,  user = {} ", item.getId(), userId);

    }

    @Override
    public List<Item> getFindAllItemByUser(Long userId) {
        List<Item> result = new ArrayList<>();
        for (Map.Entry<Long, Item> entry : usersItems.entrySet()) {
            Long id = entry.getKey();
            Item item = entry.getValue();
            if (userId.equals(id)) {
                result.add(item);
            }
        }
        log.info("У пользователя {}, инструменты {}", userId, result);
        return result;
    }

    @Override
    public Item getItemById(Long id) {
        if (!items.containsKey(id)) {
            throw new NotFoundException("инструмент не найден");
        }
        return items.get(id);
    }

    @Override
    public List<Item> search(String text) {
        List<Item> searchedItems = new ArrayList<>();
        if (!text.isBlank()) {
            for (Map.Entry<Long, Item> entry : items.entrySet()) {
                Item item = entry.getValue();
                if (item.getName().toLowerCase().contains(text.toLowerCase()) ||
                        item.getDescription().toLowerCase().contains(text.toLowerCase())) {
                    if (item.getAvailable().equals(true)) {
                        searchedItems.add(item);
                    }
                }
            }
        }
        return searchedItems;
    }
}
