package test.task.stm.BuyTickets.repositories;

import org.springframework.jdbc.core.RowMapper;
import test.task.stm.BuyTickets.models.User;
import test.task.stm.BuyTickets.models.request.UserRequest;

import java.sql.ResultSet;
import java.util.List;

public interface UserRepository {
    RowMapper<User> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> new User(resultSet.getInt("id"), resultSet.getString("login"), resultSet.getString("password"), resultSet.getString("last_name"), resultSet.getString("first_name"), resultSet.getString("patronymic"));

    User get(int id);

    List<User> getAll();

    User save(UserRequest user);

    User update(User user);

    int delete(int id);
}
