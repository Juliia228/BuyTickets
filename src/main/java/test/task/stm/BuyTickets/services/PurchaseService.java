package test.task.stm.BuyTickets.services;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import test.task.stm.BuyTickets.exception.DataNotFoundException;
import test.task.stm.BuyTickets.models.Purchase;
import test.task.stm.BuyTickets.models.request.BuyTicketRequest;
import test.task.stm.BuyTickets.models.request.PurchaseRequest;
import test.task.stm.BuyTickets.repositories.PurchaseRepository;
import test.task.stm.BuyTickets.repositories.TicketRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;

    private final TicketRepository ticketRepository;

    PurchaseService(PurchaseRepository purchaseRepository, TicketRepository ticketRepository) {
        this.purchaseRepository = purchaseRepository;
        this.ticketRepository = ticketRepository;
    }

    public Purchase find(int id) {
        return purchaseRepository.get(id);
    }

    public List<Purchase> findAll() {
        List<Purchase> purchases = purchaseRepository.getAll();
        if (purchases.isEmpty()) {
            throw new DataNotFoundException("0 objects was found");
        }
        return purchases;
    }

    public Purchase createPurchase(BuyTicketRequest request) throws BadRequestException {
        if (isTicketSold(request.getTicket_id())) {
            throw new BadRequestException("Ticket with id=" + request.getTicket_id() + " is sold");
        }
        return purchaseRepository.save(new PurchaseRequest(request.getUser_id(), request.getTicket_id(), Timestamp.valueOf(LocalDateTime.now(ZoneId.of("ECT")))));
    }

    public boolean isTicketSold(int ticket_id) {
        return purchaseRepository.getAll().stream().anyMatch(purchase -> purchase.getTicket_id() == ticket_id);
    }

    public Purchase add(PurchaseRequest purchase) {
        return purchaseRepository.save(purchase);
    }

    public Purchase edit(Purchase new_purchase) {
        return purchaseRepository.update(new_purchase);
    }

    public void delete(int id) {
        if (purchaseRepository.delete(id) == 0) {
            throw new DataNotFoundException("No data was found for deletion");
        };
    }
}
