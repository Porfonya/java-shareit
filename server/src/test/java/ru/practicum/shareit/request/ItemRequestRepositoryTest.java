package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Transactional
@ActiveProfiles("test")
class ItemRequestRepositoryTest {
    @Autowired
    ItemRequestRepository itemRequestRepository;

    @Test
    void findAllByRequester() {
        Long requesterId = 1L;
        List<ItemRequest> itemRequests = itemRequestRepository.findAllByRequester(requesterId);
        assertNotNull(itemRequests);

    }

    @Test
    void findAllByRequesterIsNot() {
        Long userId = 1L;
        Pageable page = PageRequest.of(0, 10);
        Page<ItemRequest> itemRequestsPage = itemRequestRepository.findAllByRequesterIsNot(userId, page);
        assertNotNull(itemRequestsPage);
    }

}