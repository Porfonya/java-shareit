package ru.practicum.shareit.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.BookingServiceImpl;
import ru.practicum.shareit.checker.Checker;
import ru.practicum.shareit.item.ItemController;
import ru.practicum.shareit.item.ItemServiceImpl;
import ru.practicum.shareit.user.UserServiceImpl;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ExtendWith(MockitoExtension.class)
class ItemRequestControllerTestIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    Checker checker;
    @MockBean
    private ItemRequestServiceImpl itemRequestService;
    @MockBean
    private BookingServiceImpl bookingService;
    @MockBean
    private ItemController itemController;
    @MockBean
    private ItemServiceImpl itemService;
    @MockBean
    private UserServiceImpl userService;

    @SneakyThrows
    @Test
    void createRequestItem() {

        ItemRequestDto itemRequestDto = new ItemRequestDto(1L, "desc", 1L,
                LocalDateTime.now(), null);
        Mockito.when(itemRequestService.createRequest(anyLong(), any(ItemRequestDto.class)))
                .thenReturn(itemRequestDto);
        String result = mockMvc.perform(post("/requests")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(itemRequestDto))
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertEquals(mapper.writeValueAsString(itemRequestDto), result);
    }

    @SneakyThrows
    @Test
    void findAllItemRequest() {
        Long requesterId = 1L;
        mockMvc.perform(get("/requests")
                        .contentType("application/json")
                        .header("X-Sharer-User-Id", requesterId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        verify(itemRequestService, times(1)).getRequested(requesterId);
    }

   /* @SneakyThrows
    @Test
    void testFindAllItemRequest_thenReturnItemRequest() {
        ItemRequestDto itemRequestDto = new ItemRequestDto(null, "desc", 1L,
                LocalDateTime.now(), null);
        itemRequestDto.setRequester(1L);
        Mockito.when(itemRequestService.createRequest(anyLong(), any(ItemRequestDto.class)))
                .thenReturn(itemRequestDto);
        mockMvc.perform(get("/requests")
                        .contentType("application/json")
                        .param("from", "1")
                        .param("size", "1")
                        .header("X-Sharer-User-Id", itemRequestDto.getRequester()))
                .andExpect(status().isOk());

        verify(itemRequestService, never()).listAllRequestsItemsWithRequesterId(
                itemRequestDto.getRequester(), anyInt(),anyInt() );
    }*/

    @SneakyThrows
    @Test
    void getItemRequestById() {
        Long requester = 1L;
        ItemRequest itemRequest = new ItemRequest(1L, "desc", requester,
                LocalDateTime.now());
        when(checker.checkerItemRequest(itemRequest.getId())).thenReturn(itemRequest);

        mockMvc.perform(get("/requests/{id}", itemRequest.getId())
                        .header("X-Sharer-User-Id", requester))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(itemRequestService, times(1)).getItemRequestById(requester, itemRequest);
    }
}