package test.task.stm.BuyTickets.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность билета")
public class Ticket {
    @Schema(description = "Идентификатор")
    @Min(value = 1)
    @NotNull
    private int id;
    @Schema(description = "Идентификатор маршрута, на который предоставляет доступ данный билет")
    @Min(value = 1)
    @NotNull
    private int route_id;
    @Schema(description = "Дата и время отправления")
    @NotNull(message = "departure date is required")
    private Timestamp departure_at;
    @Schema(description = "Номер места")
    @Min(value = 1, message = "seat number cannot be less than 1")
    @NotNull
    private int seat_number;
    @Schema(description = "Цена")
    @NotNull
    private long price;
}
