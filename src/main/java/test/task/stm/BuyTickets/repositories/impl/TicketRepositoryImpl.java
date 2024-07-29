package test.task.stm.BuyTickets.repositories.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import test.task.stm.BuyTickets.models.Ticket;
import test.task.stm.BuyTickets.repositories.TicketRepository;

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
            ticket = jdbcTemplate.queryForObject("select * from tickets where id = ?", new Object[]{id}, ROW_MAPPER);
        } catch (DataAccessException dataAccessException) {
            log.debug("Couldn't find entity of type Ticket with id {}", id);
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
            assert jdbcTemplate.update("insert into tickets values (?, ?, ?, ?, ?)", ticket.getId(), ticket.getRoute_id(), ticket.getDeparture_at(), ticket.getSeat_number(), ticket.getPrice()) > 0;
        } catch (AssertionError assertionError) {
            log.debug("Билет " + ticket.getId() + " не добавлен");
        }
        return get(ticket.getId());
    }

    @Override
    public Ticket update(Ticket ticket) {
        try {
            assert jdbcTemplate.update("update tickets set route_id = ?, departure_at = ?, seat_number = ?, price = ? where id = ?", ticket.getRoute_id(), ticket.getDeparture_at(), ticket.getSeat_number(), ticket.getPrice(), ticket.getId()) > 0;
        } catch (AssertionError assertionError) {
            log.debug("Данные о билете " + ticket.getId() + " не обновлены");
        }
        return get(ticket.getId());
    }

    @Override
    public int delete(int id) {
        return jdbcTemplate.update("delete from tickets where id = ?", id);
    }
}
