package test.task.stm.BuyTickets.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class Ticket {
    private int id;
    private int route_id;
    private Timestamp departure_at;
    private int seat_number;
    private long price;
}
