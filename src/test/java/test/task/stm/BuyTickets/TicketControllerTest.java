package test.task.stm.BuyTickets;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import test.task.stm.BuyTickets.exception.DataNotFoundException;
import test.task.stm.BuyTickets.models.Ticket;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ContextConfiguration
@WithMockUser(username = "admin@login.com", authorities = {"ROLE_ADMIN", "ROLE_USER"})
public class TicketControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getTicket_ExistingId_ReturnsValidResponse() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/ticket/getById")
                        .param("id", String.valueOf(50)))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn();
        String json = result.getResponse().getContentAsString();
        Ticket ticket = objectMapper.readValue(json, Ticket.class);

        assertNotNull(ticket);
        assertEquals(50, ticket.getId());
    }

    @Test
    void getTicket_NonExistingId_ReturnsStatusBadRequestAndEmptyResultDataAccessException() throws Exception {
        this.mockMvc.perform(get("/ticket/getById")
                        .param("id", String.valueOf(1000)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EmptyResultDataAccessException));
    }

    @Test
    void getTicket_NonExistingId_ReturnsStatusBadRequestAndValidationException() throws Exception {
        this.mockMvc.perform(get("/ticket/getById")
                        .param("id", String.valueOf(0))) // min = 1
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException));
    }

    @Test
    void getAllTickets_ReturnsStatusOkAndCorrectListSize() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/ticket/getAll"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType("application/json"))
                .andReturn();
        String json = result.getResponse().getContentAsString();
        List<Ticket> tickets = objectMapper.readValue(json, new TypeReference<>() {
        });

        assertNotNull(tickets);
        assertEquals(46, tickets.size());
    }

    @Test
    void getBoughtTickets_ReturnsStatusOkAndCorrectListSize() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/ticket/boughtTickets"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType("application/json"))
                .andReturn();
        String json = result.getResponse().getContentAsString();
        List<Ticket> tickets = objectMapper.readValue(json, new TypeReference<>() {
        });

        assertNotNull(tickets);
        assertEquals(3, tickets.size());
    }

    @Test
    void getAllNotAvailableTickets_ReturnsStatusOkAndCorrectListSize() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/ticket/getAllNotAvailable"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType("application/json"))
                .andReturn();
        String json = result.getResponse().getContentAsString();
        List<Ticket> tickets = objectMapper.readValue(json, new TypeReference<>() {
        });

        assertNotNull(tickets);
        assertEquals(5, tickets.size());
    }

    @Test
    void getTicketsByParams_WithoutParams_ReturnsStatusOkAndCorrectListSize() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/ticket/available/get"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType("application/json"))
                .andReturn();
        String json = result.getResponse().getContentAsString();
        List<Ticket> tickets = objectMapper.readValue(json, new TypeReference<>() {
        });

        assertNotNull(tickets);
        assertEquals(41, tickets.size());
    }

    @Test
    void getTicketsByParams_WithAllParams_ReturnsStatusOkAndCorrectListSize() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/ticket/available/get")
                        .param("offset", String.valueOf(1))
                        .param("size", String.valueOf(6))
                        .param("from", "2024-09-01 00:00:00.0")
                        .param("to", "2024-10-01 00:00:00.0")
                        .param("departure_point", "Нижний Новгород")
                        .param("destination_point", "Москва")
                        .param("transporter_name", "РЖД"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType("application/json"))
                .andReturn();
        String json = result.getResponse().getContentAsString();
        List<Ticket> tickets = objectMapper.readValue(json, new TypeReference<>() {
        });

        assertNotNull(tickets);
        assertEquals(6, tickets.size());
    }

    @Test
    void getTicketsByParams_WithFromAndDeparturePoint_ReturnsStatusOkAndCorrectListSize() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/ticket/available/get")
                        .param("from", "2024-08-16 10:00:00.0")
                        .param("departure_point", "Москва"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType("application/json"))
                .andReturn();
        String json = result.getResponse().getContentAsString();
        List<Ticket> tickets = objectMapper.readValue(json, new TypeReference<>() {
        });

        assertNotNull(tickets);
        assertEquals(10, tickets.size());
    }

    @Test
    void newTicket_ReturnsStatusOk() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/ticket/add")
                        .content("""
                                {
                                  "route_id": 28,
                                  "departure_at": "2024-10-12T03:40:00.0",
                                  "seat_number": 11,
                                  "price": 10000
                                }
                                """)
                        .contentType("application/json"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType("application/json"))
                .andReturn();
        String json = result.getResponse().getContentAsString();
        Ticket ticket = objectMapper.readValue(json, Ticket.class);

        assertNotNull(ticket);
        assertEquals(28, ticket.getRoute_id());
        assertEquals(11, ticket.getSeat_number());
        assertEquals(10000, ticket.getPrice());
    }

    @Test
    void newTicket_WithNonExcitingRouteId_ReturnsStatusBadRequestAndDataIntegrityViolationException() throws Exception {
        this.mockMvc.perform(post("/ticket/add")
                        .content("""
                                {
                                  "route_id": 200,
                                  "departure_at": "2024-10-12T03:40:00.0",
                                  "seat_number": 11,
                                  "price": 10000
                                }
                                """)
                        .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DataIntegrityViolationException));
    }

    @Test
    void updateTicket_ReturnsStatusOk() throws Exception {
        MvcResult result = this.mockMvc.perform(put("/ticket/edit")
                        .content("""
                                {
                                  "id": 66,
                                  "route_id": 28,
                                  "departure_at": "2024-10-12T03:40:00Z",
                                  "seat_number": 7,
                                  "price": 3200
                                }
                                """)
                        .contentType("application/json"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType("application/json"))
                .andReturn();
        String json = result.getResponse().getContentAsString();
        Ticket ticket = objectMapper.readValue(json, Ticket.class);

        assertNotNull(ticket);
        assertEquals(66, ticket.getId());
        assertEquals(3200, ticket.getPrice());
    }

    @Test
    void deleteTicket_ReturnsStatusOk() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/ticket/add")
                        .content("""
                                {
                                  "route_id": 28,
                                  "departure_at": "2024-10-12T03:40:00.0",
                                  "seat_number": 12,
                                  "price": 10000
                                }
                                """)
                        .contentType("application/json"))
                .andReturn();
        String json = result.getResponse().getContentAsString();
        Ticket ticket = objectMapper.readValue(json, Ticket.class);

        result = this.mockMvc.perform(delete("/ticket/delete")
                        .param("id", String.valueOf(ticket.getId())))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("Deleted successfully", result.getResponse().getContentAsString());
    }

    @Test
    void deleteTicket_ReturnsStatusBadRequestAndDataNotFoundException() throws Exception {
        this.mockMvc.perform(delete("/ticket/delete")
                        .param("id", String.valueOf(83)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DataNotFoundException));
    }
}
