package test.task.stm.BuyTickets.repositories;

import org.springframework.jdbc.core.RowMapper;
import test.task.stm.BuyTickets.models.RefreshToken;
import test.task.stm.BuyTickets.models.DTO.RefreshTokenRequest;

import java.sql.ResultSet;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

public interface RefreshTokenRepository {
    RowMapper<RefreshToken> ROW_MAPPER = (ResultSet resultSet, int rowNum) ->
            new RefreshToken(resultSet.getInt("id"),
                    resultSet.getString("token"),
                    resultSet.getObject("expiration", OffsetDateTime.class).atZoneSameInstant(ZoneId.of("Europe/Moscow")),
                    resultSet.getInt("user_id"));

    RefreshToken get(int id);

    RefreshToken getByToken(String token);

    List<RefreshToken> getAll();

    RefreshToken save(RefreshTokenRequest refreshToken);

    RefreshToken update(RefreshToken refreshToken);

    int delete(int id);
}
