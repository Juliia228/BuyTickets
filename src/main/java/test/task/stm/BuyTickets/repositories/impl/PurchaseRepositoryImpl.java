package test.task.stm.BuyTickets.repositories.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import test.task.stm.BuyTickets.models.Purchase;
import test.task.stm.BuyTickets.models.request.PurchaseRequest;
import test.task.stm.BuyTickets.repositories.PurchaseRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class PurchaseRepositoryImpl implements PurchaseRepository {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final JdbcTemplate jdbcTemplate;

    public PurchaseRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Purchase get(int id) {
        return jdbcTemplate.queryForObject("select * from purchases where id = ?", ROW_MAPPER, id);
    }

    @Override
    public List<Purchase> getAll() {
        return jdbcTemplate.query("select * from purchases", ROW_MAPPER);
    }

    @Override
    public Purchase save(PurchaseRequest purchase) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("insert into purchases (user_id, ticket_id, sold_at) values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, purchase.getUser_id());
            ps.setInt(2, purchase.getTicket_id());
            ps.setTimestamp(3, purchase.getSold_at());
            return ps;
        }, generatedKeyHolder);
        if (generatedKeyHolder.getKeys() == null) {
            throw new InvalidDataAccessApiUsageException("Purchase object cannot be saved");
        }
        return get((Integer) generatedKeyHolder.getKeys().get("id"));
    }

    @Override
    public Purchase update(Purchase purchase) {
        jdbcTemplate.update("update purchases set user_id = ?, ticket_id = ?, sold_at = ? where id = ?", purchase.getUser_id(), purchase.getTicket_id(), purchase.getSold_at(), purchase.getId());
        return get(purchase.getId());
    }

    @Override
    public int delete(int id) {
        return jdbcTemplate.update("delete from purchases where id = ?", id);
    }
}
