package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;


@RestController
@Slf4j
@Validated
@AllArgsConstructor
@RequestMapping("/requests")
public class ItemRequestController {

    private final ItemRequestClient requestClient;


    @PostMapping
    public ResponseEntity<Object> createRequestItem(@RequestHeader("X-Sharer-User-Id") Long requesterId,
                                                    @Valid @RequestBody ItemRequestDto itemRequestDto) {

        return requestClient.createRequest(requesterId, itemRequestDto);

    }

    @GetMapping()
    public ResponseEntity<Object> findAllItemRequest(@RequestHeader("X-Sharer-User-Id") Long requesterId) {
        log.info(" Получение списка своих запросов вместе с данными об ответах на них");
        return requestClient.getRequested(requesterId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> findAllItemRequest(@RequestHeader("X-Sharer-User-Id") Long requesterId,
                                                     @RequestParam(name = "from", defaultValue = "0")
                                                     @PositiveOrZero int from,
                                                     @RequestParam(name = "size", defaultValue = "20")
                                                     @Positive int size) {
        log.info("Получение запроса на список вещей постранично.");

        return requestClient.listAllRequestsItemsWithRequesterId(requesterId, from, size);
    }

    @GetMapping("{requestId}")
    public ResponseEntity<Object> getItemRequestById(@RequestHeader("X-Sharer-User-Id") Long requesterId,
                                                     @PathVariable Long requestId) {

        log.info("Получение запроса на вещь с определённым ID.");
        return requestClient.getItemRequestById(requesterId, requestId);
    }

}
