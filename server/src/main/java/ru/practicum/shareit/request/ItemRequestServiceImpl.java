package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRepository itemRepository;
    private final ItemRequestRepository requestRepository;


    @Override
    public ItemRequestDto createRequest(Long requesterId, ItemRequestDto itemRequestDto) {

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setRequester(requesterId);
        itemRequest.setDescription(itemRequestDto.getDescription());
        itemRequest.setCreated(LocalDateTime.now());


        return RequestMapper.mapToRequestDto(requestRepository.save(itemRequest));


    }

    @Override
    public List<ItemRequestDto> getRequested(Long requesterId) {

        List<ItemRequest> itemRequests = requestRepository.findAllByRequester(requesterId);

        List<ItemRequestDto> thisItemRequestDto = RequestMapper.itemRequestDtoList(itemRequests);
        for (ItemRequestDto itemRequestDto : thisItemRequestDto) {
            List<Item> items = itemRepository.findAllByItemRequestId(itemRequestDto.getId());
            if (items != null) {
                itemRequestDto.setItems(RequestMapper.mapToForRequestDtoList(items));
            }
        }
        return thisItemRequestDto;

    }

    @Override
    public List<ItemRequestDto> listAllRequestsItemsWithRequesterId(Long requesterId, int from, int size) {

        Pageable page = PageRequest.of(from / size, size, Sort.by(Sort.Direction.DESC, "created"));
        Page<ItemRequest> itemRequests = requestRepository.findAllByRequesterIsNot(requesterId, page);

        List<ItemRequestDto> thisItemRequestDto = RequestMapper.itemRequestDtoList(itemRequests);

        for (ItemRequestDto itemRequestDto : thisItemRequestDto) {
            List<Item> items = itemRepository.findAllByItemRequestId(itemRequestDto.getId());
            if (items != null) {
                itemRequestDto.setItems(RequestMapper.mapToForRequestDtoList(items));
            }
        }
        return thisItemRequestDto;

    }

    @Override
    public ItemRequestDto getItemRequestById(Long requesterId, ItemRequest itemRequest) {

        List<Item> items = itemRepository.findAllByItemRequestId(itemRequest.getId());
        ItemRequestDto requestDto = RequestMapper.mapToRequestDto(itemRequest);
        if (items != null) {
            requestDto.setItems(RequestMapper.mapToForRequestDtoList(items));
        }
        return requestDto;
    }
}
