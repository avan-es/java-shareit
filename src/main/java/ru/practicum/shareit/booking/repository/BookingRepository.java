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


    List<BookingDto> getBookingByItemIdAndEndIsBefore(Long itemId, LocalDateTime now);
/*    @Query(value = "select * " +
            "FROM BOOKINGS b " +
            "WHERE b.ITEM_ID = ?1 " +
            "AND b.START_DATE < ?2 " +
            "ORDER BY b.end_date  DESC LIMIT 1 ", nativeQuery = true)*/

/*    @Query(value = "select b.id AS bookingId, b.booker_id AS bookerId, b.start_date AS bookingDate " +
            "FROM BOOKINGS b " +
            "WHERE b.ITEM_ID = ?1 " +
            "AND b.START_DATE < ?2 " +
            "ORDER BY b.START_DATE  DESC LIMIT 1 ", nativeQuery = true)*/
  /*  @Query(value = "select bk.ID AS id, bk.START_DATE as start, bk.END_DATE AS end, bk.STATUS AS status, booker.ID, it.ID, it.NAME " +
            "from BOOKINGS as bk " +
            "join Items as it on bk.ITEM_ID  = it.ID " +
            "join Users as booker on bk.BOOKER_ID  = booker.id " +
            "WHERE bk.ITEM_ID = ?1 " +
            "AND bk.START_DATE < ?2 " +
            "order by start  DESC  LIMIT 1", nativeQuery = true)*/
@Query(value = "select * " +
        "FROM BOOKINGS b " +
        "WHERE b.ITEM_ID = ?1 " +
        "AND b.START_DATE < ?2 " +
        "ORDER BY b.end_date  DESC LIMIT 1 ", nativeQuery = true)
    Booking getLastBooking (Long itemId, LocalDateTime now);

    /*@Query(value = "select * " +
            "FROM BOOKINGS b " +
            "WHERE b.ITEM_ID = ?1 " +
            "AND b.START_DATE > ?2 " +
            "ORDER BY b.end_date  DESC LIMIT 1 ", nativeQuery = true)*/
    /*@Query(value = "select b.id AS bookingId, b.booker_id AS bookerId, b.start_date AS bookingDate " +
            "FROM BOOKINGS b " +
            "WHERE b.ITEM_ID = ?1 " +
            "AND b.START_DATE > ?2 " +
            "ORDER BY b.START_DATE  DESC LIMIT 1 ", nativeQuery = true)*/
    @Query(value = "select * " +
            "FROM BOOKINGS b " +
            "WHERE b.ITEM_ID = ?1 " +
            "AND b.START_DATE > ?2 " +
            "ORDER BY b.end_date  DESC LIMIT 1 ", nativeQuery = true)
    Booking getNextBooking (Long itemId, LocalDateTime now);




}
