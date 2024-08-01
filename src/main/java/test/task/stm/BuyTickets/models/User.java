package test.task.stm.BuyTickets.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность пользователя")
public class User {
    @Schema(description = "Идентификатор")
    @Min(value = 1)
    @NotNull
    private int id;
    @Schema(description = "Логин (электронная почта)", example = "mail@mail.ru")
    @Email(message = "login must be email")
    @NotBlank(message = "login is required")
    private String login;
    @Schema(description = "Пароль. Должен содержать не менее 8 символов, хотя бы 1 цифру, 1 заглавную букву, 1 строчную букву и 1 специальный символ", example = "creativePassword1!")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,}$", message = "password must be at least 8 characters long, contain at least 1 digit, 1 uppercase letter, 1 lowercase letter and 1 special character")
    @NotBlank(message = "password is required")
    private String password;
    @Schema(description = "Фамилия")
    @NotBlank(message = "last name is required")
    private String last_name;
    @Schema(description = "Имя")
    @NotBlank(message = "first name is required")
    private String first_name;
    @Schema(description = "Отчество")
    private String patronymic;
    //private Role role;
}
