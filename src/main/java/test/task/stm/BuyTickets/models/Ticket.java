package test.task.stm.BuyTickets.models;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class Ticket {
    private long id;
    private long route_id;
    private Timestamp departure_at;
    private int seat_number;
    private long price;
}
