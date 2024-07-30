package test.task.stm.BuyTickets.repositories.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import test.task.stm.BuyTickets.models.Route;
import test.task.stm.BuyTickets.repositories.RouteRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class RouteRepositoryImpl implements RouteRepository {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final JdbcTemplate jdbcTemplate;

    public RouteRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Route get(int id) {
        Route route = null;
        try {
            route = jdbcTemplate.queryForObject("select * from routes where id = ?", ROW_MAPPER, id);
        } catch (DataAccessException dataAccessException) {
            log.info("Couldn't find entity of type Route with id {}", id);
        }
        return route;
    }

    @Override
    public List<Route> getAll() {
        return jdbcTemplate.query("select * from routes", ROW_MAPPER);
    }

    @Override
    public Route save(Route route) {
        try {
            GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement("insert into routes (from, to, transporter_name, minutes) values (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, route.getFrom());
                ps.setString(2, route.getTo());
                ps.setString(3, route.getTransporter_name());
                ps.setInt(4, route.getMinutes());
                return ps;
            }, generatedKeyHolder);
            if (generatedKeyHolder.getKeys() == null) {
                throw new RuntimeException();
            }
            return get((Integer) generatedKeyHolder.getKeys().get("id"));
        } catch (Exception e) {
            log.info("Маршрут " + route.getId() + " не добавлен");
            return null;
        }
    }

    @Override
    public Route update(Route route) {
        try {
            jdbcTemplate.update("update routes set from = ?, to = ?, transporter_name = ?, minutes = ? where id = ?", route.getFrom(), route.getTo(), route.getTransporter_name(), route.getMinutes(), route.getId());
        } catch (Exception e) {
            log.debug("Данные о маршруте " + route.getId() + " не обновлены");
        }
        return get(route.getId());
    }

    @Override
    public int delete(int id) {
        return jdbcTemplate.update("delete from routes where id = ?", id);
    }
}
