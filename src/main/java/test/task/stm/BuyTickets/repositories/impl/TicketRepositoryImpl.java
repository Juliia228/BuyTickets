package test.task.stm.BuyTickets.repositories.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import test.task.stm.BuyTickets.models.Ticket;
import test.task.stm.BuyTickets.repositories.TicketRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public class TicketRepositoryImpl implements TicketRepository {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final JdbcTemplate jdbcTemplate;

    public TicketRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Ticket get(int id) {
        return jdbcTemplate.queryForObject("select * from tickets " +
                "where id = ?",
                ROW_MAPPER, id);
    }

    @Override
    public List<Ticket> getByDateTime(Timestamp from, Timestamp to) {
        return jdbcTemplate.query("select * from tickets " +
                "where departure_at between ? and ? " +
                "except select tickets.* from tickets " +
                "join sales " +
                "on sales.ticket_id = tickets.id",
                ROW_MAPPER, from, to);
    }

    @Override
    public List<Ticket> getByDateTime(Integer offset, Integer size, Timestamp from, Timestamp to) {
        return jdbcTemplate.query("select * from tickets " +
                        "where departure_at between ? and ? " +
                        "except select tickets.* from tickets " +
                        "join sales " +
                        "on sales.ticket_id = tickets.id " +
                        "limit ? offset ?",
                ROW_MAPPER, from, to, size, offset);
    }

    @Override
    public List<Ticket> getByDate(Date from, Date to) {
        return null;
//        return jdbcTemplate.query("select * from tickets where departure_at between ? and ?", ROW_MAPPER, from, to);
    }

    @Override
    public List<Ticket> getByDate(Integer offset, Integer size, Date from, Date to) {
        return null;
//        return jdbcTemplate.query("select * from tickets where departure_at between ? and ?", ROW_MAPPER, from, to);
    }

    @Override
    public List<Ticket> getByDay(Date day) {
        return null;
//        return jdbcTemplate.query("select * from tickets where departure_at = ?", ROW_MAPPER, from, to);
    }

    @Override
    public List<Ticket> getByDay(Integer offset, Integer size, Date day) {
        return null;
//        return jdbcTemplate.query("select * from tickets where departure_at = ?", ROW_MAPPER, from, to);
    }

    @Override
    public List<Ticket> getByRoutePoints(String departure_point, String destination_point) {
        return jdbcTemplate.query("select tickets.* from tickets " +
                "join routes " +
                "on routes.id = tickets.route_id " +
                "and routes.departure_point = ? " +
                "and routes.destination_point = ? " +
                "except select tickets.* from tickets " +
                "join sales " +
                "on sales.ticket_id = tickets.id",
                ROW_MAPPER, departure_point, destination_point);
    }

    @Override
    public List<Ticket> getByRoutePoints(Integer offset, Integer size, String departure_point, String destination_point) {
        return jdbcTemplate.query("select tickets.* from tickets " +
                        "join routes " +
                        "on routes.id = tickets.route_id " +
                        "and routes.departure_point = ? " +
                        "and routes.destination_point = ? " +
                        "except select tickets.* from tickets " +
                        "join sales " +
                        "on sales.ticket_id = tickets.id " +
                        "limit ? offset ?",
                ROW_MAPPER, departure_point, destination_point, size, offset);
    }

    @Override
    public List<Ticket> getByDeparturePoint(String departure_point) {
        return jdbcTemplate.query("select tickets.* from tickets " +
                        "join routes " +
                        "on routes.id = tickets.route_id " +
                        "and routes.departure_point = ? " +
                        "except select tickets.* from tickets " +
                        "join sales " +
                        "on sales.ticket_id = tickets.id",
                ROW_MAPPER, departure_point);
    }

    @Override
    public List<Ticket> getByDeparturePoint(Integer offset, Integer size, String departure_point) {
        return jdbcTemplate.query("select tickets.* from tickets " +
                        "join routes " +
                        "on routes.id = tickets.route_id " +
                        "and routes.departure_point = ? " +
                        "except select tickets.* from tickets " +
                        "join sales " +
                        "on sales.ticket_id = tickets.id " +
                        "limit ? offset ?",
                ROW_MAPPER, departure_point, size, offset);
    }

    @Override
    public List<Ticket> getByDestinationPoint(String destination_point) {
        return jdbcTemplate.query("select tickets.* from tickets " +
                        "join routes " +
                        "on routes.id = tickets.route_id " +
                        "and routes.destination_point = ? " +
                        "except select tickets.* from tickets " +
                        "join sales " +
                        "on sales.ticket_id = tickets.id",
                ROW_MAPPER, destination_point);
    }

    @Override
    public List<Ticket> getByDestinationPoint(Integer offset, Integer size, String destination_point) {
        return jdbcTemplate.query("select tickets.* from tickets " +
                        "join routes " +
                        "on routes.id = tickets.route_id " +
                        "and routes.destination_point = ? " +
                        "except select tickets.* from tickets " +
                        "join sales " +
                        "on sales.ticket_id = tickets.id " +
                        "limit ? offset ?",
                ROW_MAPPER, destination_point, size, offset);
    }

    @Override
    public List<Ticket> getByTransporter(String transporter_name) {
        return jdbcTemplate.query("select tickets.* from tickets " +
                        "join routes " +
                        "on routes.id = tickets.route_id " +
                        "and routes.transporter_name = ? " +
                        "except select tickets.* from tickets " +
                        "join sales " +
                        "on sales.ticket_id = tickets.id",
                ROW_MAPPER, transporter_name);
    }

    @Override
    public List<Ticket> getByTransporter(Integer offset, Integer size, String transporter_name) {
        return jdbcTemplate.query("select tickets.* from tickets " +
                        "join routes " +
                        "on routes.id = tickets.route_id " +
                        "and routes.transporter_name = ? " +
                        "except select tickets.* from tickets " +
                        "join sales " +
                        "on sales.ticket_id = tickets.id " +
                        "limit ? offset ?",
                ROW_MAPPER, transporter_name, size, offset);
    }

    @Override
    public List<Ticket> getAllNotAvailable() {
        return jdbcTemplate.query("select tickets.* from tickets " +
                        "intersect select tickets.* from tickets " +
                        "join sales " +
                        "on sales.ticket_id = tickets.id",
                ROW_MAPPER);
    }

    @Override
    public List<Ticket> getAllAvailable() {
        return jdbcTemplate.query("select * from tickets " +
                        "except select tickets.* from tickets " +
                        "join sales " +
                        "on sales.ticket_id = tickets.id",
                ROW_MAPPER);
    }

    @Override
    public List<Ticket> getAllAvailable(Integer offset, Integer size) {
        return jdbcTemplate.query("select * from tickets " +
                        "except select tickets.* from tickets " +
                        "join sales " +
                        "on sales.ticket_id = tickets.id " +
                        "limit ? offset ?",
                ROW_MAPPER, size, offset);
    }

    @Override
    public List<Ticket> getAll() {
        return jdbcTemplate.query("select * from tickets", ROW_MAPPER);
    }

    @Override
    public Ticket save(Ticket ticket) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("insert into tickets (route_id, departure_at, seat_number, price) values (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, ticket.getRoute_id());
            ps.setTimestamp(2, ticket.getDeparture_at());
            ps.setInt(3, ticket.getSeat_number());
            ps.setLong(4, ticket.getPrice());
            return ps;
        }, generatedKeyHolder);
        if (generatedKeyHolder.getKeys() == null) {
            throw new InvalidDataAccessApiUsageException("Ticket object cannot be saved");
        }
        return get((Integer) generatedKeyHolder.getKeys().get("id"));
    }

    @Override
    public Ticket update(Ticket ticket) {
        try {
            jdbcTemplate.update("update tickets set route_id = ?, departure_at = ?, seat_number = ?, price = ? where id = ?", ticket.getRoute_id(), ticket.getDeparture_at(), ticket.getSeat_number(), ticket.getPrice(), ticket.getId());
        } catch (Exception e) {
            log.debug("Данные о билете " + ticket.getId() + " не обновлены");
        }
        return get(ticket.getId());
    }

    @Override
    public int delete(int id) {
        return jdbcTemplate.update("delete from tickets where id = ?", id);
    }
}
