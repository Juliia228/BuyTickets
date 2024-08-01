package test.task.stm.BuyTickets.models;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class SaleRequest {
    @Min(value = 1)
    private int user_id;
    @Min(value = 1)
    private int ticket_id;
    private Timestamp sold_at;
}
