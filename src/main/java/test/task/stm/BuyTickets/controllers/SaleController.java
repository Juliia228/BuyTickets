package test.task.stm.BuyTickets.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.task.stm.BuyTickets.models.Sale;
import test.task.stm.BuyTickets.services.SaleService;

import java.util.List;

@RestController("/sale")
public class SaleController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    private final SaleService saleService;

    @GetMapping("/getById")
    public ResponseEntity<Sale> getSale(@RequestParam int id) {
        return ResponseEntity.ok(saleService.find(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Sale>> getAllSales() {
        return ResponseEntity.ok(saleService.findAll());
    }

    @PostMapping("/add")
    public ResponseEntity<Sale> newSale(@RequestBody Sale new_sale) {
        // id необязательный параметр
        return ResponseEntity.ok(saleService.add(new_sale));
    }

    @PutMapping("/edit")
    public ResponseEntity<Sale> updateSale(@RequestBody Sale new_sale) {
        // id обязательный параметр
        return ResponseEntity.ok(saleService.edit(new_sale));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Sale> deleteSale(@RequestParam int id) {
        return ResponseEntity.ok(saleService.delete(id));
    }
}
