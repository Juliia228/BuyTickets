package test.task.stm.BuyTickets.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sale {
    private int id;
    private int user_id;
    private int ticket_id;
    private Timestamp sold_at;

    public Sale(int user_id, int ticket_id, Timestamp sold_at) {
        this.user_id = user_id;
        this.ticket_id = ticket_id;
        this.sold_at = sold_at;
    }
}
