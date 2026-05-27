package com.javasantos.estoque_project.infrastructure.securityTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.javasantos.estoque_project.DTO.LoginRequest;
import com.javasantos.estoque_project.infrastructure.entity.Responsible;
import com.javasantos.estoque_project.infrastructure.respository.ResponsibleRepository;
import com.javasantos.estoque_project.infrastructure.security.JwtService;
import com.javasantos.estoque_project.services.AuthService;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

	@InjectMocks
	private AuthService authService;
	
	@Mock
	private JwtService jwtService;
	
	@Mock
	private ResponsibleRepository responsibleRepository;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	private Responsible responsible;
    private LoginRequest loginRequest;
    
    @BeforeEach
    void setUp() {
        responsible = new Responsible();
        responsible.setEmail("admin@email.com");
        responsible.setPassword("senhaCriptografada");
        responsible.setRole("ADMIN");
        responsible.setName("Admin");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("admin@email.com");
        loginRequest.setPassword("123456");
    }

    @Test
    void deveRealizarLoginComSucesso() {
        when(responsibleRepository.findByEmail("admin@email.com")).thenReturn(responsible);
        when(passwordEncoder.matches("123456", "senhaCriptografada")).thenReturn(true);
        when(jwtService.gerarToken(responsible)).thenReturn("token.gerado.aqui");

        Map<String, Object> resultado = authService.login(loginRequest);

        assertNotNull(resultado);
        assertEquals("token.gerado.aqui", resultado.get("token"));
        assertEquals("admin@email.com", resultado.get("name") != null ? responsible.getEmail() : null);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        when(responsibleRepository.findByEmail("admin@email.com")).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.login(loginRequest);
        });

        assertEquals("Usuário não encontrado", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoSenhaInvalida() {
        when(responsibleRepository.findByEmail("admin@email.com")).thenReturn(responsible);
        when(passwordEncoder.matches("123456", "senhaCriptografada")).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.login(loginRequest);
        });

        assertEquals("Senha inválida", exception.getMessage());
    }
}
