package test.task.stm.BuyTickets.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность перевозчика")
public class Transporter {
    @Schema(description = "Название перевозчика")
    @NotBlank(message = "transporter name is required")
    private String name;
    @Schema(description = "Номер телефона", example = "88006285738")
    @Pattern(regexp = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$", message = "phone number must be Russian and valid")
    private String phone;
}
