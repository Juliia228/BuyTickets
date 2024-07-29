package test.task.stm.BuyTickets.repositories.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import test.task.stm.BuyTickets.models.Transporter;
import test.task.stm.BuyTickets.repositories.TransporterRepository;

import java.util.List;

@Repository
public class TransporterRepositoryImpl implements TransporterRepository {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final JdbcTemplate jdbcTemplate;

    public TransporterRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Transporter get(String name) {
        Transporter transporter = null;
        try {
            transporter = jdbcTemplate.queryForObject("select * from transporters where name = ?", new Object[]{name}, ROW_MAPPER);
        } catch (DataAccessException dataAccessException) {
            log.info("Couldn't find entity of type Transporter with name {}", name);
        }
        return transporter;
    }

    @Override
    public List<Transporter> getAll() {
        return jdbcTemplate.query("select * from transporters", ROW_MAPPER);
    }

    @Override
    public Transporter save(Transporter transporter) {
        try {
            jdbcTemplate.update("insert into transporters values (?, ?)", transporter.getName(), transporter.getPhone());
        } catch (Exception e) {
            log.info("Перевозчик " + transporter.getName() + " не добавлен");
        }
        return get(transporter.getName());
    }

    @Override
    public Transporter update(Transporter transporter) {
        try {
            jdbcTemplate.update("update transporters set phone = ? where name = ?", transporter.getPhone(), transporter.getName());
        } catch (Exception e) {
            log.info("Данные о перевозчике " + transporter.getName() + " не обновлены");
        }
        return get(transporter.getName());
    }

    @Override
    public int delete(String name) {
        return jdbcTemplate.update("delete from transporters where name = ?", name);
    }
}
