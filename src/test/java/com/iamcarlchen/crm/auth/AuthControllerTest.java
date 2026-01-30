package com.iamcarlchen.crm.auth;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.iamcarlchen.crm.employees.Employee;
import com.iamcarlchen.crm.employees.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

  @Autowired MockMvc mvc;

  @MockBean EmployeeService employeeService;
  @MockBean JwtService jwtService;

  @Test
  void login_success_returns_token_and_user() throws Exception {
    Employee e = new Employee();
    e.setId(1L);
    e.setUsername("alice");
    e.setName("Alice");
    e.setRole("ADMIN");
    e.setStatus("ACTIVE");

    when(employeeService.getByUsername("alice")).thenReturn(e);
    when(employeeService.verifyPassword(eq(e), eq("pw"))).thenReturn(true);
    when(jwtService.issueToken(eq("alice"), anyMap())).thenReturn("jwt-token");

    mvc.perform(post("/api/auth/login")
            .contentType("application/json")
            .content("{\"username\":\"alice\",\"password\":\"pw\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").value("jwt-token"))
        .andExpect(jsonPath("$.username").value("alice"))
        .andExpect(jsonPath("$.role").value("ADMIN"));
  }

  @Test
  void login_inactive_user_returns_401() throws Exception {
    Employee e = new Employee();
    e.setUsername("bob");
    e.setRole("USER");
    e.setStatus("INACTIVE");

    when(employeeService.getByUsername("bob")).thenReturn(e);

    mvc.perform(post("/api/auth/login")
            .contentType("application/json")
            .content("{\"username\":\"bob\",\"password\":\"pw\"}"))
        .andExpect(status().isUnauthorized());
  }
}
