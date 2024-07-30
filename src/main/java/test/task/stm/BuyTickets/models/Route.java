package test.task.stm.BuyTickets.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Route {
    private int id;
    private String from;
    private String to;
    private String transporter_name;
    private int minutes;

    public Route(String from, String to, String transporter_name, int minutes) {
        this.from = from;
        this.to = to;
        this.transporter_name = transporter_name;
        this.minutes = minutes;
    }
}
