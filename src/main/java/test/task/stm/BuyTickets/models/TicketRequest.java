package test.task.stm.BuyTickets.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class TicketRequest {
    @Min(value = 1)
    private int route_id;
    @NotNull(message = "departure date is required")
    private Timestamp departure_at;
    @Min(value = 1, message = "seat number cannot be less than 1")
    private int seat_number;
    private long price;
}
