package ru.practicum.shareit.booking.model;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date")
    private LocalDateTime start;

    @Column(name = "end_date")
    private LocalDateTime end;

    @Column(name = "item_id")
//    @NotNull
    private Long itemId;

    @Column(name = "booker_id")
//    @NotNull
    private Long bookerId;

//    @Enumerated(EnumType.STRING)
//    @NotNull
    private String status;

}
