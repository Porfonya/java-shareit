package ru.practicum.shareit.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRequestRepository requestRepository;

    private long ownerOneId;
    ItemRequest itemRequest;
    User ownerOne;

    @BeforeEach
    public void addItems() {
         ownerOne = User.builder()
                .name("name1")
                .email("mail1@mail.ru")
                .build();
        userRepository.save(ownerOne);

        ownerOneId = ownerOne.getId();

        itemRepository.save(Item.builder()
                .id(1L)
                .name("item")
                .description("desc")
                .available(true)
                .owner(ownerOne)
                .build());

        User ownerTwo = User.builder()
                .name("name2")
                .email("mail2@mail.ru")
                .build();
        userRepository.save(ownerTwo);

        itemRepository.save(Item.builder()
                .id(2L)
                .name("item2")
                .description("other")
                .available(true)
                .owner(ownerTwo)
                .itemRequest(itemRequest)
                .build());



    }



    @Test
    void findItemsByOwnerId() {
        List<Item> items = itemRepository.findItemsByOwnerIdOrderById(ownerOneId);

        assertEquals(items.size(), 1);
        assertEquals(items.get(0).getName(), "item");
    }

    @Test
    void findAllByItemRequestId() {
        itemRequest = ItemRequest.builder()
                .id(1L)
                .description("itemRequest")
                .created(LocalDateTime.now())
                .build();
        requestRepository.save(itemRequest);

        long requestId = itemRequest.getId();
        itemRepository.save(Item.builder()
                .id(3L)
                .name("item3")
                .description("itemRequest")
                .available(true)
                .owner(ownerOne)
                .itemRequest(itemRequest)
                .build());
        List<Item> items = itemRepository.findAllByItemRequestId(requestId);

        assertEquals(items.size(), 1);
        assertEquals(items.get(0).getItemRequest(), itemRequest);
    }
    @Test
    void search_whenDataIsNone_whenReturnEmptyList() {
        List<Item> items = itemRepository.search("none");

        assertEquals(items.size(), 0);
    }

    @Test
    void search_whenDataIsText_whenReturnTextList() {
        List<Item> items = itemRepository.search("desc");

        assertEquals(items.size(), 1);
    }
}