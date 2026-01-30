package com.iamcarlchen.crm.news;

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

@WebMvcTest(controllers = NewsController.class)
@AutoConfigureMockMvc(addFilters = false)
class NewsControllerTest {

  @Autowired MockMvc mvc;

  @MockBean NewsService service;
  @MockBean JwtAuthFilter jwtAuthFilter;

  @Test
  void list_returns_array() throws Exception {
    NewsEntity n = new NewsEntity();
    n.setId(1L);
    n.setTitle("Hello");
    n.setStatus("DRAFT");
    n.setPublishedAt(LocalDateTime.parse("2026-01-01T00:00:00"));

    when(service.list(null)).thenReturn(List.of(n));

    mvc.perform(get("/api/news"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].title").value("Hello"))
        .andExpect(jsonPath("$[0].status").value("DRAFT"));
  }

  @Test
  void create_returns_created_news() throws Exception {
    NewsEntity n = new NewsEntity();
    n.setId(2L);
    n.setTitle("New");
    n.setStatus("PUBLISHED");

    when(service.create(any())).thenReturn(n);

    mvc.perform(post("/api/news")
            .contentType("application/json")
            .content("{" +
                "\"title\":\"New\"," +
                "\"status\":\"PUBLISHED\"," +
                "\"content\":\"Hi\"" +
                "}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(2))
        .andExpect(jsonPath("$.title").value("New"));

    verify(service, times(1)).create(any(NewsDtos.NewsUpsertRequest.class));
  }
}
