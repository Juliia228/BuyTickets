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
import test.task.stm.BuyTickets.models.request.BuyTicketRequest;
import test.task.stm.BuyTickets.models.Sale;
import test.task.stm.BuyTickets.models.request.SaleRequest;
import test.task.stm.BuyTickets.services.SaleService;

import java.util.List;

@Validated
@RestController
@Tag(name="Покупка билетов", description="Управляет сущностями осуществленных покупок билетов")
public class SaleController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @Operation(
            summary = "Получение данных о покупке",
            description = "Позволяет найти информацию о продаже билета"
    )
    @GetMapping("/sale/getById")
    public ResponseEntity<Sale> getSale(@RequestParam @Min(1) @Parameter(description = "Идентификатор продажи") int sale_id) {
        return ResponseEntity.ok(saleService.find(sale_id));
    }

    @Operation(
            summary = "Получение всех осуществленных продаж билетов"
    )
    @GetMapping("/sale/getAll")
    public ResponseEntity<List<Sale>> getAllSales() {
        return ResponseEntity.ok(saleService.findAll());
    }

    @Operation(
            summary = "Осуществление покупки билета",
            description = ""
    )
    @PostMapping("/buyTicket")
    public ResponseEntity<Sale> buyTicket(@Valid @RequestBody @Parameter(description = "id пользователя, купившего билет и id купленного билета") BuyTicketRequest request) throws BadRequestException {
        return ResponseEntity.ok(saleService.createSale(request));
    }

    @Operation(
            summary = "Добавление информации о продаже",
            description = "Позволяет добавить новую продажу билета"
    )
    @PostMapping("/sale/add")
    public ResponseEntity<Sale> newSale(@Valid @RequestBody @Parameter(description = "Данные о продаже") SaleRequest new_sale) {
        return ResponseEntity.ok(saleService.add(new_sale));
    }

    @Operation(
            summary = "Обновление данных о продаже билета",
            description = "Позволяет обновить данные о продаже"
    )
    @PutMapping("/sale/edit")
    public ResponseEntity<Sale> updateSale(@Valid @RequestBody @Parameter(description = "Новые данные о продаже") Sale new_sale) {
        return ResponseEntity.ok(saleService.edit(new_sale));
    }

    @Operation(
            summary = "Удаление информации об осуществленной продаже билета",
            description = "Позволяет удалить информацию о продаже по ее id"
    )
    @DeleteMapping("/sale/delete")
    public ResponseEntity<String> deleteSale(@RequestParam @Min(1) @Parameter(description = "Идентификатор продажи") int sale_id) {
        saleService.delete(sale_id);
        return ResponseEntity.ok("Deleted successfully");
    }
}
