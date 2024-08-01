package test.task.stm.BuyTickets.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@Schema(description = "Сущность осуществленной продажи билета для запроса")
public class SaleRequest {
    @Schema(description = "Идентификатор пользователя, который купил билет")
    @Min(value = 1)
    private int user_id;
    @Schema(description = "Идентификатор купленного билета")
    @Min(value = 1)
    private int ticket_id;
    @Schema(description = "Дата и время продажи")
    private Timestamp sold_at;
}
