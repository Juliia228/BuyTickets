package test.task.stm.BuyTickets.model;

import lombok.Data;

@Data
public class User {
    private long id;
    private String login;
    private String password;
    private String full_name;
}
