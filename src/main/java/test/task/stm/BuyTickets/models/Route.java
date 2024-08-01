package test.task.stm.BuyTickets.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность маршрута")
public class Route {
    @Schema(description = "Идентификатор")
    @Min(value = 1)
    private int id;
    @Schema(description = "Пункт отправления", example = "Нижний Новгород")
    @NotBlank(message = "departure point is required")
    private String departure_point;
    @Schema(description = "Пункт назначения", example = "Москва")
    @NotBlank(message = "destination point is required")
    private String destination_point;
    @Schema(description = "Название перевозчика")
    @NotBlank(message = "transporter name is required")
    private String transporter_name;
    @Schema(description = "Длительность поездка (в минутах)")
    @Min(value = 1, message = "trip cannot last less than 1 minute")
    private int minutes;
}
