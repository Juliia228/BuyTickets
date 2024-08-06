package test.task.stm.BuyTickets.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import test.task.stm.BuyTickets.models.Ticket;
import test.task.stm.BuyTickets.models.User;
import test.task.stm.BuyTickets.models.DTO.TicketRequest;
import test.task.stm.BuyTickets.services.TicketService;
import test.task.stm.BuyTickets.services.UserService;

import java.sql.Timestamp;
import java.util.List;

@Validated
@RestController
@RequestMapping("/ticket")
@Tag(name = "Билеты", description = "Взаимодействие с билетами")
public class TicketController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final TicketService ticketService;
    private final UserService userService;

    public TicketController(TicketService ticketService, UserService userService) {
        this.ticketService = ticketService;
        this.userService = userService;
    }

    @Operation(
            summary = "Получение купленных билетов для текущего пользователя",
            description = "Позволяет получить список всех купленных билетов для текущего пользователя"
    )
    @GetMapping("/boughtTickets")
    public ResponseEntity<List<Ticket>> getBoughtTickets() {
        User current_user = userService.getCurrentUser();
        return ResponseEntity.ok(ticketService.findByUser(current_user.getId()));
    }

    @Operation(
            summary = "Получить данные о билете",
            description = "Позволяет найти билет по id"
    )
    @GetMapping("/getById")
    public ResponseEntity<Ticket> getTicket(@RequestParam @Min(1) @Parameter(description = "Идентификатор билета") int id) {
        return ResponseEntity.ok(ticketService.find(id));
    }

    @Operation(
            summary = "Получить все билеты"
    )
    @GetMapping("/getAll")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return ResponseEntity.ok(ticketService.findAll());
    }

    @Operation(
            summary = "Получить проданные билеты",
            description = "Позволяет найти недоступные для покупки билеты"
    )
    @GetMapping("/getAllNotAvailable")
    public ResponseEntity<List<Ticket>> getAllNotAvailableTickets() {
        return ResponseEntity.ok(ticketService.findAllNotAvailable());
    }

    @Operation(
            summary = "Получить доступные билеты",
            description = "Позволяет найти еще не проданные билеты (с возможностью пагинации)"
    )
    @GetMapping("/available/getAll")
    public ResponseEntity<List<Ticket>> getAllAvailableTickets(@RequestParam(required = false) @Min(0) @Parameter(description = "Страница") Integer offset,
                                                               @RequestParam(required = false) @Min(1) @Parameter(description = "Размер") Integer size) {
        return ResponseEntity.ok(ticketService.findAllAvailable(offset, size));
    }

    @Operation(
            summary = "Получить доступные билеты по параметрам фильтрации",
            description = "Позволяет найти еще не проданные билеты по заданным параметрам (с возможностью пагинации)"
    )
    @GetMapping("/available/get")
    public ResponseEntity<List<Ticket>> getTicketsByParams(@RequestParam(required = false) @Min(0) Integer offset,
                                                           @RequestParam(required = false) @Min(1) Integer size,
                                                           @RequestParam(required = false) @FutureOrPresent Timestamp from,
                                                           @RequestParam(required = false) @FutureOrPresent Timestamp to,
                                                           @RequestParam(required = false) @Size(min = 1) String departure_point,
                                                           @RequestParam(required = false) @Size(min = 1) String destination_point,
                                                           @RequestParam(required = false) @Size(min = 1) String transporter_name) {
        return ResponseEntity.ok(ticketService.find(offset, size, from, to, departure_point, destination_point, transporter_name));
    }

    @Operation(
            summary = "Добавление билета",
            description = "Позволяет добавить новый билет"
    )
    @PostMapping("/add")
    public ResponseEntity<Ticket> newTicket(@Valid @RequestBody @Parameter(description = "Данные о билете") TicketRequest new_ticket) {
        return ResponseEntity.ok(ticketService.add(new_ticket));
    }

    @Operation(
            summary = "Обновление билета",
            description = "Позволяет обновить данные билета"
    )
    @PutMapping("/edit")
    public ResponseEntity<Ticket> updateTicket(@Valid @RequestBody @Parameter(description = "Новые данные о билете") Ticket new_ticket) {
        return ResponseEntity.ok(ticketService.edit(new_ticket));
    }

    @Operation(
            summary = "Удаление билета",
            description = "Позволяет удалить билет по id"
    )
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteTicket(@RequestParam @Min(1) @Parameter(description = "Идентификатор билет") int id) {
        ticketService.delete(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}
