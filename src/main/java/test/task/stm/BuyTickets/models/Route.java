package test.task.stm.BuyTickets.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Route {
    private int id;
    private String from;
    private String to;
    private String transporter_name;
    private int minutes;
}
