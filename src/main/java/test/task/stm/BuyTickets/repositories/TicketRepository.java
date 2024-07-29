package test.task.stm.BuyTickets.repositories;

import org.springframework.jdbc.core.RowMapper;
import test.task.stm.BuyTickets.models.Ticket;

import java.sql.ResultSet;
import java.util.List;

public interface TicketRepository {
    RowMapper<Ticket> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> new Ticket(resultSet.getInt("id"), resultSet.getInt("route_id"), resultSet.getTimestamp("departure_at"), resultSet.getInt("seat_number"), resultSet.getLong("price"));

    Ticket get(int id);

    List<Ticket> getAll();

    Ticket save(Ticket ticket);

    Ticket update(Ticket ticket);

    int delete(int id);
}
