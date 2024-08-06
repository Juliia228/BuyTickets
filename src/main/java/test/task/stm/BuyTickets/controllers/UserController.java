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
import test.task.stm.BuyTickets.models.*;
import test.task.stm.BuyTickets.models.DTO.*;
import test.task.stm.BuyTickets.services.RefreshTokenService;
import test.task.stm.BuyTickets.services.UserService;

import java.util.List;

@Validated
@RestController
@RequestMapping("/user")
@Tag(name = "Пользователи", description = "Взаимодействие с пользователями")
public class UserController {
    Logger log = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    UserController(UserService userService, RefreshTokenService refreshTokenService) {
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
    }

    @Operation(
            summary = "Регистрация пользователя",
            description = "Позволяет добавить нового пользователя"
    )
    @PostMapping("/signUp")
    public ResponseEntity<User> register(@Valid @RequestBody @Parameter(description = "Данные пользователя") UserRequest new_user) {
        return ResponseEntity.ok(userService.register(new_user));
    }

    @Operation(
            summary = "Авторизация пользователя",
            description = "Позволяет аутентифицировать пользователя по логину и паролю"
    )
    @PostMapping("/signIn")
    public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody @Parameter(description = "Данные пользователя для авторизации") AuthRequest request) {
        return ResponseEntity.ok(userService.authenticate(request.getLogin(), request.getPassword()));
    }

    @Operation(
            summary = "Обновление access токена по refresh токену",
            description = "Позволяет зарегистрировать и аутентифицировать нового пользователя"
    )
    @PostMapping("/refreshToken")
    public ResponseEntity<RefreshResponse> refreshToken(@Valid @RequestBody @Parameter(description = "Данные пользователя") RefreshRequest refreshRequest) {
        return ResponseEntity.ok(refreshTokenService.generateAccessToken(refreshRequest.getRefresh_token()));
    }

    @Operation(
            summary = "Выдача прав администратора",
            description = "Позволяет выдать пользователю права администратора"
    )
    @PostMapping("/setAdmin")
    public ResponseEntity<User> setAdminForUser(@RequestParam @Min(1) @Parameter(description = "Идентификатор пользователя") int id) {
        return ResponseEntity.ok(userService.setRoleAdmin(id));
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
            summary = "Получить всех пользователей"
    )
    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
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
