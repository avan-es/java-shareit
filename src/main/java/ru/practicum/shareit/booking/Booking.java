package ru.practicum.shareit.booking;

import lombok.Data;
import ru.practicum.shareit.booking.enums.Status;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "bookings")
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time")
    @NotNull
    private LocalDate startTime;

    @Column(name = "end_time")
    @NotNull
    private LocalDate endTime;

    @Column(name = "item_id")
    @NotNull
    private Long itemId;

    @Column(name = "booker_id")
    @NotNull
    private Long bookerId;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;

}
