package test.task.stm.BuyTickets.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность осуществленных покупок")
public class Purchase {
    @Schema(description = "Идентификатор")
    @Min(value = 1)
    @NotNull
    private int id;
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
    private OffsetDateTime sold_at;
}
