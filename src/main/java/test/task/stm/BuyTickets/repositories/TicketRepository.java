package test.task.stm.BuyTickets.repositories;

import org.springframework.jdbc.core.RowMapper;
import test.task.stm.BuyTickets.models.Ticket;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface TicketRepository {
    RowMapper<Ticket> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> new Ticket(resultSet.getInt("id"), resultSet.getInt("route_id"), resultSet.getTimestamp("departure_at"), resultSet.getInt("seat_number"), resultSet.getLong("price"));

    Ticket get(int id);

    List<Ticket> getByDateTime(Timestamp from, Timestamp to);

    List<Ticket> getByDateTime(Integer offset, Integer size, Timestamp from, Timestamp to);

    List<Ticket> getByDate(Date from, Date to);

    List<Ticket> getByDate(Integer offset, Integer size, Date from, Date to);

    List<Ticket> getByDay(Date day);

    List<Ticket> getByDay(Integer offset, Integer size, Date day);

    List<Ticket> getByRoutePoints(String departurePoint, String destinationPoint);

    List<Ticket> getByRoutePoints(Integer offset, Integer size, String departurePoint, String destinationPoint);

    List<Ticket> getByDeparturePoint(String departurePoint);

    List<Ticket> getByDeparturePoint(Integer offset, Integer size, String departurePoint);

    List<Ticket> getByDestinationPoint(String destinationPoint);

    List<Ticket> getByDestinationPoint(Integer offset, Integer size, String destinationPoint);

    List<Ticket> getByTransporter(String transporterName);

    List<Ticket> getByTransporter(Integer offset, Integer size, String transporterName);

    List<Ticket> getAllNotAvailable();

    List<Ticket> getAllAvailable();

    List<Ticket> getAllAvailable(Integer offset, Integer size);

    List<Ticket> getAll();

    Ticket save(Ticket ticket);

    Ticket update(Ticket ticket);

    int delete(int id);
}
