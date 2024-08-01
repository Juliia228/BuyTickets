package test.task.stm.BuyTickets.repositories.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import test.task.stm.BuyTickets.models.User;
import test.task.stm.BuyTickets.repositories.UserRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User get(int id) {
        return jdbcTemplate.queryForObject("select * from users where id = ?", ROW_MAPPER, id);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("select * from users", ROW_MAPPER);
    }

    @Override
    public User save(User user) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("insert into users (login, password, last_name, first_name, patronymic) values (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getLast_name());
            ps.setString(4, user.getFirst_name());
            ps.setString(5, user.getPatronymic());
            return ps;
        }, generatedKeyHolder);
        if (generatedKeyHolder.getKeys() == null) {
            throw new InvalidDataAccessApiUsageException("User object cannot be saved");
        }
        return get((Integer) generatedKeyHolder.getKeys().get("id"));
    }

    @Override
    public User update(User user) {
        try {
            jdbcTemplate.update("update users set login = ?, password = ?, last_name = ?, first_name = ?, patronymic = ? where id = ?", user.getLogin(), user.getPassword(), user.getLast_name(), user.getFirst_name(), user.getPatronymic(), user.getId());
        } catch (Exception e) {
            log.info("Данные о пользователе " + user.getId() + " не обновлены");
        }
        return get(user.getId());
    }

    @Override
    public int delete(int id) {
        return jdbcTemplate.update("delete from users where id = ?", id);
    }
}
