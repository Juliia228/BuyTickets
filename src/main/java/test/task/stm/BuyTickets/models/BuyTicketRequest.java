package test.task.stm.BuyTickets.models;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class BuyTicketRequest {
    @Min(value = 1)
    private int user_id;
    @Min(value = 1)
    private int ticket_id;
}
