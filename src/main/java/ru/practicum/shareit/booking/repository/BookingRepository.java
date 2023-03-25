package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.List;

@Component("dbBookingRepository")
public interface BookingRepository extends JpaRepository<Booking, Long> {


    @Query("select new ru.practicum.shareit.booking.dto.BookingDto(bk.id, bk.start, bk.end, bk.status, booker.id, it.id, it.name) " +
            "from Booking as bk " +
            "join Item as it on bk.itemId = it.id " +
            "join User as booker on bk.bookerId = booker.id ")
    List<Booking> findAllByBookerIdAndStatusOrderByStart (Long userId, String status);

    List<Booking> findAllByBookerIdAndStatusAndStartIsAfter(Long userId, String status, LocalDateTime now);

    List<Booking> findAllByBookerIdAndStatusAndEndIsAfter(Long userId, String status, LocalDateTime now);

    List<Booking> findAllByBookerIdAndStatusAndEndIsBefore(Long userId, String status, LocalDateTime now);

    List<Booking> findAllByBookerIdOrderByStartDesc(Long userId);
}
