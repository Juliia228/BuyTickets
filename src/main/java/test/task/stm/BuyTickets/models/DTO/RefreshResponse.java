package test.task.stm.BuyTickets.models.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Ответ после обновления access токена")
public class RefreshResponse {
    @Schema(description = "Access токен, действителен в течение 15 минут")
    @NotBlank(message = "access token is required")
    private String access_token;
    @Schema(description = "Refresh токен")
    @NotBlank(message = "refresh token is required")
    private String refresh_token;
}
