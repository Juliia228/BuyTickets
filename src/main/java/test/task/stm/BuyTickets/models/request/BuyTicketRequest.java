package test.task.stm.BuyTickets.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Сущность для запроса на покупку билета")
public class BuyTicketRequest {
    @Schema(description = "Идентификатор пользователя, который покупает билет")
    @Min(value = 1)
    @NotNull
    private int user_id;
    @Schema(description = "Идентификатор билета, который покупает пользователь")
    @Min(value = 1)
    @NotNull
    private int ticket_id;
}
