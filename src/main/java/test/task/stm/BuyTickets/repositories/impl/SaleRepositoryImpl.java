package test.task.stm.BuyTickets.repositories.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import test.task.stm.BuyTickets.models.Sale;
import test.task.stm.BuyTickets.models.SaleRequest;
import test.task.stm.BuyTickets.repositories.SaleRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;
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
        return jdbcTemplate.queryForObject("select * from sales where id = ?", ROW_MAPPER, id);
    }

    @Override
    public List<Sale> getAll() {
        return jdbcTemplate.query("select * from sales", ROW_MAPPER);
    }

    @Override
    public Sale save(SaleRequest sale) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("insert into sales (user_id, ticket_id, sold_at) values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, sale.getUser_id());
            ps.setInt(2, sale.getTicket_id());
            ps.setTimestamp(3, sale.getSold_at());
            return ps;
        }, generatedKeyHolder);
        if (generatedKeyHolder.getKeys() == null) {
            throw new InvalidDataAccessApiUsageException("Sale object cannot be saved");
        }
        return get((Integer) generatedKeyHolder.getKeys().get("id"));
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
