package test.task.stm.BuyTickets.repositories;

import org.springframework.jdbc.core.RowMapper;
import test.task.stm.BuyTickets.models.Sale;
import test.task.stm.BuyTickets.models.SaleRequest;

import java.sql.ResultSet;
import java.util.List;

public interface SaleRepository {
    RowMapper<Sale> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> new Sale(resultSet.getInt("id"), resultSet.getInt("user_id"), resultSet.getInt("ticket_id"), resultSet.getTimestamp("sold_at"));

    Sale get(int id);

    List<Sale> getAll();

    Sale save(SaleRequest sale);

    Sale update(Sale sale);

    int delete(int id);
}
