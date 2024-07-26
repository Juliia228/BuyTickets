package test.task.stm.BuyTickets.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import test.task.stm.BuyTickets.model.User;

@RestController
public class UserController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/new/user")
    public void saveUser() {
        log.info("request");
        log.info(Integer.toString(save()));
        log.info(findById(1L).toString());
    }

    public int save() {
        return jdbcTemplate.update("INSERT INTO users (login, password, full_name) VALUES(?,?,?)",
                new Object[] { "itslogin", "itspassword", "My Full Name" });
    }

    public User findById(Long id) {
        try {
            User user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE id=?",
                    BeanPropertyRowMapper.newInstance(User.class), id);
            return user;
        } catch (Exception e) {
            return null;
        }
    }
}
