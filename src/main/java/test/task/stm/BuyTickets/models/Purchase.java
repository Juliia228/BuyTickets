package test.task.stm.BuyTickets.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность осуществленных покупок")
public class Purchase {
    @Schema(description = "Идентификатор")
    @Min(value = 1)
    private int id;
    @Schema(description = "Идентификатор пользователя, который купил билет")
    @Min(value = 1)
    private int user_id;
    @Schema(description = "Идентификатор купленного билета")
    @Min(value = 1)
    private int ticket_id;
    @Schema(description = "Дата и время покупки")
    private Timestamp sold_at;
}
