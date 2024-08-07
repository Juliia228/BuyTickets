package test.task.stm.BuyTickets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import test.task.stm.BuyTickets.controllers.TicketController;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ContextConfiguration
public class AuthenticationTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    TicketController ticketController;

    @WithMockUser(authorities = "ROLE_USER")
    @Test
    @DisplayName("GET /ticket/available/get для авторизованного пользователя, возвращает HTTP-ответ со статусом 200 Ok")
    public void userIsAuthorized_GetTicketsByParams_ReturnsStatusOK() throws Exception {
        this.mockMvc.perform(get("/ticket/available/get"))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "ROLE_USER")
    @Test
    @DisplayName("GET /ticket/getAllNotAvailable для авторизованного пользователя с ролью USER, возвращает HTTP-ответ со статусом 403 Forbidden")
    public void userHaveRoleUser_GetAllNotAvailableTickets_ReturnsStatusForbidden() throws Exception {
        this.mockMvc.perform(get("/ticket/getAllNotAvailable")) // разрешен только для ADMIN
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ROLE_ADMIN")
    @Test
    @DisplayName("GET /ticket/getAllNotAvailable для авторизованного пользователя с ролью ADMIN, возвращает HTTP-ответ со статусом 200 OK")
    public void userHaveRoleAdmin_GetAllNotAvailableTickets_ReturnsStatusOK() throws Exception {
        this.mockMvc.perform(get("/ticket/getAllNotAvailable")) // разрешен только для ADMIN
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /ticket/available/get для неавторизованного пользователя, возвращает HTTP-ответ со статусом 403 Forbidden")
    public void userIsUnauthorized_GetTicketsByParams_ReturnsStatusForbidden() throws Exception {
        this.mockMvc.perform(get("/ticket/available/get"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("POST /user/signIn для неавторизованного пользователя с корректными данными в запросе, возвращает HTTP-ответ со статусом 200 Ok")
    public void userIsUnauthorizedAndRequestBodyIsValid_AuthenticateUser_ReturnsStatusOK() throws Exception {
        this.mockMvc.perform(post("/user/signIn")
                        .content("""
                                {
                                    "login": "ivan@yandex.ru",
                                    "password": "123*Paspaspas"
                                }
                                """)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /user/signIn для неавторизованного пользователя с некорректным паролем, возвращает HTTP-ответ со статусом 403 Forbidden и выбрасывает исключение BadCredentialsException")
    public void userIsUnauthorizedAndPasswordIsInvalid_AuthenticateUser_ReturnsStatusForbiddenAndThrowBadCredentialsException() throws Exception {
        this.mockMvc.perform(post("/user/signIn")
                        .content("""
                                {
                                    "login": "ivan@yandex.ru",
                                    "password": "1NonexitingPassword*"
                                }
                                """)
                        .contentType("application/json"))
                .andExpect(status().isForbidden())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadCredentialsException));
    }

    @Test
    @DisplayName("GET /ticket/getAll для авторизованного пользователя с неверным токеном, возвращает HTTP-ответ со статусом 401 Unauthorized")
    public void invalidToken_GetAllTickets_ReturnsStatusUnauthorized() throws Exception {
        this.mockMvc.perform(get("/ticket/getAll")
                        .header("Authorization", "Bearer RANDOM.WRONG4STRING.IT7IS5NOT9TOKEN"))
                .andExpect(status().isUnauthorized());
    }
}
