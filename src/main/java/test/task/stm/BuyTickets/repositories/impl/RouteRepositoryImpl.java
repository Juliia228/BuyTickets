package test.task.stm.BuyTickets.repositories.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import test.task.stm.BuyTickets.models.Route;
import test.task.stm.BuyTickets.repositories.RouteRepository;

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
            route = jdbcTemplate.queryForObject("select * from routes where id = ?", new Object[]{id}, ROW_MAPPER);
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
            jdbcTemplate.update("insert into routes values (?, ?, ?, ?, ?)", route.getId(), route.getFrom(), route.getTo(), route.getTransporter_name(), route.getMinutes());
        } catch (Exception e) {
            log.info("Маршрут " + route.getId() + " не добавлен");
        }
        return get(route.getId());
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
