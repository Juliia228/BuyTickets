package test.task.stm.BuyTickets.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Route {
    private int id;
    private String departure_point;
    private String destination_point;
    private String transporter_name;
    private int minutes;

    public Route(String departure_point, String destination_point, String transporter_name, int minutes) {
        this.departure_point = departure_point;
        this.destination_point = destination_point;
        this.transporter_name = transporter_name;
        this.minutes = minutes;
    }
}
