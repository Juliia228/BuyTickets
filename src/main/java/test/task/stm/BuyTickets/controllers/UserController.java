package test.task.stm.BuyTickets.controllers;

import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.task.stm.BuyTickets.models.*;
import test.task.stm.BuyTickets.services.SaleService;
import test.task.stm.BuyTickets.services.UserService;

import java.util.List;

@RestController("/user")
public class UserController {
    Logger log = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;
    private final SaleService saleService;

    UserController(UserService userService, SaleService saleService) {
        this.userService = userService;
        this.saleService = saleService;
    }

    @GetMapping("/getById")
    public ResponseEntity<User> getUser(@RequestParam int id) {
        return ResponseEntity.ok(userService.find(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<User> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/boughtTickets")
    public List<Ticket> getBoughtTickets(@RequestParam int id) {
        return saleService.getTicketsByUser(id);
    }

    @PostMapping("/register")
    public ResponseEntity<User> doRegister(@RequestBody User new_user) {
        return ResponseEntity.ok(userService.registerNewUser(new_user));
    }

    @PostMapping("/buyTicket")
    public ResponseEntity<Sale> buyTicket(@RequestBody BuyTicketRequest request) throws BadRequestException {
        if (saleService.isTicketSold(request.getTicket_id())) {
            throw new BadRequestException("Ticket with id=" + request.getTicket_id() + " is sold");
        }
        return ResponseEntity.ok(saleService.createSale(request));
    }

    @PostMapping("/add")
    public ResponseEntity<User> newUser(@RequestBody User new_user) {
        // id необязательный параметр
        return ResponseEntity.ok(userService.add(new_user));
    }

    @PutMapping("/edit")
    public ResponseEntity<User> updateUser(@RequestBody User new_user) {
        // id обязательный параметр
        return ResponseEntity.ok(userService.edit(new_user));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<User> deleteUser(@RequestParam int id) {
        return ResponseEntity.ok(userService.delete(id));
    }

//    public User findById(Long id) {
//        try {
//            User user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE id=?",
//                    BeanPropertyRowMapper.newInstance(User.class), id);
//            return user;
//        } catch (Exception e) {
//            return null;
//        }
//    }
}
