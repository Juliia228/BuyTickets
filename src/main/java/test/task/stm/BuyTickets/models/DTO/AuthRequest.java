package test.task.stm.BuyTickets.models.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Данные запроса для авторизации")
public class AuthRequest {
    @Email(message = "login must be email")
    @NotBlank(message = "login is required")
    @Schema(description = "Логин (электронная почта)", example = "example@example.example")
    private String login;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,}$",
            message = "password must be at least 8 characters long, contain at least 1 digit, 1 uppercase letter, 1 lowercase letter and 1 special character")
    @NotBlank(message = "password is required")
    @Schema(description = "Пароль", example = "creativePassword1!")
    private String password;
}
