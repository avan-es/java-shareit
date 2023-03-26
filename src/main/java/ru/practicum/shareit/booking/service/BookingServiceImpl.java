package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.enums.Status;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingMapper;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.validation.BookingValidation;
import ru.practicum.shareit.exeptions.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.validation.ItemValidation;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserMapper;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.validation.UserValidation;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService{

    private final BookingRepository bookingRepository;
    private final UserValidation userValidation;
    private final ItemValidation itemValidation;
    private final BookingValidation bookingValidation;

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    public BookingDto saveBooking(Booking booking, Long userId) {
        Item item = itemValidation.isPresent(booking.getItemId());
        itemValidation.isAvailable(booking.getItemId());
        User user = userValidation.isPresent(userId);
        if (item.getOwner().equals(userId)) {
            throw new NotFoundException("Свою вещь бронировать ненужно.") ;
        }
        booking.setStatus(Status.WAITING.toString());
        booking.setBookerId(userId);
        bookingValidation.bookingValidation(booking);
        bookingRepository.save(booking);
        return BookingMapper.INSTANT.toBookingDto(booking,
                                                  ItemMapper.INSTANT.toItemBookingDto(item),
                                                  UserMapper.INSTANT.toUserBookingDto(user));

    }

    @Override
    public BookingDto acceptBooking(Long userId, Long bookingId, Boolean approved) {
        Booking booking = bookingValidation.isPresent(bookingId);
        userValidation.isPresent(userId);
        User booker = userValidation.isPresent(booking.getBookerId());
        Item item = itemValidation.isPresent(booking.getItemId());
        if (booking.getStatus().equals(Status.APPROVED.toString())) {
            throw new BadRequest("Бронирование уже подтверждено.") ;
        }
        if (item.getOwner().equals(userId)) {
            if (approved.equals(true)) {
                booking.setStatus(Status.APPROVED.toString());
            } else {
                booking.setStatus(Status.REJECTED.toString());
            }
            bookingRepository.save(booking);
        } else {
            log.error("Только владелиц может подтвердить бронь.");
            throw new NotFoundException("Только владелиц может подтвердить бронь.");
        }
        return BookingMapper.INSTANT.toBookingDto(booking,
                ItemMapper.INSTANT.toItemBookingDto(item),
                UserMapper.INSTANT.toUserBookingDto(booker));
    }

    @Override
    public List<Booking> getAllItems() {
        return bookingRepository.findAll();
    }

    @Override
    public BookingDto getBooking(Long bookingId, Long userId) {
        Booking booking = bookingValidation.isPresent(bookingId);
        Item item = itemValidation.isPresent(booking.getItemId());
        User booker = new User();
        if (booking.getBookerId().equals(userId)) {
            booker = userValidation.isPresent(userId);
        } else if (item.getOwner().equals(userId)) {
            booker = userValidation.isPresent(booking.getBookerId());
        }
        if ((booking.getBookerId().equals(userId)) || (item.getOwner().equals(userId))) {
            return BookingMapper.INSTANT.toBookingDto(booking,
                    ItemMapper.INSTANT.toItemBookingDto(item),
                    UserMapper.INSTANT.toUserBookingDto(booker));
        } else {
            throw new NotFoundException("Только арендодатель и арендатор могут просматривать данное бронирование.");
        }
    }

    @Override
    public List<BookingDto> getUsersBookingByState(Long userId, String state) {
        userValidation.isPresent(userId);
        LocalDateTime currentDateTime = LocalDateTime.now();
        switch (state){
            case "ALL":
                return bookingRepository.userFindAll(userId);
            case "CURRENT":
                return bookingRepository.userFindAllCurrent(userId,  currentDateTime, currentDateTime); //"APPROVED",
            case "PAST":
                return bookingRepository.userFindAllPast(userId, LocalDateTime.now());
            case "FUTURE":
                return bookingRepository.userFindAllFuture(userId, LocalDateTime.now());
            case "WAITING":
                return bookingRepository.userFindAllWaitingOrRejected(userId, state);
            case "REJECTED":
                return bookingRepository.userFindAllWaitingOrRejected(userId, state);
            default: throw new BadRequest(String.format("Unknown state: %s", state));
        }
    }

    @Override
    public List<BookingDto> getOwnerBookingByState(Long userId, String state) {
        User booker = userValidation.isPresent(userId);
        LocalDateTime currentDateTime = LocalDateTime.now();
        switch (state){
            case "ALL":
                return bookingRepository.ownerFindAll(userId);
            case "CURRENT":
                return bookingRepository.ownerFindAllCurrent(userId, currentDateTime, currentDateTime);
            case "PAST":
                return bookingRepository.ownerFindAllPast(userId, LocalDateTime.now());
            case "FUTURE":
                return bookingRepository.ownerFindAllFuture(userId, LocalDateTime.now());
            case "WAITING":
                return bookingRepository.ownerFindAllWaitingOrRejected(userId, state);
            case "REJECTED":
                return bookingRepository.ownerFindAllWaitingOrRejected(userId, state);
            default: throw new BadRequest(String.format("Unknown state: %s", state));
        }
    }
}
