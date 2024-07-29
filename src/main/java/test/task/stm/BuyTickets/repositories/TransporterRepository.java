package test.task.stm.BuyTickets.repositories;

import org.springframework.jdbc.core.RowMapper;
import test.task.stm.BuyTickets.models.Transporter;

import java.sql.ResultSet;
import java.util.List;

public interface TransporterRepository {
    RowMapper<Transporter> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> new Transporter(resultSet.getString("name"), resultSet.getString("phone"));

    Transporter get(String name);

    List<Transporter> getAll();

    Transporter save(Transporter transporter);

    Transporter update(Transporter transporter);

    int delete(String name);
}
