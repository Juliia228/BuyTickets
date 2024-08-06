package test.task.stm.BuyTickets.services;

import org.springframework.stereotype.Service;
import test.task.stm.BuyTickets.exception.DataNotFoundException;
import test.task.stm.BuyTickets.models.Ticket;
import test.task.stm.BuyTickets.models.DTO.TicketRequest;
import test.task.stm.BuyTickets.repositories.TicketRepository;

import java.sql.Timestamp;
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

    public List<Ticket> findByUser(int user_id) {
        return ticketRepository.getByUser(user_id);
    }

    public List<Ticket> find(Integer offset, Integer size, Timestamp from, Timestamp to,
                             String departure_point, String destination_point, String transporter_name) {
        return ticketRepository.getByParams(offset, size, from, to, departure_point, destination_point, transporter_name);
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
        return ticketRepository.getAll();
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
        }
        ;
    }
}
