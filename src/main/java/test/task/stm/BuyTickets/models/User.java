package test.task.stm.BuyTickets.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private int id;
    private String login;
    private String password;
    private String first_name;
    private String last_name;
    private String patronymic;
    //private Role role;
}
