package test.task.stm.BuyTickets.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    @Email
    private String login;
    @Pattern(regexp = "/(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,}/g")
    private String password;
    @NotBlank
    private String last_name;
    @NotBlank
    private String first_name;
    private String patronymic;
    //private Role role;

    public User(String login, String password, String last_name, String  first_name, String patronymic) {
        this.login = login;
        this.password = password;
        this.last_name = last_name;
        this.first_name = first_name;
        this.patronymic = patronymic;
    }
}
