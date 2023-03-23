package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.model.Booking;

@Component("dbBookingRepository")
public interface BookingRepository extends JpaRepository<Booking, Long> {

}
