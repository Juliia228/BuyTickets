package test.task.stm.BuyTickets.controllers;

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
import test.task.stm.BuyTickets.services.UserService;

import java.util.List;

@Validated
@RestController
@RequestMapping("/user")
public class UserController {
    Logger log = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;
    private final SaleService saleService;

    UserController(UserService userService, SaleService saleService) {
        this.userService = userService;
        this.saleService = saleService;
    }

    @GetMapping("/getById")
    public ResponseEntity<User> getUser(@RequestParam @Min(1) int id) {
        return ResponseEntity.ok(userService.find(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/boughtTickets")
    public ResponseEntity<List<Ticket>> getBoughtTickets(@RequestParam @Min(1) int id) {
        return ResponseEntity.ok(saleService.getTicketsByUser(id));
    }

    @PostMapping("/register")
    public ResponseEntity<User> doRegister(@Valid @RequestBody UserRequest new_user) {
        return ResponseEntity.ok(userService.registerNewUser(new_user));
    }

    @PostMapping("/add")
    public ResponseEntity<User> newUser(@Valid @RequestBody UserRequest new_user) {
        return ResponseEntity.ok(userService.add(new_user));
    }

    @PutMapping("/edit")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User new_user) {
        return ResponseEntity.ok(userService.edit(new_user));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam @Min(1) int id) {
        userService.delete(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}
