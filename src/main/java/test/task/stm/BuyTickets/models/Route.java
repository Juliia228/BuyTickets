package test.task.stm.BuyTickets.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Route {
    private int id;
    @NotBlank
    private String departure_point;
    @NotBlank
    private String destination_point;
    @NotBlank
    private String transporter_name;
    @Min(value = 1)
    private int minutes;

    public Route(String departure_point, String destination_point, String transporter_name, int minutes) {
        this.departure_point = departure_point;
        this.destination_point = destination_point;
        this.transporter_name = transporter_name;
        this.minutes = minutes;
    }
}
