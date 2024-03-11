package ru.practicum.shareit.request;

import java.util.List;

public interface ItemRequestService {
    ItemRequestDto createRequest(Long requesterId, ItemRequestDto itemRequestDto);

    List<ItemRequestDto> getRequested(Long requesterId);

    public List<ItemRequestDto> listAllRequestsItemsWithRequesterId(Long userId, int from, int size);

    ItemRequestDto getItemRequestById(Long requesterId, ItemRequest itemRequest);


}
