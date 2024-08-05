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
import test.task.stm.BuyTickets.models.Route;
import test.task.stm.BuyTickets.models.request.RouteRequest;
import test.task.stm.BuyTickets.services.RouteService;

import java.util.List;

@Validated
@RestController
@RequestMapping("/route")
@Tag(name="Маршруты", description="Взаимодействие с маршрутами")
public class RouteController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @Operation(
            summary = "Получить данные о маршруте",
            description = "Позволяет найти маршрут по id"
    )
    @GetMapping("/getById")
    public ResponseEntity<Route> getRoute(@RequestParam @Min(1) @Parameter(description = "Идентификатор маршрута") int id) {
        return ResponseEntity.ok(routeService.find(id));
    }

    @Operation(
            summary = "Получить все маршруты"
    )
    @GetMapping("/getAll")
    public ResponseEntity<List<Route>> getAllRoutes() {
        return ResponseEntity.ok(routeService.findAll());
    }

    @Operation(
            summary = "Добавление маршрута",
            description = "Позволяет добавить новый маршрут"
    )
    @PostMapping("/add")
    public ResponseEntity<Route> newRoute(@Valid @RequestBody @Parameter(description = "Данные о маршруте") RouteRequest new_route) {
        return ResponseEntity.ok(routeService.add(new_route));
    }

    @Operation(
            summary = "Обновление маршрута",
            description = "Позволяет обновить данные маршрута"
    )
    @PutMapping("/edit")
    public ResponseEntity<Route> updateRoute(@Valid @RequestBody @Parameter(description = "Новые данные о маршруте") Route new_route) {
        return ResponseEntity.ok(routeService.edit(new_route));
    }

    @Operation(
            summary = "Удаление маршрута",
            description = "Позволяет удалить маршрут по id"
    )
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteRoute(@RequestParam @Min(1) @Parameter(description = "Идентификатор маршрута") int id) {
        routeService.delete(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}
