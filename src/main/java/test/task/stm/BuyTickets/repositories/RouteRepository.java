package test.task.stm.BuyTickets.repositories;

import org.springframework.jdbc.core.RowMapper;
import test.task.stm.BuyTickets.models.Route;

import java.sql.ResultSet;
import java.util.List;

public interface RouteRepository {
    RowMapper<Route> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> new Route(resultSet.getInt("id"), resultSet.getString("from"), resultSet.getString("to"), resultSet.getString("transporter_name"), resultSet.getInt("minutes"));

    Route get(int id);

    List<Route> getAll();

    Route save(Route route);

    Route update(Route route);

    int delete(int id);
}
