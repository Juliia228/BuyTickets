package test.task.stm.BuyTickets.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import test.task.stm.BuyTickets.models.*;
import test.task.stm.BuyTickets.models.request.UserRequest;
import test.task.stm.BuyTickets.services.JwtService;
import test.task.stm.BuyTickets.services.UserService;

import java.util.List;

@Validated
@RestController
@RequestMapping("/user")
@Tag(name = "Пользователи", description = "Взаимодействие с пользователями")
public class UserController {
    Logger log = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    UserController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Operation(
            summary = "Получить данные о пользователе",
            description = "Позволяет найти пользователя по id"
    )
    @GetMapping("/getById")
    public ResponseEntity<User> getUser(@RequestParam @Min(1) @Parameter(description = "Идентификатор пользователя") int id) {
        return ResponseEntity.ok(userService.find(id));
    }

    @Operation(
            summary = "Авторизация пользователя",
            description = "Позволяет аутентифицировать пользователя по логину и паролю"
    )
    @GetMapping("/signIn")
    public ResponseEntity<String> authorizeUser(@RequestParam @Email(message = "login must be email")
                                              @NotBlank(message = "login is required")
                                              @Parameter(description = "Логин (электронная почта)", example = "mail@mail.ru")
                                              String login,
                                              @RequestParam @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,}$",
                                                      message = "password must be at least 8 characters long, contain at least 1 digit, 1 uppercase letter, 1 lowercase letter and 1 special character")
                                              @NotBlank(message = "password is required")
                                              @Parameter(description = "Пароль", example = "creativePassword1!")
                                              String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
        if (authentication.isAuthenticated()){
            UserDetails userDetails = new UserDetailsImpl(userService.findByLogin(login));
            return ResponseEntity.ok(jwtService.generateToken(userDetails));
        } else {
            throw new UsernameNotFoundException("User is not authorized");
        }
    }

    @Operation(
            summary = "Получить всех пользователей"
    )
    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

//    @Operation(
//            summary = "Получение купленных билетов для текущего пользователя",
//            description = "Позволяет получить список всех купленных билетов для текущего пользователя по его id"
//    )
//    @GetMapping("/boughtTickets")
//    //public ResponseEntity<List<Ticket>> getBoughtTickets(//убрать @RequestParam @Min(1) @Parameter(description = "Идентификатор пользователя") int id) {
//        return ResponseEntity.ok(ticketService.findByUser(id));
//    }

    @Operation(
            summary = "Регистрация пользователя",
            description = "Позволяет зарегистрировать и аутентифицировать нового пользователя"
    )
    @PostMapping("/register")
    public ResponseEntity<String> doRegister(@Valid @RequestBody @Parameter(description = "Данные пользователя") UserRequest new_user) {
        String token = userService.createToken(new_user);
        //UserDetails userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        return ResponseEntity.ok(token);
    }

    @Operation(
            summary = "Выдача прав администратора",
            description = "Позволяет сделать пользователя админом"
    )
    @PostMapping("/setAdmin")
    public ResponseEntity<User> setAdminForUser(@RequestParam @Min(1) @Parameter(description = "Идентификатор пользователя") int id) {
        return ResponseEntity.ok(userService.setRoleAdmin(id));
    }

    @Operation(
            summary = "Обновление пользователя",
            description = "Позволяет обновить данные пользователя"
    )
    @PutMapping("/edit")
    public ResponseEntity<User> updateUser(@Valid @RequestBody @Parameter(description = "Новые данные о пользователе") User new_user) {
        return ResponseEntity.ok(userService.edit(new_user));
    }

    @Operation(
            summary = "Удаление пользователя",
            description = "Позволяет удалить пользователя по id"
    )
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam @Min(1) @Parameter(description = "Идентификатор пользователя") int id) {
        userService.delete(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}
