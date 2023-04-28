package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserGatewayDto;
import ru.practicum.shareit.user.dto.UserGatewayForUpdateDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {
    private final UserClient userClient;

    @PostMapping
    public ResponseEntity<Object> addUser(
                                    @RequestBody @Valid UserGatewayDto user) {
        log.info("Создание пользователя с именем: {} и почтой: {}.", user.getName(),
                user.getEmail().replaceAll("(?<=.{2}).(?=[^@]*?@)", "*"));
        return userClient.addUser(user);
    }

    @GetMapping
    public ResponseEntity<Object> getUsers() {
        log.info("Запрос всех пользователей.");
        return userClient.getUsers();
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Object> updateUser(
                                    @RequestBody @Valid UserGatewayForUpdateDto userDto,
                                    @Valid @Positive(message = "ID пользователя должен быть > 0.")
                                    @PathVariable
                                    Long userId) {
        log.info("Обновление пользователя с userId={}.", userId);
        return userClient.updateUser(userDto, userId);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUser(
                                    @Valid @Positive(message = "ID пользователя должен быть > 0.")
                                    @PathVariable
                                    Long userId) {
        log.info("Запрос на получение пользователя с userId={}.", userId);
        return userClient.getUser(userId);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(
                                    @Valid @Positive(message = "ID пользователя должен быть > 0.")
                                    @PathVariable
                                    Long userId) {
        log.info("Удаление пользователя с userId={}.", userId);
        return userClient.deleteUser(userId);
    }
}