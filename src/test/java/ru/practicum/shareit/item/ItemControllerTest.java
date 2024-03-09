package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import ru.practicum.shareit.checker.Checker;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class ItemControllerTest {
    @Mock
    private ItemServiceImpl itemService;
    @Mock
    Checker checker;
    @InjectMocks
    private ItemController itemController;
    Long userId;
    Long itemId;
    User newUser;


    private Item expected;

    private Item newItem;

    private ItemDto expectedDto;

    @BeforeEach
    void setUp() {
        expected = new Item(null,
                "newItem",
                "newItemDescription",
                true,
                null,
                new ItemRequest(),
                null);

        expectedDto = new ItemDto(itemId,
                "newItem",
                "newItemDescription",
                true,
                1L,
                null,
                null,
                new ArrayList<>());
        newItem = new Item(1L,
                "newItemTwo",
                "newItemDescriptionTwo",
                true,
                null,
                null,
                null);
        newUser = new User(1L,
                "newUser@mail.ru",
                "NewUser");
    }

    @Test
    void create() {
        when(itemService.createItem(userId, expectedDto)).thenReturn(expectedDto);

        ItemDto actual = itemController.create(userId, expectedDto);

        assertEquals(expectedDto, actual);


    }

    @Test
    void updateItem_whenUserAndItemExistAndAllDataIsCorrect_thenReturnUpdateItem() {

  /*      when(checker.checkerUserWithReturn(anyLong())).thenReturn(newUser);
        when(checker.checkerItem(anyLong())).thenReturn(newItem);


        when(itemService.updateItem(newUser, newItem, expectedDto)).thenReturn(expectedDto);

        ItemDto actual = itemController.updateItem(userId, itemId, expectedDto);

        assertEquals(actual, expectedDto);*/

    }

    @Test
    void search() {
    }

    @Test
    void getItemByUserId() {
    }

    @Test
    void getAllItemsByUserIdBookings() {


    }

    @Test
    void addCommentById() {
    }
}