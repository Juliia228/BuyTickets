package test.task.stm.BuyTickets.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.task.stm.BuyTickets.models.Route;
import test.task.stm.BuyTickets.services.RouteService;

import java.util.List;

@RestController("/route")
public class RouteController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/getById")
    public ResponseEntity<Route> getRoute(@RequestParam int id) {
        return ResponseEntity.ok(routeService.find(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Route>> getAllRoutes() {
        return ResponseEntity.ok(routeService.findAll());
    }

    @PostMapping("/add")
    public ResponseEntity<Route> newRoute(@RequestBody Route new_route) {
        // id необязательный параметр
        return ResponseEntity.ok(routeService.add(new_route));
    }

    @PutMapping("/edit")
    public ResponseEntity<Route> updateRoute(@RequestBody Route new_route) {
        // id обязательный параметр
        return ResponseEntity.ok(routeService.edit(new_route));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Route> deleteRoute(@RequestParam int id) {
        return ResponseEntity.ok(routeService.delete(id));
    }
}
