package test.task.stm.BuyTickets.repositories.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import test.task.stm.BuyTickets.models.Sale;
import test.task.stm.BuyTickets.repositories.SaleRepository;

import java.util.List;

@Repository
public class SaleRepositoryImpl implements SaleRepository {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final JdbcTemplate jdbcTemplate;

    public SaleRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Sale get(int id) {
        Sale sale = null;
        try {
            sale = jdbcTemplate.queryForObject("select * from sales where id = ?", ROW_MAPPER, id);
        } catch (DataAccessException dataAccessException) {
            log.info("Couldn't find entity of type Sale with id {}", id);
        }
        return sale;
    }

    @Override
    public List<Sale> getAll() {
        return jdbcTemplate.query("select * from sales", ROW_MAPPER);
    }

    @Override
    public Sale save(Sale sale) {
        try {
            jdbcTemplate.update("insert into sales values (?, ?, ?, ?)", sale.getId(), sale.getUser_id(), sale.getTicket_id(), sale.getSold_at());
        } catch (Exception e) {
            log.info("Продажа билета " + sale.getId() + " не добавлена");
        }
        return get(sale.getId());
    }

    @Override
    public Sale update(Sale sale) {
        try {
            jdbcTemplate.update("update sales set user_id = ?, ticket_id = ?, sold_at = ? where id = ?", sale.getUser_id(), sale.getTicket_id(), sale.getSold_at(), sale.getId());
        } catch (Exception e) {
            log.debug("Данные о продаже " + sale.getId() + " не обновлены");
        }
        return get(sale.getId());
    }

    @Override
    public int delete(int id) {
        return jdbcTemplate.update("delete from sales where id = ?", id);
    }
}
