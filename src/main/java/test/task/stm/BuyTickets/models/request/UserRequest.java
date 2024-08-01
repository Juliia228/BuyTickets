package test.task.stm.BuyTickets.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRequest {
    @Email(message = "login must be email")
    private String login;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,}$", message = "password must be at least 8 characters long, contain at least 1 digit, 1 uppercase letter, 1 lowercase letter and 1 special character")
    private String password;
    @NotBlank(message = "last name is required")
    private String last_name;
    @NotBlank(message = "first name is required")
    private String first_name;
    private String patronymic;
    //private Role role;
}
