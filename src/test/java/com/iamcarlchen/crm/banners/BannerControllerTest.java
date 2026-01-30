package com.iamcarlchen.crm.banners;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.iamcarlchen.crm.auth.JwtAuthFilter;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = BannerController.class)
@AutoConfigureMockMvc(addFilters = false)
class BannerControllerTest {

  @Autowired MockMvc mvc;

  @MockBean BannerService service;
  @MockBean JwtAuthFilter jwtAuthFilter;

  @Test
  void list_returns_array() throws Exception {
    BannerEntity b = new BannerEntity();
    b.setId(1L);
    b.setName("Banner A");
    b.setPosition("HOME_TOP");
    b.setStatus("ONLINE");

    when(service.list(null, null)).thenReturn(List.of(b));

    mvc.perform(get("/api/banners"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].name").value("Banner A"))
        .andExpect(jsonPath("$[0].position").value("HOME_TOP"))
        .andExpect(jsonPath("$[0].status").value("ONLINE"));
  }

  @Test
  void create_returns_created_banner() throws Exception {
    BannerEntity b = new BannerEntity();
    b.setId(2L);
    b.setName("Banner B");
    b.setPosition("HOME_TOP");
    b.setStatus("DRAFT");

    when(service.create(any(), any())).thenReturn(b);

    mvc.perform(post("/api/banners")
            .contentType("application/json")
            .content("{" +
                "\"name\":\"Banner B\"," +
                "\"position\":\"HOME_TOP\"," +
                "\"status\":\"DRAFT\"" +
                "}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(2));

    verify(service, times(1)).create(any(BannerDtos.BannerUpsertRequest.class), any());
  }
}
