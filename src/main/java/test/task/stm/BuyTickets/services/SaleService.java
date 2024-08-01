package test.task.stm.BuyTickets.services;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import test.task.stm.BuyTickets.exception.DataNotFoundException;
import test.task.stm.BuyTickets.models.request.BuyTicketRequest;
import test.task.stm.BuyTickets.models.request.SaleRequest;
import test.task.stm.BuyTickets.models.Ticket;
import test.task.stm.BuyTickets.models.Sale;
import test.task.stm.BuyTickets.repositories.SaleRepository;
import test.task.stm.BuyTickets.repositories.TicketRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class SaleService {
    private final SaleRepository saleRepository;

    private final TicketRepository ticketRepository;

    SaleService(SaleRepository saleRepository, TicketRepository ticketRepository) {
        this.saleRepository = saleRepository;
        this.ticketRepository = ticketRepository;
    }

    public Sale find(int id) {
        return saleRepository.get(id);
    }

    public List<Sale> findAll() {
        List<Sale> sales = saleRepository.getAll();
        if (sales.isEmpty()) {
            throw new DataNotFoundException("0 objects was found");
        }
        return sales;
    }

    public List<Ticket> getTicketsByUser(int user_id) {
//        List<Sale> sales = saleRepository.getAll()
//                .stream()
//                .filter(sale -> sale.getUser_id() == user_id)
//                .toList();
//        return sales
//                .stream()
//                .map(Sale::getTicket_id).map(ticket_id -> ticketRepository.get(ticket_id)).toList();
        return saleRepository.getAll().stream()
                .filter(sale -> sale.getUser_id() == user_id)
                .map(Sale::getTicket_id)
                .map(ticketRepository::get)
                .toList();
    }

    public Sale createSale(BuyTicketRequest request) throws BadRequestException {
        if (isTicketSold(request.getTicket_id())) {
            throw new BadRequestException("Ticket with id=" + request.getTicket_id() + " is sold");
        }
        return saleRepository.save(new SaleRequest(request.getUser_id(), request.getTicket_id(), Timestamp.valueOf(LocalDateTime.now(ZoneId.of("ECT")))));
    }

    public boolean isTicketSold(int ticket_id) {
        return saleRepository.getAll().stream().anyMatch(sale -> sale.getTicket_id() == ticket_id);
    }

    public Sale add(SaleRequest sale) {
        return saleRepository.save(sale);
    }

    public Sale edit(Sale new_sale) {
        return saleRepository.update(new_sale);
    }

    public void delete(int id) {
        if (saleRepository.delete(id) == 0) {
            throw new DataNotFoundException("No data was found for deletion");
        };
    }
}
