package test.task.stm.BuyTickets.repositories;

import org.springframework.jdbc.core.RowMapper;
import test.task.stm.BuyTickets.models.Ticket;
import test.task.stm.BuyTickets.models.request.TicketRequest;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;

public interface TicketRepository {
    RowMapper<Ticket> ROW_MAPPER = (ResultSet resultSet, int rowNum) ->
            new Ticket(resultSet.getInt("id"),
                    resultSet.getInt("route_id"),
                    resultSet.getTimestamp("departure_at"),
                    resultSet.getInt("seat_number"),
                    resultSet.getLong("price"));

    Ticket get(int id);

    List<Ticket> getByUser(int user_id);

    List<Ticket> getByParams(Integer offset, Integer size, Timestamp from, Timestamp to,
                             String departure_point, String destination_point, String transporter_name);

    List<Ticket> getAllNotAvailable();

    List<Ticket> getAllAvailable();

    List<Ticket> getAllAvailable(Integer offset, Integer size);

    List<Ticket> getAll();

    Ticket save(TicketRequest ticket);

    Ticket update(Ticket ticket);

    int delete(int id);
}
