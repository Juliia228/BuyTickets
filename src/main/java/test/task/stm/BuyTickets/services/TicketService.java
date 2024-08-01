package test.task.stm.BuyTickets.services;

import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import test.task.stm.BuyTickets.exception.DataNotFoundException;
import test.task.stm.BuyTickets.models.Ticket;
import test.task.stm.BuyTickets.models.TicketRequest;
import test.task.stm.BuyTickets.repositories.TicketRepository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket find(int id) {
        return ticketRepository.get(id);
    }

    public List<Ticket> find(Integer offset, Integer size, Timestamp from, Timestamp to) {
        return (offset != null && size != null)
                ? ticketRepository.getByDateTime(offset, size, from, to)
                : ticketRepository.getByDateTime(from, to);
    }

    public List<Ticket> find(Integer offset, Integer size, Date from, Date to) {
        return (offset != null && size != null)
                ? ticketRepository.getByDate(offset, size, from, to)
                : ticketRepository.getByDate(from, to);
    }

    public List<Ticket> find(Integer offset, Integer size, Date day) {
        return (offset != null && size != null)
                ? ticketRepository.getByDay(offset, size, day)
                : ticketRepository.getByDay(day);
    }

    public List<Ticket> find(Integer offset, Integer size, String departure_point, String destination_point) {
        return (offset != null && size != null)
                ? ticketRepository.getByRoutePoints(offset, size, departure_point, destination_point)
                : ticketRepository.getByRoutePoints(departure_point, destination_point);
    }

    public List<Ticket> findByDeparturePoint(Integer offset, Integer size, String departure_point) {
        return (offset != null && size != null)
                ? ticketRepository.getByDeparturePoint(offset, size, departure_point)
                : ticketRepository.getByDeparturePoint(departure_point);
    }

    public List<Ticket> findByDestinationPoint(Integer offset, Integer size, String destination_point) {
        return (offset != null && size != null)
                ? ticketRepository.getByDestinationPoint(offset, size, destination_point)
                : ticketRepository.getByDestinationPoint(destination_point);
    }

    public List<Ticket> findByTransporter(Integer offset, Integer size, String transporter_name) {
        return (offset != null && size != null)
                ? ticketRepository.getByTransporter(offset, size, transporter_name)
                : ticketRepository.getByTransporter(transporter_name);
    }

    public List<Ticket> findAllNotAvailable() {
        return ticketRepository.getAllNotAvailable();
    }

    public List<Ticket> findAllAvailable(Integer offset, Integer size) {
        return (offset != null && size != null)
                ? ticketRepository.getAllAvailable(offset, size)
                : ticketRepository.getAllAvailable();
    }

    public List<Ticket> findAll() {
        List<Ticket> tickets = ticketRepository.getAll();
        if (tickets.isEmpty()) {
            throw new DataNotFoundException("0 objects was found");
        }
        return tickets;
    }

    public Ticket add(TicketRequest ticket) {
        return ticketRepository.save(ticket);
    }

    public Ticket edit(Ticket new_ticket) {
        return ticketRepository.update(new_ticket);
    }

    public void delete(int id) {
        if (ticketRepository.delete(id) == 0) {
            throw new DataNotFoundException("No data was found for deletion");
        };
    }
}
