package test.task.stm.BuyTickets.repositories.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import test.task.stm.BuyTickets.models.Ticket;
import test.task.stm.BuyTickets.repositories.TicketRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;
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
        Ticket ticket = null;
        try {
            ticket = jdbcTemplate.queryForObject("select * from tickets where id = ?", ROW_MAPPER, id);
        } catch (DataAccessException dataAccessException) {
            log.info("Couldn't find entity of type Ticket with id {}", id);
        }
        return ticket;
    }

    @Override
    public List<Ticket> getAll() {
        return jdbcTemplate.query("select * from tickets", ROW_MAPPER);
    }

    @Override
    public Ticket save(Ticket ticket) {
        try {
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
                throw new RuntimeException();
            }
            return get((Integer) generatedKeyHolder.getKeys().get("id"));
        } catch (Exception e) {
            log.info("Билет " + ticket.getId() + " не добавлен");
        }
        return get(ticket.getId());
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
