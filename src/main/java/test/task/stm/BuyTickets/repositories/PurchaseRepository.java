package test.task.stm.BuyTickets.repositories;

import org.springframework.jdbc.core.RowMapper;
import test.task.stm.BuyTickets.models.Purchase;
import test.task.stm.BuyTickets.models.DTO.PurchaseRequest;

import java.sql.ResultSet;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

public interface PurchaseRepository {
    RowMapper<Purchase> ROW_MAPPER = (ResultSet resultSet, int rowNum) ->
            new Purchase(resultSet.getInt("id"),
                    resultSet.getInt("user_id"),
                    resultSet.getInt("ticket_id"),
                    resultSet.getObject("sold_at", OffsetDateTime.class).atZoneSameInstant(ZoneId.of("Europe/Moscow")));

    Purchase get(int id);

    List<Purchase> getAll();

    Purchase save(PurchaseRequest purchase);

    Purchase update(Purchase purchase);

    int delete(int id);
}
