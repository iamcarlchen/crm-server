package com.iamcarlchen.crm.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final com.iamcarlchen.crm.employees.EmployeeService employeeService;

  public JwtAuthFilter(JwtService jwtService, com.iamcarlchen.crm.employees.EmployeeService employeeService) {
    this.jwtService = jwtService;
    this.employeeService = employeeService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String auth = request.getHeader("Authorization");
    if (auth == null || !auth.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = auth.substring("Bearer ".length()).trim();
    try {
      var payload = jwtService.verify(token);
      var e = employeeService.getByUsername(payload.username());

      var principal = new EmployeePrincipal(e.getId(), e.getUsername(), e.getName(), e.getRole());
      var authorities = java.util.List.of(new SimpleGrantedAuthority("ROLE_" + e.getRole()));
      var authentication = new UsernamePasswordAuthenticationToken(principal, null, authorities);
      SecurityContextHolder.getContext().setAuthentication(authentication);

    } catch (Exception ignored) {
      // invalid token; ignore and continue as anonymous
    }

    filterChain.doFilter(request, response);
  }
}
