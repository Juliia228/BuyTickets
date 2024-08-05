package test.task.stm.BuyTickets.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import test.task.stm.BuyTickets.models.User;
import test.task.stm.BuyTickets.repositories.UserRepository;
import test.task.stm.BuyTickets.models.UserDetailsImpl;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userRepository.getByLogin(username));
        return user.map(UserDetailsImpl::new)
                .orElseThrow(()->new UsernameNotFoundException("No user with login=" + username + " was found"));
    }
}
