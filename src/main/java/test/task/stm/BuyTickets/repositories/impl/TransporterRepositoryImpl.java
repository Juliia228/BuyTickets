package test.task.stm.BuyTickets.repositories.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        return jdbcTemplate.queryForObject("select * from transporters " +
                "where name = ?", ROW_MAPPER, name);
    }

    @Override
    public List<Transporter> getAll() {
        return jdbcTemplate.query("select * from transporters", ROW_MAPPER);
    }

    @Override
    public Transporter save(Transporter transporter) {
        jdbcTemplate.update("insert into transporters values (?, ?)",
                transporter.getName(), transporter.getPhone());
        return get(transporter.getName());
    }

    @Override
    public Transporter update(Transporter transporter) {
        jdbcTemplate.update("update transporters set phone = ? where name = ?",
                transporter.getPhone(), transporter.getName());
        return get(transporter.getName());
    }

    @Override
    public int delete(String name) {
        return jdbcTemplate.update("delete from transporters " +
                "where name = ?", name);
    }
}
