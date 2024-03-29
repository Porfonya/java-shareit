package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.checker.Checker;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ru.practicum.shareit.booking.BookingState.APPROVED;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final Checker checker;

    @Transactional
    @Override
    public ItemDto createItem(Long userId, ItemDto itemDto) {
        User user = checker.checkerUserWithReturn(userId);
        Item thisItem = itemRepository.save(ItemMapper.mapToItem(itemDto));
        thisItem.setOwner(user);

        if (itemDto.getRequestId() != null) {

            ItemRequest itemRequest = checker.checkerItemRequest(itemDto.getRequestId());
            thisItem.setItemRequest(itemRequest);
            return ItemMapper.mapToItemRequestDto(thisItem);
        }
        return ItemMapper.mapToItemDto(thisItem);
    }

    @Transactional
    @Override
    public ItemDto updateItem(User user, Item existingItem, ItemDto itemDto) {

        existingItem.setName(itemDto.getName() == null ? existingItem.getName() : itemDto.getName());
        existingItem.setDescription(itemDto.getDescription() == null ?
                existingItem.getDescription() : itemDto.getDescription());
        existingItem.setAvailable(itemDto.getAvailable() == null ?
                existingItem.getAvailable() : itemDto.getAvailable());
        return ItemMapper.mapToItemDto(itemRepository.save(existingItem));
    }

    @Transactional
    @Override
    public List<ItemDto> search(String text) {
        if (text.isEmpty()) return List.of();
        List<Item> foundItems = itemRepository.search(text);
        log.info("Найдены следующие слова");
        return ItemMapper.mapToItemDto(foundItems);
    }


    @Transactional(readOnly = true)
    @Override
    public ItemDto getItemByUserId(Item item, User user) {

        List<CommentDto> commentDtos = new ArrayList<>();
        List<Comment> comments = commentRepository.findAllByItemIdOrderByCreatedDesc(item.getId());
        for (Comment comment : comments) {
            commentDtos.add(CommentMapper.mapToCommentDto(comment,
                    userRepository.getReferenceById(comment.getAuthorId())));
        }

        ItemDto itemDto = ItemMapper.mapToItemDto(item);
        itemDto.setComments(commentDtos);
        if (item.getOwner().getId().equals(user.getId())) {

            Booking last = bookingRepository.findTop1ByItemIdAndStartBeforeOrderByStartDesc(itemDto.getId(), LocalDateTime.now());
            Booking next = bookingRepository.findTop1ByItemIdAndStartAfterOrderByStartAsc(itemDto.getId(), LocalDateTime.now());

            if (last != null) {
                itemDto.setLastBooking(BookingMapper.mapToItemBooking(last));
                if (next != null) {
                    itemDto.setNextBooking(BookingMapper.mapToItemBooking(next));
                }
            }

        }
        return itemDto;

    }

    @Transactional(readOnly = true)
    @Override
    public List<ItemDto> getAllItemsByUserId(Long userId) {

        List<Item> items = itemRepository.findItemsByOwnerIdOrderById(userId);
        List<ItemDto> itemDtos = ItemMapper.mapToItemDto(items);

        for (ItemDto itemDto : itemDtos) {
            List<Booking> lastBooking = bookingRepository.findTop2ByItemIdAndStatusOrderByEndAsc(itemDto.getId(), APPROVED);
            if (lastBooking.size() >= 1) {
                itemDto.setLastBooking(BookingMapper.mapToItemBookingDto(lastBooking).get(0));

            }
            if ((lastBooking).size() == 2) {
                itemDto.setNextBooking(BookingMapper.mapToItemBookingDto(lastBooking).get(1));
            }

        }
        return itemDtos;
    }

    @Transactional
    @Override
    public CommentDto createComment(Item item, User user, CommentDto commentDto) {
        List<Booking> bookings = bookingRepository.findBookingsByBookerIdAndItemIdAndStatusAndEndBefore(user.getId(),
                item.getId(), APPROVED, LocalDateTime.now());
        if (commentDto.getText().isEmpty()) {
            throw new ValidationException("Нельзя записать пустой текст");
        }
        if (bookings.isEmpty()) {
            throw new ValidationException(String.format("Пользователь с id = %s не брал в аренду вещь с id = %s",
                    user.getId(), item.getId()));
        }
        Comment comment = new Comment();
        comment.setText(commentDto.getText());
        comment.setAuthorId(user.getId());
        comment.setItemId(item.getId());
        Comment thisComment = commentRepository.save(comment);

        return CommentMapper.mapToCommentDto(thisComment, user);
    }
}
