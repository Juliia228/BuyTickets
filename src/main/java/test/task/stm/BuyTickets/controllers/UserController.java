package test.task.stm.BuyTickets.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import test.task.stm.BuyTickets.models.*;
import test.task.stm.BuyTickets.models.request.UserRequest;
import test.task.stm.BuyTickets.services.SaleService;
import test.task.stm.BuyTickets.services.TicketService;
import test.task.stm.BuyTickets.services.UserService;

import java.util.List;

@Validated
@RestController
@RequestMapping("/user")
@Tag(name="Пользователи", description="Взаимодействие с пользователями")
public class UserController {
    Logger log = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;
    private final TicketService ticketService;

    UserController(UserService userService, TicketService ticketService) {
        this.userService = userService;
        this.ticketService = ticketService;
    }

    @Operation(
            summary = "Получить данные о пользователе",
            description = "Позволяет найти пользователя по id"
    )
    @GetMapping("/getById")
    public ResponseEntity<User> getUser(@RequestParam @Min(1) @Parameter(description = "Идентификатор пользователя") int id) {
        return ResponseEntity.ok(userService.find(id));
    }

    @Operation(
            summary = "Получить всех пользователей"
    )
    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @Operation(
            summary = "Получение купленных билетов для текущего пользователя",
            description = "Позволяет получить список всех купленных билетов для текущего пользователя по его id"
    )
    @GetMapping("/boughtTickets")
    public ResponseEntity<List<Ticket>> getBoughtTickets(@RequestParam @Min(1) @Parameter(description = "Идентификатор пользователя") int id) {
        return ResponseEntity.ok(ticketService.findByUser(id));
    }

    @Operation(
            summary = "Регистрация пользователя",
            description = "Позволяет добавить нового пользователя"
    )
    @PostMapping("/register")
    public ResponseEntity<User> doRegister(@Valid @RequestBody @Parameter(description = "Данные пользователя") UserRequest new_user) {
        return ResponseEntity.ok(userService.add(new_user));
    }

    @Operation(
            summary = "Обновление пользователя",
            description = "Позволяет обновить данные пользователя"
    )
    @PutMapping("/edit")
    public ResponseEntity<User> updateUser(@Valid @RequestBody @Parameter(description = "Новые данные о пользователе") User new_user) {
        return ResponseEntity.ok(userService.edit(new_user));
    }

    @Operation(
            summary = "Удаление пользователя",
            description = "Позволяет удалить пользователя по id"
    )
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam @Min(1) @Parameter(description = "Идентификатор пользователя") int id) {
        userService.delete(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}
