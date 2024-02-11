package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;


    @Override
    public ItemDto createItem(Long userId, ItemDto itemDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        Item thisItem = itemRepository.save(ItemMapper.mapToItem(itemDto));
        thisItem.setOwner(user);
        return ItemMapper.mapToItemDto(thisItem);
    }

    @Override
    public ItemDto updateItem(Long userId, Long id, ItemDto itemDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        Item existingItem = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item not found"));
        existingItem.setItemId(id);
        existingItem.setName(itemDto.getName() == null ? existingItem.getName() : itemDto.getName());
        existingItem.setDescription(itemDto.getDescription() == null ? existingItem.getDescription() : itemDto.getDescription());
        existingItem.setAvailable(itemDto.getAvailable() == null ? existingItem.getAvailable() : itemDto.getAvailable());
        existingItem.setOwner(itemDto.getOwner() == null ? user : itemDto.getOwner());
        return ItemMapper.mapToItemDto(itemRepository.save(existingItem));
    }

    @Override
    public ItemDto getItemById(Long id) {
        Item item = itemRepository.getReferenceById(id);
        return ItemMapper.mapToItemDto(item);

    }

    @Override
    public List<ItemDto> getUserItems(Long userId) {

   /*     List<Item> items = itemRepository.findByUserId(userId);
        return ItemMapper.mapToItemDto(items);*/
        return null;
    }

    @Override
    public List<ItemDto> search(String text) {
       /* List<Item> item = itemRepository.findAll();
        List<ItemDto> itemDtos = new ArrayList<>();
        for (Item value : item) {
            itemDtos.add(ItemMapper.mapToItemDto(value));
        }*/
//        return itemDtos;
        return null;

    }
}
