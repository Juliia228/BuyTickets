package test.task.stm.BuyTickets.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import test.task.stm.BuyTickets.models.Ticket;
import test.task.stm.BuyTickets.models.request.TicketRequest;
import test.task.stm.BuyTickets.services.TicketService;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Validated
@RestController
@RequestMapping("/ticket")
@Tag(name="Билеты", description="Взаимодействие с билетами")
public class TicketController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
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
                                                               @RequestParam(required = false) @Min(1) @Parameter(description = "Размер")Integer size) {
        return ResponseEntity.ok(ticketService.findAllAvailable(offset, size));
    }

    @GetMapping("/available/getByDateTime")
    public ResponseEntity<List<Ticket>> getTicketsByDateTime(@RequestParam(required = false) @Min(0) Integer offset,
                                                             @RequestParam(required = false) @Min(1) Integer size,
                                                             @RequestParam @FutureOrPresent Timestamp from,
                                                             @RequestParam @FutureOrPresent Timestamp to) {
        return ResponseEntity.ok(ticketService.find(offset, size, from, to));
    }

    @GetMapping("/available/getByDate")
    public ResponseEntity<List<Ticket>> getTicketsByDate(@RequestParam(required = false) @Min(0) Integer offset,
                                                         @RequestParam(required = false) @Min(1) Integer size,
                                                         @RequestParam @FutureOrPresent Date from,
                                                         @RequestParam @FutureOrPresent Date to) {
        return ResponseEntity.ok(ticketService.find(offset, size, from, to));
    }

    @GetMapping("/available/getByDay")
    public ResponseEntity<List<Ticket>> getTicketsByDay(@RequestParam(required = false) @Min(0) Integer offset,
                                                        @RequestParam(required = false) @Min(1) Integer size,
                                                        @RequestParam @FutureOrPresent Date day) {
        return ResponseEntity.ok(ticketService.find(offset, size, day));
    }

    @GetMapping("/available/getByRoutePoints")
    public ResponseEntity<List<Ticket>> getTicketsByRoutePoints(@RequestParam(required = false) @Min(0) Integer offset,
                                                                @RequestParam(required = false) @Min(1) Integer size,
                                                                @RequestParam @NotBlank String departure_point,
                                                                @RequestParam @NotBlank String destination_point) {
        return ResponseEntity.ok(ticketService.find(offset, size, departure_point, destination_point));
    }

    @GetMapping("/available/getByDeparturePoint")
    public ResponseEntity<List<Ticket>> getTicketsByDeparturePoint(@RequestParam(required = false) @Min(0) Integer offset,
                                                                   @RequestParam(required = false) @Min(1) Integer size,
                                                                   @RequestParam @NotBlank String departure_point) {
        return ResponseEntity.ok(ticketService.findByDeparturePoint(offset, size, departure_point));
    }

    @GetMapping("/available/getByDestinationPoint")
    public ResponseEntity<List<Ticket>> getTicketsByDestinationPoint(@RequestParam(required = false) @Min(0) Integer offset,
                                                                     @RequestParam(required = false) @Min(1) Integer size,
                                                                     @RequestParam @NotBlank String destination_point) {
        return ResponseEntity.ok(ticketService.findByDestinationPoint(offset, size, destination_point));
    }

    @GetMapping("/available/getByTransporter")
    public ResponseEntity<List<Ticket>> getTicketsByTransporter(@RequestParam(required = false) @Min(0) Integer offset,
                                                                @RequestParam(required = false) @Min(1) Integer size,
                                                                @RequestParam @NotBlank String transporter_name) {
        return ResponseEntity.ok(ticketService.findByTransporter(offset, size, transporter_name));
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
