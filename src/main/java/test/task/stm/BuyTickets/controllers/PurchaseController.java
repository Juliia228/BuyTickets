package test.task.stm.BuyTickets.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import test.task.stm.BuyTickets.models.Purchase;
import test.task.stm.BuyTickets.models.request.BuyTicketRequest;
import test.task.stm.BuyTickets.models.request.PurchaseRequest;
import test.task.stm.BuyTickets.services.PurchaseService;

import java.util.List;

@Validated
@RestController
@Tag(name="Покупка билетов", description="Управляет сущностями осуществленных покупок билетов")
public class PurchaseController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @Operation(
            summary = "Получение данных о покупке",
            description = "Позволяет найти информацию о покупке билета"
    )
    @GetMapping("/purchase/getById")
    public ResponseEntity<Purchase> getPurchase(@RequestParam @Min(1) @Parameter(description = "Идентификатор покупки") int purchase_id) {
        return ResponseEntity.ok(purchaseService.find(purchase_id));
    }

    @Operation(
            summary = "Получение всех осуществленных покупок билетов"
    )
    @GetMapping("/purchase/getAll")
    public ResponseEntity<List<Purchase>> getAllPurchases() {
        return ResponseEntity.ok(purchaseService.findAll());
    }

    @Operation(
            summary = "Осуществление покупки билета",
            description = ""
    )
    @PostMapping("/buyTicket")
    public ResponseEntity<Purchase> buyTicket(@Valid @RequestBody @Parameter(description = "id пользователя, купившего билет и id купленного билета") BuyTicketRequest request) throws BadRequestException {
        return ResponseEntity.ok(purchaseService.createPurchase(request));
    }

    @Operation(
            summary = "Добавление информации о покупке",
            description = "Позволяет добавить новую покупку билета"
    )
    @PostMapping("/purchase/add")
    public ResponseEntity<Purchase> newPurchase(@Valid @RequestBody @Parameter(description = "Данные о покупке") PurchaseRequest new_purchase) {
        return ResponseEntity.ok(purchaseService.add(new_purchase));
    }

    @Operation(
            summary = "Обновление данных о покупке билета",
            description = "Позволяет обновить данные о покупке"
    )
    @PutMapping("/purchase/edit")
    public ResponseEntity<Purchase> updatePurchase(@Valid @RequestBody @Parameter(description = "Новые данные о покупке") Purchase new_purchase) {
        return ResponseEntity.ok(purchaseService.edit(new_purchase));
    }

    @Operation(
            summary = "Удаление информации об осуществленной покупке билета",
            description = "Позволяет удалить информацию о покупке по ее id"
    )
    @DeleteMapping("/purchase/delete")
    public ResponseEntity<String> deletePurchase(@RequestParam @Min(1) @Parameter(description = "Идентификатор покупке") int purchase_id) {
        purchaseService.delete(purchase_id);
        return ResponseEntity.ok("Deleted successfully");
    }
}
