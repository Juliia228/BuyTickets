package test.task.stm.BuyTickets.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import test.task.stm.BuyTickets.models.Route;
import test.task.stm.BuyTickets.models.RouteRequest;
import test.task.stm.BuyTickets.services.RouteService;

import java.util.List;

@Validated
@RestController
@RequestMapping("/route")
public class RouteController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/getById")
    public ResponseEntity<Route> getRoute(@RequestParam @Min(1) int id) {
        return ResponseEntity.ok(routeService.find(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Route>> getAllRoutes() {
        return ResponseEntity.ok(routeService.findAll());
    }

    @PostMapping("/add")
    public ResponseEntity<Route> newRoute(@Valid @RequestBody RouteRequest new_route) {
        return ResponseEntity.ok(routeService.add(new_route));
    }

    @PutMapping("/edit")
    public ResponseEntity<Route> updateRoute(@Valid @RequestBody Route new_route) {
        return ResponseEntity.ok(routeService.edit(new_route));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteRoute(@RequestParam @Min(1) int id) {
        routeService.delete(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}
