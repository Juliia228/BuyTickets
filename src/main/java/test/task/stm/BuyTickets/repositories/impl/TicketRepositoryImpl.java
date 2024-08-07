package test.task.stm.BuyTickets.repositories.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import test.task.stm.BuyTickets.models.Ticket;
import test.task.stm.BuyTickets.models.DTO.TicketRequest;
import test.task.stm.BuyTickets.repositories.TicketRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
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
    public List<Ticket> getByUser(int user_id) {
        return jdbcTemplate.query("select tickets.* from tickets " +
                        "join purchases " +
                        "on purchases.ticket_id = tickets.id " +
                        "join users " +
                        "on users.id = purchases.user_id " +
                        "and users.id = ?",
                ROW_MAPPER, user_id);
    }

    @Override
    public List<Ticket> getByParams(Integer offset, Integer size, Timestamp from, Timestamp to,
                                    String departure_point, String destination_point, String transporter_name) {
        return jdbcTemplate.query("SELECT tickets.* FROM tickets " +
                        "JOIN routes ON routes.id = tickets.route_id " +
                        ((transporter_name != null) ? "AND routes.transporter_name LIKE '%" + transporter_name + "%' " : "") +
                        ((departure_point != null) ? "AND routes.departure_point LIKE '%" + departure_point + "%' " : "") +
                        ((destination_point != null) ? "AND routes.destination_point LIKE '%" + destination_point + "%' " : "") +
                        ((from != null && to != null)
                                ? "WHERE departure_at BETWEEN '" + from + "' AND '" + to + "' "
                                : ((from != null)
                                ? "WHERE departure_at >= '" + from + "' "
                                : (to != null)
                                ? "WHERE departure_at <= '" + to + "' "
                                : "")) +
                        "EXCEPT SELECT tickets.* FROM tickets " +
                        "JOIN purchases " +
                        "ON purchases.ticket_id = tickets.id " +
                        ((offset != null && size != null) ? "LIMIT " + size + " OFFSET " + offset : ""),
                ROW_MAPPER);
    }

    @Override
    public List<Ticket> getAllNotAvailable() {
        return jdbcTemplate.query("select tickets.* from tickets " +
                        "intersect select tickets.* from tickets " +
                        "join purchases " +
                        "on purchases.ticket_id = tickets.id",
                ROW_MAPPER);
    }

    @Override
    public List<Ticket> getAll() {
        return jdbcTemplate.query("select * from tickets", ROW_MAPPER);
    }

    @Override
    public Ticket save(TicketRequest ticket) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("insert into tickets (route_id, departure_at, seat_number, price) values (?, ?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS);
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
        jdbcTemplate.update("update tickets set route_id = ?, departure_at = ?, seat_number = ?, price = ? where id = ?",
                ticket.getRoute_id(), ticket.getDeparture_at(), ticket.getSeat_number(), ticket.getPrice(), ticket.getId());
        return get(ticket.getId());
    }

    @Override
    public int delete(int id) {
        return jdbcTemplate.update("delete from tickets " +
                "where id = ?", id);
    }
}
