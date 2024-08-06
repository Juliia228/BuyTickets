package test.task.stm.BuyTickets.models.DTO;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import test.task.stm.BuyTickets.models.User;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@Schema(description = "Ответ после авторизации")
public class AuthResponse {
    @Schema(description = "Авторизованный пользователь")
    private User user;
    @Schema(description = "Access токен, действителен в течение 15 минут")
    @NotBlank(message = "access token is required")
    private String access_token;
    @Schema(description = "Refresh токен, действителен в течение часа")
    @NotBlank(message = "refresh token is required")
    private String refresh_token;
    @Schema(description = "Срок истечения refresh токена")
    @NotBlank(message = "expiration time is required")
    private ZonedDateTime expiration;
    @Schema(description = "Тип аутентификации")
    private final String token_type = "Bearer";
}
