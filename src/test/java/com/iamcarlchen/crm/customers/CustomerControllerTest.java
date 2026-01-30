package com.iamcarlchen.crm.customers;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.iamcarlchen.crm.auth.JwtAuthFilter;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
class CustomerControllerTest {

  @Autowired MockMvc mvc;

  @MockBean CustomerService service;
  @MockBean JwtAuthFilter jwtAuthFilter;

  @Test
  void list_returns_array() throws Exception {
    Customer c = new Customer();
    c.setId(1L);
    c.setName("Acme");
    c.setIndustry("Tech");
    c.setLevel("A");
    c.setCreatedAt(LocalDateTime.parse("2026-01-01T00:00:00"));
    c.setUpdatedAt(LocalDateTime.parse("2026-01-02T00:00:00"));

    when(service.list()).thenReturn(List.of(c));

    mvc.perform(get("/api/customers"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].name").value("Acme"))
        .andExpect(jsonPath("$[0].industry").value("Tech"));
  }

  @Test
  void create_validates_request_and_returns_created_customer() throws Exception {
    Customer created = new Customer();
    created.setId(2L);
    created.setName("Beta");

    when(service.create(any())).thenReturn(created);

    mvc.perform(post("/api/customers")
            .contentType("application/json")
            .content(
                "{" +
                "\"name\":\"Beta\"," +
                "\"industry\":\"Retail\"," +
                "\"level\":\"B\"," +
                "\"phone\":\"123\"," +
                "\"email\":\"b@ex.com\"," +
                "\"address\":\"Somewhere\"," +
                "\"owner\":\"alice\"" +
                "}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(2))
        .andExpect(jsonPath("$.name").value("Beta"));

    verify(service, times(1)).create(any(CustomerDtos.CustomerUpsertRequest.class));
  }
}
