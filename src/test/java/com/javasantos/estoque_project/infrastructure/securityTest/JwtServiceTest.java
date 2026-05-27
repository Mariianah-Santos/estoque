package com.javasantos.estoque_project.infrastructure.securityTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.javasantos.estoque_project.infrastructure.entity.Responsible;
import com.javasantos.estoque_project.infrastructure.security.JwtService;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

	 private JwtService jwtService;
	    private Responsible responsible;

	    @BeforeEach
	    void setUp() {
	        jwtService = new JwtService();

	        responsible = new Responsible();
	        responsible.setEmail("admin@email.com");
	        responsible.setPassword("123456");
	        responsible.setRole("ADMIN");
	        responsible.setName("Admin");
	    }

	    @Test
	    void deveGerarTokenComSucesso() {
	        String token = jwtService.gerarToken(responsible);

	        assertNotNull(token);
	        assertFalse(token.isEmpty());
	    }

	    @Test
	    void deveExtrairEmailDoToken() {
	        String token = jwtService.gerarToken(responsible);
	        String email = jwtService.extrairEmail(token);

	        assertEquals("admin@email.com", email);
	    }

	    @Test
	    void deveExtrairRoleDoToken() {
	        String token = jwtService.gerarToken(responsible);
	        String role = jwtService.extrairRole(token);

	        assertEquals("ADMIN", role);
	    }

	    @Test
	    void deveValidarTokenValido() {
	        String token = jwtService.gerarToken(responsible);
	        boolean valido = jwtService.validarToken(token);

	        assertTrue(valido);
	    }

	    @Test
	    void deveRetornarFalsoParaTokenInvalido() {
	        boolean valido = jwtService.validarToken("token.invalido.aqui");

	        assertFalse(valido);
	    }
}
