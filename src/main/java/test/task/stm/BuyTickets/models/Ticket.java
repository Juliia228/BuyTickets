package test.task.stm.BuyTickets.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    private int id;
    private int route_id;
    private Timestamp departure_at;
    private int seat_number;
    private long price;

    public Ticket(int route_id, Timestamp departure_at, int seat_number, long price) {
        this.route_id = route_id;
        this.departure_at = departure_at;
        this.seat_number = seat_number;
        this.price = price;
    }
}
