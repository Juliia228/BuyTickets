package test.task.stm.BuyTickets.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name="Перевозчики", description="Взаимодействие с перевозчиками")
public class TransporterController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final TransporterService transporterService;

    public TransporterController(TransporterService transporterService) {
        this.transporterService = transporterService;
    }

    @Operation(
            summary = "Получить данные о перевозчике",
            description = "Позволяет найти перевозчика по названию"
    )
    @GetMapping("/getByName")
    public ResponseEntity<Transporter> getTransporter(@RequestParam @NotBlank @Parameter(description = "Название перевозчика") String name) {
        return ResponseEntity.ok(transporterService.find(name));
    }

    @Operation(
            summary = "Получить всех перевозчиков"
    )
    @GetMapping("/getAll")
    public ResponseEntity<List<Transporter>> getAllTransporters() {
        return ResponseEntity.ok(transporterService.findAll());
    }

    @Operation(
            summary = "Добавление перевозчика",
            description = "Позволяет добавить нового перевозчика"
    )
    @PostMapping("/add")
    public ResponseEntity<Transporter> newTransporter(@Valid @RequestBody @Parameter(description = "Данные о перевозчике") Transporter new_transporter) {
        return ResponseEntity.ok(transporterService.add(new_transporter));
    }

    @Operation(
            summary = "Обновление перевозчика",
            description = "Позволяет обновить данные перевозчика"
    )
    @PutMapping("/edit")
    public ResponseEntity<Transporter> updateTransporter(@Valid @RequestBody @Parameter(description = "Новые данные о перевозчике") Transporter new_transporter) {
        return ResponseEntity.ok(transporterService.edit(new_transporter));
    }

    @Operation(
            summary = "Удаление перевозчика",
            description = "Позволяет удалить перевозчика по названию"
    )
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteTransporter(@RequestParam @NotBlank @Parameter(description = "Название перевозчика") String name) {
        transporterService.delete(name);
        return ResponseEntity.ok("Deleted successfully");
    }
}
