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

    @GetMapping("/getByDateTime")
    public ResponseEntity<List<Ticket>> getTicketsByDateTime(@RequestParam Timestamp from, @RequestParam Timestamp to) {
        // from не позднее текущего момента времени
        return ResponseEntity.ok(ticketService.find(from, to));
    }

    @GetMapping("/getByDate")
    public ResponseEntity<List<Ticket>> getTicketsByDate(@RequestParam Date from, @RequestParam Date to) {
        return ResponseEntity.ok(ticketService.find(from, to));
    }

    @GetMapping("/getByDay")
    public ResponseEntity<List<Ticket>> getTicketsByDay(@RequestParam Date day) {
        return ResponseEntity.ok(ticketService.find(day));
    }

    @GetMapping("/getByTime")
    public ResponseEntity<List<Ticket>> getTicketsByTime(@RequestParam Time from, @RequestParam Time to) {
        return ResponseEntity.ok(ticketService.find(from, to));
    }

    @GetMapping("/getByRoute")
    public List<Ticket> getTicketsByRoute(@RequestParam String departure_point, @RequestParam String destination_point) {
        return ResponseEntity.ok(ticketService.find(departure_point, destination_point));
    }

    @GetMapping("/getByDeparturePoint")
    public List<Ticket> getTicketsByDeparturePoint(@RequestParam String departure_point) {
        return ResponseEntity.ok(ticketService.findByDeparturePoint(departure_point));
    }

    @GetMapping("/getByDestinationPoint")
    public List<Ticket> getTicketsByDestinationPoint(@RequestParam String destination_point) {
        return ResponseEntity.ok(ticketService.findByDestinationPoint(destination_point));
    }

    @GetMapping("/getByTransporter")
    public List<Ticket> getTicketsByTransporter(@RequestParam String transporter_name) {
        return ResponseEntity.ok(ticketService.findByTransporter(transporter_name));
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
    public ResponseEntity<Ticket> deleteTicket(@RequestParam int id) {
        return ResponseEntity.ok(ticketService.delete(id));
    }
}
