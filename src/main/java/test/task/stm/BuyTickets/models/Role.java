package test.task.stm.BuyTickets.models;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Перечисление пользовательских ролей")
public enum Role {
    @Schema(description = "Покупатель")
    ROLE_USER,
    @Schema(description = "Администратор")
    ROLE_ADMIN;

    public String getRoleName() {
        return this.name().substring("ROLE_".length());
    }
}
