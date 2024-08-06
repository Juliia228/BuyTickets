package test.task.stm.BuyTickets.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import test.task.stm.BuyTickets.exception.DataNotFoundException;
import test.task.stm.BuyTickets.exception.RegistrationException;
import test.task.stm.BuyTickets.models.*;
import test.task.stm.BuyTickets.models.DTO.AuthResponse;
import test.task.stm.BuyTickets.models.DTO.UserRequest;
import test.task.stm.BuyTickets.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
    }

    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return findByLogin(username);
    }

    public User find(int id) {
        return userRepository.get(id);
    }

    public User findByLogin(String login) {
        return userRepository.getByLogin(login);
    }

    public List<User> findAll() {
        return userRepository.getAll();
    }

    public User add(UserRequest user) {
        if (user.getRoles() == null || user.getRoles().length == 0) {
            user.setRoles(new String[]{Role.ROLE_USER.name()});
        } else {
            List<String> roles = new ArrayList<>(Arrays.asList(user.getRoles()));
            if (!roles.contains(Role.ROLE_USER.name())) {
                roles.add(Role.ROLE_USER.name());
            }
            user.setRoles(roles.toArray(user.getRoles()));
        }
        return userRepository.save(user);
    }

    public AuthResponse authorise(String login, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
        if (authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            new UserDetailsImpl(userService.findByLogin(request.getLogin()));
            String jwtToken = jwtService.generateToken(userDetails);
            RefreshToken token = refreshTokenService.createRefreshToken(login);;
            User user = findByLogin(login);
            return new AuthResponse(user, jwtToken, token.getToken(), token.getExpiration());
        } else {
            throw new UsernameNotFoundException("User is not authorized");
        }
    }

    public String createToken(UserRequest new_user) {
        if (userRepository.isUserExistByLogin(new_user.getLogin())) {
            throw new RegistrationException("User with login=" + new_user.getLogin() + " already exists");
        }
        new_user.setPassword(passwordEncoder.encode(new_user.getPassword()));
        User user = userRepository.save(new_user);
        return jwtService.generateToken(new UserDetailsImpl(user));
    }

    public User setRoleAdmin(int id) {
        User user = find(id);
        List<String> roles = new ArrayList<>(Arrays.asList(user.getRoles()));
        if (!roles.contains(Role.ROLE_ADMIN.name())) {
            roles.add(Role.ROLE_ADMIN.name());
        }
        user.setRoles(roles.toArray(user.getRoles()));
        return userRepository.update(user);
    }

    public User edit(User new_user) {
        return userRepository.update(new_user);
    }

    public void delete(int id) {
        if (userRepository.delete(id) == 0) {
            throw new DataNotFoundException("No data was found for deletion");
        }
    }
}
