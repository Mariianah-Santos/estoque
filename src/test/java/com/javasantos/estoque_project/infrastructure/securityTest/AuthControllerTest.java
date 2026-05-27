package com.javasantos.estoque_project.infrastructure.securityTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.javasantos.estoque_project.DTO.LoginRequest;
import com.javasantos.estoque_project.controller.AuthController;
import com.javasantos.estoque_project.services.AuthService;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {



    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @Test
    void deveRetornarTokenAoFazerLogin() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("admin@email.com");
        loginRequest.setPassword("123456");

        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("token", "token.gerado.aqui");
        mockResponse.put("id", 1L);
        mockResponse.put("name", "Admin");
        mockResponse.put("role", "ADMIN");

        when(authService.login(any(LoginRequest.class)))
            .thenReturn(mockResponse);

        ResponseEntity<?> response = authController.login(loginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("token.gerado.aqui", ((Map<?, ?>) response.getBody()).get("token"));
    }

    @Test
    void deveRetornar401QuandoCredenciaisInvalidas() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("admin@email.com");
        loginRequest.setPassword("senhaerrada");

        when(authService.login(any(LoginRequest.class)))
            .thenThrow(new RuntimeException("Senha inválida"));

        ResponseEntity<?> response = authController.login(loginRequest);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Senha inválida", response.getBody());
    }
}
