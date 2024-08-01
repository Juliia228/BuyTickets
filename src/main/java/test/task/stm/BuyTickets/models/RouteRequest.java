package test.task.stm.BuyTickets.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RouteRequest {
    @NotBlank(message = "departure point is required")
    private String departure_point;
    @NotBlank(message = "destination point is required")
    private String destination_point;
    @NotBlank(message = "transporter name is required")
    private String transporter_name;
    @Min(value = 1, message = "trip cannot last less than 1 minute")
    private int minutes;
}
