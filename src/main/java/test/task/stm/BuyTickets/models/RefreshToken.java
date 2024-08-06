package test.task.stm.BuyTickets.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@Schema(description = "Сущность refresh токена")
public class RefreshToken {
    @Schema(description = "Идентификатор")
    @Min(value = 1)
    @NotNull
    private int id;
    @Schema(description = "Refresh токен")
    @NotBlank(message = "token is required")
    private String token;
    @Schema(description = "Срок истечения токена")
    @NotNull(message = "expiration time is required")
    private ZonedDateTime expiration;
    @Schema(description = "Идентификатор пользователя")
    @Min(value = 1)
    @NotNull
    private int user_id;
}