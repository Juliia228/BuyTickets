package test.task.stm.BuyTickets.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
@Schema(description = "Сущность для запроса на покупку билета")
public class BuyTicketRequest {
    @Schema(description = "Идентификатор пользователя, который покупает билет")
    @Min(value = 1)
    private int user_id;
    @Schema(description = "Идентификатор билета, который покупает пользователь")
    @Min(value = 1)
    private int ticket_id;
}
