package test.task.stm.BuyTickets.models.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@Schema(description = "Сущность осуществленной покупки билета для запроса")
public class PurchaseRequest {
    @Schema(description = "Идентификатор пользователя, который купил билет")
    @Min(value = 1)
    @NotNull
    private int user_id;
    @Schema(description = "Идентификатор купленного билета")
    @Min(value = 1)
    @NotNull
    private int ticket_id;
    @Schema(description = "Дата и время покупки")
    @NotNull
    private ZonedDateTime sold_at;
}
