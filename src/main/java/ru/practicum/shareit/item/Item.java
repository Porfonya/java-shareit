package ru.practicum.shareit.item;

import lombok.*;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ITEMS", schema = "public")
public class Item {
    @Id
    @Column(name = "item_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "available", nullable = false)
    private Boolean available;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User owner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "request_id")
    private ItemRequest itemRequest;

    @ElementCollection
    @CollectionTable(name = "COMMENTS", joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "text")
    private Set<String> comment = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id) && Objects.equals(name, item.name)
                && Objects.equals(description, item.description)
                && Objects.equals(available, item.available)
                && Objects.equals(owner, item.owner)
                && Objects.equals(itemRequest, item.itemRequest)
                && Objects.equals(comment, item.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, available, owner, itemRequest, comment);
    }
}
