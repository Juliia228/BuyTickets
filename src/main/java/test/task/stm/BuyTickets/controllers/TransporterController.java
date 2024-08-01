package test.task.stm.BuyTickets.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import test.task.stm.BuyTickets.models.Transporter;
import test.task.stm.BuyTickets.services.TransporterService;

import java.util.List;

@Validated
@RestController
@RequestMapping("/transporter")
public class TransporterController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final TransporterService transporterService;

    public TransporterController(TransporterService transporterService) {
        this.transporterService = transporterService;
    }

    @GetMapping("/getById")
    public ResponseEntity<Transporter> getTransporter(@RequestParam @NotBlank String name) {
        return ResponseEntity.ok(transporterService.find(name));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Transporter>> getAllTransporters() {
        return ResponseEntity.ok(transporterService.findAll());
    }

    @PostMapping("/add")
    public ResponseEntity<Transporter> newTransporter(@Valid @RequestBody Transporter new_transporter) {
        return ResponseEntity.ok(transporterService.add(new_transporter));
    }

    @PutMapping("/edit")
    public ResponseEntity<Transporter> updateTransporter(@Valid @RequestBody Transporter new_transporter) {
        // id обязательный параметр
        return ResponseEntity.ok(transporterService.edit(new_transporter));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Transporter> deleteTransporter(@RequestParam int id) {
        return ResponseEntity.ok(transporterService.delete(id));
    }
}
