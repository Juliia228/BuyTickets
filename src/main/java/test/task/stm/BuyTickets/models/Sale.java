package test.task.stm.BuyTickets.models;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class Sale {
    private long id;
    private long user_id;
    private long ticket_id;
    private Timestamp sold_at;
}
