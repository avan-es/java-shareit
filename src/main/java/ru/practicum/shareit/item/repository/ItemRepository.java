package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Component("dbItemRepository")
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> getAllByOwner (Long id);
    List<Item> searchByNameContainingIgnoreCase (String name);
    List<Item> searchByDescriptionContainingIgnoreCase (String description);

/*    @Query(value = "select b.id as bookingId, b.booker_id as bookerId, b.start_date as bookingDate from bookings b " +
    "where b.item_id = ?1 and b.start_date < ?2 " +
    "order by b.start_date DESC LIMIT 1", nativeQuery = true)
    ItemBookingForOwnerDto getLastBooking(Long itemId, LocalDateTime now);

    @Query(value = "select b.id as bookingId, b.booker_id as bookerId, b.start_date as bookingDate from bookings b " +
            "where b.item_id = ?1 and b.start_date > ?2 and not b.status = 'REJECTED' " +
            "order by b.start_date DESC LIMIT 1", nativeQuery = true)
    ItemBookingForOwnerDto getNextBooking(Long itemId, LocalDateTime now);

    @Query(value = "select b.id as bookingId, b.booker_id as bookerId, b.start_date as bookingDate from bookings b " +
            "where b.item_id in (?1) and b.start_date < ?2 " +
            "order by b.start_date DESC LIMIT 1", nativeQuery = true)
    List<ItemBookingForOwnerDto> getLastBookings(List<Long> itemId, LocalDateTime now);

    @Query(value = "select b.id as bookingId, b.booker_id as bookerId, b.start_date as bookingDate from bookings b " +
            "where b.item_id in (?1) and b.start_date > ?2 and not b.status = 'REJECTED' " +
            "order by b.start_date DESC LIMIT 1", nativeQuery = true)
    List<ItemBookingForOwnerDto> getNextBookings(List<Long> itemId, LocalDateTime now);*/
}
