package test.task.stm.BuyTickets.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import test.task.stm.BuyTickets.exception.DataNotFoundException;
import test.task.stm.BuyTickets.exception.RegistrationException;
import test.task.stm.BuyTickets.models.Role;
import test.task.stm.BuyTickets.models.User;
import test.task.stm.BuyTickets.models.UserDetailsImpl;
import test.task.stm.BuyTickets.models.request.UserRequest;
import test.task.stm.BuyTickets.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {
    UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
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
        List<User> users = userRepository.getAll();
        if (users.isEmpty()) {
            throw new DataNotFoundException("0 objects was found");
        }
        return users;
    }

    public User add(UserRequest user) {
        return userRepository.save(user);
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
        user.getRoles().add(Role.ROLE_ADMIN.name());
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
