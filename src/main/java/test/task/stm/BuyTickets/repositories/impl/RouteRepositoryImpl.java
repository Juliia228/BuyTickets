package test.task.stm.BuyTickets.repositories.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import test.task.stm.BuyTickets.models.Route;
import test.task.stm.BuyTickets.models.RouteRequest;
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
        return jdbcTemplate.queryForObject("select * from routes where id = ?", ROW_MAPPER, id);
    }

    @Override
    public List<Route> getAll() {
        return jdbcTemplate.query("select * from routes", ROW_MAPPER);
    }

    @Override
    public Route save(RouteRequest route) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("insert into routes (departure_point, destination_point, transporter_name, minutes) values (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, route.getDeparture_point());
            ps.setString(2, route.getDestination_point());
            ps.setString(3, route.getTransporter_name());
            ps.setInt(4, route.getMinutes());
            return ps;
        }, generatedKeyHolder);
        if (generatedKeyHolder.getKeys() == null) {
            throw new InvalidDataAccessApiUsageException("Route object cannot be saved");
        }
        return get((Integer) generatedKeyHolder.getKeys().get("id"));
    }

    @Override
    public Route update(Route route) {
        jdbcTemplate.update("update routes set departure_point = ?, destination_point = ?, transporter_name = ?, minutes = ? where id = ?", route.getDeparture_point(), route.getDestination_point(), route.getTransporter_name(), route.getMinutes(), route.getId());
        return get(route.getId());
    }

    @Override
    public int delete(int id) {
        return jdbcTemplate.update("delete from routes where id = ?", id);
    }
}
