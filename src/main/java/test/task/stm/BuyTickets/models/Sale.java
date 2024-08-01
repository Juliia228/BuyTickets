package test.task.stm.BuyTickets.models;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sale {
    @Min(value = 1)
    private int id;
    @Min(value = 1)
    private int user_id;
    @Min(value = 1)
    private int ticket_id;
    private Timestamp sold_at;
}
