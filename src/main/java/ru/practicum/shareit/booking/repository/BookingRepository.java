package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.List;

@Component("dbBookingRepository")
public interface BookingRepository extends JpaRepository<Booking, Long> {

//Booker
    @Query("select new ru.practicum.shareit.booking.dto.BookingDto(bk.id, bk.start, bk.end, bk.status, booker.id, it.id, it.name) " +
            "from Booking as bk " +
            "join Item as it on bk.itemId = it.id " +
            "join User as booker on bk.bookerId = booker.id " +
            "WHERE booker.id = ?1 " +
            "order by bk.start DESC ")
    List<BookingDto> userFindAll(Long userId);

    @Query("select new ru.practicum.shareit.booking.dto.BookingDto(bk.id, bk.start, bk.end, bk.status, booker.id, it.id, it.name) " +
            "from Booking as bk " +
            "join Item as it on bk.itemId = it.id " +
            "join User as booker on bk.bookerId = booker.id " +
            "WHERE booker.id = ?1 " +
            "AND bk.status = ?2 " +
            "AND bk.end > ?3" +
            "order by bk.start DESC ")
    List<BookingDto> userFindAllCurrent(Long userId, String status, LocalDateTime now);

    @Query("select new ru.practicum.shareit.booking.dto.BookingDto(bk.id, bk.start, bk.end, bk.status, booker.id, it.id, it.name) " +
            "from Booking as bk " +
            "join Item as it on bk.itemId = it.id " +
            "join User as booker on bk.bookerId = booker.id " +
            "WHERE booker.id = ?1 " +
            "AND bk.end < ?2" +
            "order by bk.start DESC ")
    List<BookingDto> userFindAllPast(Long userId, LocalDateTime now);

    @Query("select new ru.practicum.shareit.booking.dto.BookingDto(bk.id, bk.start, bk.end, bk.status, booker.id, it.id, it.name) " +
            "from Booking as bk " +
            "join Item as it on bk.itemId = it.id " +
            "join User as booker on bk.bookerId = booker.id " +
            "WHERE booker.id = ?1 " +
            "AND bk.status in ('WAITING','APPROVED') " +
            "AND bk.end > ?2 " +
            "order by bk.start DESC ")
    List<BookingDto> userFindAllFuture(Long userId, LocalDateTime now);

    @Query("select new ru.practicum.shareit.booking.dto.BookingDto(bk.id, bk.start, bk.end, bk.status, booker.id, it.id, it.name) " +
            "from Booking as bk " +
            "join Item as it on bk.itemId = it.id " +
            "join User as booker on bk.bookerId = booker.id " +
            "WHERE booker.id = ?1 " +
            "AND bk.status = ?2 " +
            "order by bk.start DESC ")
    List<BookingDto> userFindAllWaitingOrRejected(Long userId, String status);

    //Owner

    @Query("select new ru.practicum.shareit.booking.dto.BookingDto(bk.id, bk.start, bk.end, bk.status, booker.id, it.id, it.name) " +
            "from Booking as bk " +
            "join Item as it on bk.itemId = it.id " +
            "join User as booker on bk.bookerId = booker.id " +
            "WHERE it.owner = ?1 " +
            "order by bk.start DESC ")
    List<BookingDto> ownerFindAll(Long userId);

    @Query("select new ru.practicum.shareit.booking.dto.BookingDto(bk.id, bk.start, bk.end, bk.status, booker.id, it.id, it.name) " +
            "from Booking as bk " +
            "join Item as it on bk.itemId = it.id " +
            "join User as booker on bk.bookerId = booker.id " +
            "WHERE it.owner = ?1 " +
            "AND bk.status = ?2 " +
            "AND bk.end > ?3" +
            "order by bk.start DESC ")
    List<BookingDto> ownerFindAllCurrent(Long userId, String status, LocalDateTime now);

    @Query("select new ru.practicum.shareit.booking.dto.BookingDto(bk.id, bk.start, bk.end, bk.status, booker.id, it.id, it.name) " +
            "from Booking as bk " +
            "join Item as it on bk.itemId = it.id " +
            "join User as booker on bk.bookerId = booker.id " +
            "WHERE it.owner = ?1 " +
            "AND bk.end < ?2" +
            "order by bk.start DESC ")
    List<BookingDto> ownerFindAllPast(Long userId, LocalDateTime now);

    @Query("select new ru.practicum.shareit.booking.dto.BookingDto(bk.id, bk.start, bk.end, bk.status, booker.id, it.id, it.name) " +
            "from Booking as bk " +
            "join Item as it on bk.itemId = it.id " +
            "join User as booker on bk.bookerId = booker.id " +
            "WHERE it.owner = ?1 " +
            "AND bk.status in ('WAITING','APPROVED') " +
            "AND bk.end > ?2 " +
            "order by bk.start DESC ")
    List<BookingDto> ownerFindAllFuture(Long userId, LocalDateTime now);

    @Query("select new ru.practicum.shareit.booking.dto.BookingDto(bk.id, bk.start, bk.end, bk.status, booker.id, it.id, it.name) " +
            "from Booking as bk " +
            "join Item as it on bk.itemId = it.id " +
            "join User as booker on bk.bookerId = booker.id " +
            "WHERE it.owner = ?1 " +
            "AND bk.status = ?2 " +
            "order by bk.start DESC ")
    List<BookingDto> ownerFindAllWaitingOrRejected(Long userId, String status);






}
