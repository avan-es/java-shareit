package ru.practicum.shareit.item.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Component("dbItemRepository")
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> getAllByOwner(Long id);

    List<Item> searchByNameContainingIgnoreCase(String name);

    List<Item> searchByDescriptionContainingIgnoreCase(String description);

    List<Item> findAllByRequestId (Long requestId, Pageable page);
    List<Item> findAllByRequestId (Long requestId);
}
