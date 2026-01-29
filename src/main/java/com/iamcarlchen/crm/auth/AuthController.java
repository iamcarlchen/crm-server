package com.iamcarlchen.crm.auth;

import com.iamcarlchen.crm.auth.AuthDtos.LoginRequest;
import com.iamcarlchen.crm.auth.AuthDtos.LoginResponse;
import com.iamcarlchen.crm.auth.AuthDtos.MeResponse;
import com.iamcarlchen.crm.employees.Employee;
import com.iamcarlchen.crm.employees.EmployeeService;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final EmployeeService employeeService;
  private final JwtService jwtService;

  public AuthController(EmployeeService employeeService, JwtService jwtService) {
    this.employeeService = employeeService;
    this.jwtService = jwtService;
  }

  @PostMapping("/login")
  public LoginResponse login(@Valid @RequestBody LoginRequest req) {
    Employee e = employeeService.getByUsername(req.username());
    if (!"ACTIVE".equalsIgnoreCase(e.getStatus())) {
      throw new UnauthorizedException();
    }
    if (!employeeService.verifyPassword(e, req.password())) {
      throw new UnauthorizedException();
    }

    String token =
        jwtService.issueToken(e.getUsername(), Map.of("role", e.getRole()));

    return new LoginResponse(token, e.getUsername(), e.getRole());
  }

  @GetMapping("/me")
  public MeResponse me(Authentication auth) {
    if (auth == null || !(auth.getPrincipal() instanceof EmployeePrincipal p)) {
      throw new UnauthorizedException();
    }
    return new MeResponse(p.id(), p.username(), p.name(), p.role());
  }

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  static class UnauthorizedException extends RuntimeException {}
}
