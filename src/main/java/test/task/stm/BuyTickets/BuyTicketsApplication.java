package test.task.stm.BuyTickets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import test.task.stm.BuyTickets.models.DTO.RouteRequest;
import test.task.stm.BuyTickets.models.DTO.TicketRequest;
import test.task.stm.BuyTickets.models.DTO.UserRequest;
import test.task.stm.BuyTickets.models.Role;
import test.task.stm.BuyTickets.models.Transporter;
import test.task.stm.BuyTickets.repositories.*;
import test.task.stm.BuyTickets.services.UserService;

import java.sql.Timestamp;

@EnableAutoConfiguration
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class BuyTicketsApplication {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	public static void main(String[] args) {
		SpringApplication.run(BuyTicketsApplication.class, args);
	}

	@Transactional
	@Bean
	CommandLineRunner commandLineRunner (
			RouteRepository routeRepository,
			TicketRepository ticketRepository,
			TransporterRepository transporterRepository,
			UserService userService,
			PasswordEncoder passwordEncoder) {
		return args -> {
			transporterRepository.save(new Transporter("GoGo", "88005553535"));
			transporterRepository.save(new Transporter("Aviasales", "88003964384"));
			transporterRepository.save(new Transporter("РЖД", "88007750000"));

			userService.add(new UserRequest("first@login.com", passwordEncoder.encode("mypaSSword1&"), "Фамилия", "Имя", "Отчество", null));
			userService.add(new UserRequest("ivan@yandex.ru", passwordEncoder.encode("123*Paspaspas"), "Иванов", "Иван", "Иванович", null));
			userService.add(new UserRequest("admin@login.com", passwordEncoder.encode("Admin000#"), "Комарова", "Юлия", "Андреевна", new String[]{Role.ROLE_ADMIN.name()}));
			userService.add(new UserRequest("log@log.log", passwordEncoder.encode("passworD12!"), "Пупкин", "Василий", "Васильевич", null));

			int route_id1 = routeRepository.save(new RouteRequest("Нижний Новгород", "Москва", "РЖД", 420)).getId();
			int route_id2 = routeRepository.save(new RouteRequest("Нижний Новгород", "Турция", "Aviasales", 250)).getId();
			int route_id3 = routeRepository.save(new RouteRequest("Москва", "Казань", "РЖД", 600)).getId();
			int route_id4 = routeRepository.save(new RouteRequest("Москва", "Нижний Новгород", "GoGo", 400)).getId();

			for (int seat_number = 1; seat_number <= 10; seat_number++) {
				ticketRepository.save(new TicketRequest(route_id1, Timestamp.valueOf("2024-09-15 12:30:00.0"), seat_number, seat_number * 1000));
				ticketRepository.save(new TicketRequest(route_id2, Timestamp.valueOf("2024-10-12 03:40:00.0"), seat_number, seat_number * 500));
				ticketRepository.save(new TicketRequest(route_id3, Timestamp.valueOf("2024-08-02 15:00:00.0"), seat_number, seat_number * 5000));
				ticketRepository.save(new TicketRequest(route_id4, Timestamp.valueOf("2024-08-08 22:10:00.0"), seat_number, seat_number * 2500));
			}
		};
	}
}
