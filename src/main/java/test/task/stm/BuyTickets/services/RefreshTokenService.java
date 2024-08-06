package test.task.stm.BuyTickets.services;

import io.jsonwebtoken.JwtException;
import org.springframework.stereotype.Service;
import test.task.stm.BuyTickets.models.RefreshToken;
import test.task.stm.BuyTickets.models.User;
import test.task.stm.BuyTickets.models.UserDetailsImpl;
import test.task.stm.BuyTickets.models.DTO.RefreshResponse;
import test.task.stm.BuyTickets.models.DTO.RefreshTokenRequest;
import test.task.stm.BuyTickets.repositories.RefreshTokenRepository;
import test.task.stm.BuyTickets.repositories.UserRepository;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository, JwtService jwtService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public RefreshResponse generateAccessToken(String requestRefreshToken) {
        RefreshToken refreshToken = findByToken(requestRefreshToken);
        if (!isTokenExpired(refreshToken)) {
            User user = userRepository.get(refreshToken.getUser_id());
            String new_token = jwtService.generateToken(new UserDetailsImpl(user));
            return new RefreshResponse(new_token, requestRefreshToken);
        }
        throw new JwtException("Refresh token=" + requestRefreshToken + " is expired. Please make a new login");
    }

    public RefreshToken createRefreshToken(String login) {
        RefreshTokenRequest refreshToken = new RefreshTokenRequest(UUID.randomUUID().toString(),
                ZonedDateTime.now().plusHours(1),
                userRepository.getByLogin(login).getId());
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.getByToken(token);
    }

    public boolean isTokenExpired(RefreshToken token) {
        if (token.getExpiration().isBefore(ZonedDateTime.now())) {
            refreshTokenRepository.delete(token.getId());
            return true;
        }
        return false;
    }
}
