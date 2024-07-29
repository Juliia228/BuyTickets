package test.task.stm.BuyTickets.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class Sale {
    private int id;
    private int user_id;
    private int ticket_id;
    private Timestamp sold_at;
}
