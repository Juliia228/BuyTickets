package test.task.stm.BuyTickets.models;

import lombok.Data;

@Data
public class User {
    private long id;
    private String login;
    private String password;
    private String first_name;
    private String last_name;
    private String patronymic;
    //private Role role;
}
