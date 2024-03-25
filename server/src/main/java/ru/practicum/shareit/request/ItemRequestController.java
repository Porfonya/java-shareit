package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.checker.Checker;

import java.util.List;


@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/requests")
public class ItemRequestController {
    private ItemRequestServiceImpl itemRequestService;
    private Checker checker;


    @PostMapping
    public ItemRequestDto createRequestItem(@RequestHeader("X-Sharer-User-Id") Long requesterId,
                                            @RequestBody ItemRequestDto itemRequestDto) {

        checker.checkerUser(requesterId);
        return itemRequestService.createRequest(requesterId, itemRequestDto);

    }

    @GetMapping()
    public List<ItemRequestDto> findAllItemRequest(@RequestHeader("X-Sharer-User-Id") Long requesterId) {
        log.info(" Получение списка своих запросов вместе с данными об ответах на них");
        checker.checkerUser(requesterId);
        return itemRequestService.getRequested(requesterId);
    }

    @GetMapping("/all")
    public List<ItemRequestDto> findAllItemRequest(@RequestHeader("X-Sharer-User-Id") Long requesterId,
                                                   @RequestParam(name = "from", defaultValue = "0") int from,
                                                   @RequestParam(name = "size", defaultValue = "20") int size) {
        log.info("Получение запроса на список вещей постранично.");
        checker.checkerUser(requesterId);
        return itemRequestService.listAllRequestsItemsWithRequesterId(requesterId, from, size);
    }

    @GetMapping("{requestId}")
    public ItemRequestDto getItemRequestById(@RequestHeader("X-Sharer-User-Id") Long requesterId,
                                             @PathVariable Long requestId) {
        checker.checkerUser(requesterId);
        ItemRequest itemRequest = checker.checkerItemRequest(requestId);
        log.info("Получение запроса на вещь с определённым ID.");
        return itemRequestService.getItemRequestById(requesterId, itemRequest);
    }

}
