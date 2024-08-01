package test.task.stm.BuyTickets.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Min(value = 1)
    private int id;
    @Min(value = 1)
    private int route_id;
    @NotNull(message = "departure date is required")
    private Timestamp departure_at;
    @Min(value = 1, message = "seat number cannot be less than 1")
    private int seat_number;
    private long price;
}
