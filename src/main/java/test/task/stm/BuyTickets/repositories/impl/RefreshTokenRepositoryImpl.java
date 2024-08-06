package test.task.stm.BuyTickets.repositories.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import test.task.stm.BuyTickets.models.RefreshToken;
import test.task.stm.BuyTickets.models.DTO.RefreshTokenRequest;
import test.task.stm.BuyTickets.repositories.RefreshTokenRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final JdbcTemplate jdbcTemplate;

    public RefreshTokenRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public RefreshToken get(int id) {
        return jdbcTemplate.queryForObject("select * from tokens " +
                "where id = ?", ROW_MAPPER, id);
    }

    @Override
    public RefreshToken getByToken(String token) {
        return jdbcTemplate.queryForObject("select * from tokens " +
                "where token = ?", ROW_MAPPER, token);
    }

    @Override
    public List<RefreshToken> getAll() {
        return jdbcTemplate.query("select * from tokens", ROW_MAPPER);
    }

    @Override
    public RefreshToken save(RefreshTokenRequest refreshToken) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("insert into tokens (token, expiration, user_id) values (?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, refreshToken.getToken());
            ps.setObject(2, refreshToken.getExpiration().toOffsetDateTime());
            ps.setInt(3, refreshToken.getUser_id());
            return ps;
        }, generatedKeyHolder);
        if (generatedKeyHolder.getKeys() == null) {
            throw new InvalidDataAccessApiUsageException("Token object cannot be saved");
        }
        return get((Integer) generatedKeyHolder.getKeys().get("id"));
    }

    @Override
    public RefreshToken update(RefreshToken refreshToken) {
        jdbcTemplate.update("update tokens set token = ?, expiration = ?, user_id = ? where id = ?",
                refreshToken.getToken(), refreshToken.getExpiration(), refreshToken.getUser_id(), refreshToken.getId());
        return get(refreshToken.getId());
    }

    @Override
    public int delete(int id) {
        return jdbcTemplate.update("delete from tokens " +
                "where id = ?", id);
    }
}
