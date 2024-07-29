package test.task.stm.BuyTickets.models;

import lombok.Data;

@Data
public class Route {
    private long id;
    private String from;
    private String to;
    private int transporter_name;
    private int minutes;
}
