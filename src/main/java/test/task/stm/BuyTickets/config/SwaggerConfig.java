package test.task.stm.BuyTickets.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Buy Tickets Api",
                description = "API для покупки транспортных билетов", version = "1.0.0",
                contact = @Contact(
                        name = "Комарова Юлия",
                        url = "https://web.telegram.org/k/#@iuliia_kom"
                )
        )
)
public class SwaggerConfig {
}
