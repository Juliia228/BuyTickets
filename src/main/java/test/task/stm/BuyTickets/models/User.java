package test.task.stm.BuyTickets.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String login;
    private String password;
    private String first_name;
    private String last_name;
    private String patronymic;
    //private Role role;

    public User(String login, String password, String first_name, String last_name, String patronymic) {
        this.login = login;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.patronymic = patronymic;
    }
}
