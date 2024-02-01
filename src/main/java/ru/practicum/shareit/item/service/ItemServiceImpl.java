package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemStorageImpl;
import ru.practicum.shareit.user.repository.UserStorageImpl;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final ItemStorageImpl itemStorage;
    private final UserStorageImpl userStorage;


    @Override
    public ItemDto createItem(Long userId, ItemDto itemDto) {
        Item thisItem = new Item();
        if (userStorage.getById(userId) != null) {
            thisItem = itemStorage.addItem(ItemMapper.mapToItem(itemDto));
            itemStorage.savingTheUserItems(userId, thisItem);
        }
        return ItemMapper.mapToItemDto(thisItem);
    }

    @Override
    public ItemDto updateItem(Long userId, Long id, ItemDto itemDto) {
        if (itemStorage.getUsersItems().containsKey(userId)) {
            Item existingItem = itemStorage.getItemById(id);
            if (itemStorage.getUsersItems().containsKey(userId)) {
                existingItem.setId(id);
                existingItem.setName(itemDto.getName() == null ? existingItem.getName() : itemDto.getName());
                existingItem.setDescription(itemDto.getDescription() == null ? existingItem.getDescription() : itemDto.getDescription());
                existingItem.setAvailable(itemDto.getAvailable() == null ? existingItem.getAvailable() : itemDto.getAvailable());
                existingItem.setOwner(itemDto.getOwner() == null ? existingItem.getOwner() : itemDto.getOwner());
                existingItem.setIsRequested(itemDto.getIsRequested() == null ? existingItem.getIsRequested() : itemDto.getIsRequested());
                itemStorage.savingTheUserItems(userId, existingItem);
                log.info("Items обновлен");
                return ItemMapper.mapToItemDto(existingItem);
            } else {
                throw new NotFoundException("Инструмент для обновления не найден");
            }
        } else {
            throw new NotFoundException("Владелец инструмента отсутсвует");
        }
    }

    @Override
    public ItemDto getItemById(Long id) {
        Item item = itemStorage.getItemById(id);
        return ItemMapper.mapToItemDto(item);
    }

    @Override
    public List<Item> getUserItems(Long userId) {
        return itemStorage.getFindAllItemByUser(userId);
    }

    @Override
    public List<ItemDto> search(String text) {
        List<Item> item = itemStorage.search(text);
        List<ItemDto> itemDtos = new ArrayList<>();
        for (Item value : item) {
            itemDtos.add(ItemMapper.mapToItemDto(value));
        }
        return itemDtos;


    }
}
