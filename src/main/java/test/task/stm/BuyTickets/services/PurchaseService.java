package test.task.stm.BuyTickets.services;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import test.task.stm.BuyTickets.exception.DataNotFoundException;
import test.task.stm.BuyTickets.models.Purchase;
import test.task.stm.BuyTickets.models.DTO.PurchaseRequest;
import test.task.stm.BuyTickets.repositories.PurchaseRepository;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final UserService userService;

    PurchaseService(PurchaseRepository purchaseRepository, UserService userService) {
        this.purchaseRepository = purchaseRepository;
        this.userService = userService;
    }

    public Purchase find(int id) {
        return purchaseRepository.get(id);
    }

    public List<Purchase> findAll() {
        return purchaseRepository.getAll();
    }

    public Purchase createPurchase(int ticket_id) throws BadRequestException {
        if (isTicketSold(ticket_id)) {
            throw new BadRequestException("Ticket with id=" + ticket_id + " is sold");
        }
        PurchaseRequest purchase = new PurchaseRequest(userService.getCurrentUser().getId(), ticket_id, ZonedDateTime.now());
        return purchaseRepository.save(purchase);
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
        }
    }
}
