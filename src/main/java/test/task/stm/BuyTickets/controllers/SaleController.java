package test.task.stm.BuyTickets.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import test.task.stm.BuyTickets.models.request.BuyTicketRequest;
import test.task.stm.BuyTickets.models.Sale;
import test.task.stm.BuyTickets.models.request.SaleRequest;
import test.task.stm.BuyTickets.services.SaleService;

import java.util.List;

@Validated
@RestController
public class SaleController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping("/sale/getById")
    public ResponseEntity<Sale> getSale(@RequestParam @Min(1) int id) {
        return ResponseEntity.ok(saleService.find(id));
    }

    @GetMapping("/sale/getAll")
    public ResponseEntity<List<Sale>> getAllSales() {
        return ResponseEntity.ok(saleService.findAll());
    }

    @PostMapping("/buyTicket")
    public ResponseEntity<Sale> buyTicket(@Valid @RequestBody BuyTicketRequest request) throws BadRequestException {
        return ResponseEntity.ok(saleService.createSale(request));
    }

    @PostMapping("/sale/add")
    public ResponseEntity<Sale> newSale(@Valid @RequestBody SaleRequest new_sale) {
        return ResponseEntity.ok(saleService.add(new_sale));
    }

    @PutMapping("/sale/edit")
    public ResponseEntity<Sale> updateSale(@Valid @RequestBody Sale new_sale) {
        return ResponseEntity.ok(saleService.edit(new_sale));
    }

    @DeleteMapping("/sale/delete")
    public ResponseEntity<String> deleteSale(@RequestParam @Min(1) int id) {
        saleService.delete(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}
