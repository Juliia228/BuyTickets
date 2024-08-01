package test.task.stm.BuyTickets.controllers;

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
import test.task.stm.BuyTickets.services.TicketService;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Validated
@RestController
@RequestMapping("/ticket")
public class TicketController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/getById")
    public ResponseEntity<Ticket> getTicket(@RequestParam @Min(1) int id) {
        return ResponseEntity.ok(ticketService.find(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return ResponseEntity.ok(ticketService.findAll());
    }

    @GetMapping("/getAllNotAvailable")
    public ResponseEntity<List<Ticket>> getAllNotAvailableTickets() {
        return ResponseEntity.ok(ticketService.findAllNotAvailable());
    }

    @GetMapping("/available/getAll")
    public ResponseEntity<List<Ticket>> getAllAvailableTickets(@RequestParam(required = false) @Min(0) Integer page,
                                                               @RequestParam(required = false) @Min(1) Integer size) {
        return ResponseEntity.ok(ticketService.findAllAvailable(page, size));
    }

    @GetMapping("/available/getByDateTime")
    public ResponseEntity<List<Ticket>> getTicketsByDateTime(@RequestParam(required = false) @Min(0) Integer page,
                                                             @RequestParam(required = false) @Min(1) Integer size,
                                                             @RequestParam @FutureOrPresent Timestamp from,
                                                             @RequestParam @FutureOrPresent Timestamp to) {
        return ResponseEntity.ok(ticketService.find(page, size, from, to));
    }

    @GetMapping("/available/getByDate")
    public ResponseEntity<List<Ticket>> getTicketsByDate(@RequestParam(required = false) @Min(0) Integer page,
                                                         @RequestParam(required = false) @Min(1) Integer size,
                                                         @RequestParam @FutureOrPresent Date from,
                                                         @RequestParam @FutureOrPresent Date to) {
        return ResponseEntity.ok(ticketService.find(page, size, from, to));
    }

    @GetMapping("/available/getByDay")
    public ResponseEntity<List<Ticket>> getTicketsByDay(@RequestParam(required = false) @Min(0) Integer page,
                                                        @RequestParam(required = false) @Min(1) Integer size,
                                                        @RequestParam @FutureOrPresent Date day) {
        return ResponseEntity.ok(ticketService.find(page, size, day));
    }

    @GetMapping("/available/getByRoutePoints")
    public ResponseEntity<List<Ticket>> getTicketsByRoutePoints(@RequestParam(required = false) @Min(0) Integer page,
                                                                @RequestParam(required = false) @Min(1) Integer size,
                                                                @RequestParam @NotBlank String departure_point,
                                                                @RequestParam @NotBlank String destination_point) {
        return ResponseEntity.ok(ticketService.find(page, size, departure_point, destination_point));
    }

    @GetMapping("/available/getByDeparturePoint")
    public ResponseEntity<List<Ticket>> getTicketsByDeparturePoint(@RequestParam(required = false) @Min(0) Integer page,
                                                                   @RequestParam(required = false) @Min(1) Integer size,
                                                                   @RequestParam @NotBlank String departure_point) {
        return ResponseEntity.ok(ticketService.findByDeparturePoint(page, size, departure_point));
    }

    @GetMapping("/available/getByDestinationPoint")
    public ResponseEntity<List<Ticket>> getTicketsByDestinationPoint(@RequestParam(required = false) @Min(0) Integer page,
                                                                     @RequestParam(required = false) @Min(1) Integer size,
                                                                     @RequestParam @NotBlank String destination_point) {
        return ResponseEntity.ok(ticketService.findByDestinationPoint(page, size, destination_point));
    }

    @GetMapping("/available/getByTransporter")
    public ResponseEntity<List<Ticket>> getTicketsByTransporter(@RequestParam(required = false) @Min(0) Integer page,
                                                                @RequestParam(required = false) @Min(1) Integer size,
                                                                @RequestParam @NotBlank String transporter_name) {
        return ResponseEntity.ok(ticketService.findByTransporter(page, size, transporter_name));
    }

    @PostMapping("/add")
    public ResponseEntity<Ticket> newTicket(@Valid @RequestBody Ticket new_ticket) {
        // id необязательный параметр
        return ResponseEntity.ok(ticketService.add(new_ticket));
    }

    @PutMapping("/edit")
    public ResponseEntity<Ticket> updateTicket(@Valid @RequestBody Ticket new_ticket) {
        // id обязательный параметр
        return ResponseEntity.ok(ticketService.edit(new_ticket));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteTicket(@RequestParam @Min(1) int id) {
        ticketService.delete(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}
