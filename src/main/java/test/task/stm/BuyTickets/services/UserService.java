package test.task.stm.BuyTickets.services;

import org.springframework.stereotype.Service;
import test.task.stm.BuyTickets.exception.DataNotFoundException;
import test.task.stm.BuyTickets.models.User;
import test.task.stm.BuyTickets.models.request.UserRequest;
import test.task.stm.BuyTickets.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User find(int id) {
        return userRepository.get(id);
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

    public User registerNewUser(UserRequest new_user) {
        return userRepository.save(new_user);
    }

    public User edit(User new_user) {
        return userRepository.update(new_user);
    }

    public void delete(int id) {
        if (userRepository.delete(id) == 0) {
            throw new DataNotFoundException("No data was found for deletion");
        };
    }
}
