package com.javasantos.estoque_project.infrastructure.solicitationTest;

import com.javasantos.estoque_project.controller.SolicitationController;
import com.javasantos.estoque_project.infrastructure.entity.Solicitation;
import com.javasantos.estoque_project.infrastructure.enuns.MovementType;
import com.javasantos.estoque_project.services.SolicitationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SolicitationControllerTest {

    @Mock
    private SolicitationService solicitationService;

    @InjectMocks
    private SolicitationController solicitationController;

    private Solicitation solicitation;

    @BeforeEach
    void setUp() {
        solicitation = new Solicitation();
        solicitation.setId(1L);
        solicitation.setQuantity(2);
        solicitation.setStatus(MovementType.PENDING);
    }

    // ==================== CREATE ====================

    @Test
    void deveCriarSolicitacaoComSucesso() {
        when(solicitationService.create(solicitation)).thenReturn(solicitation);

        ResponseEntity<Solicitation> response = solicitationController.create(solicitation);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(MovementType.PENDING, response.getBody().getStatus());
    }

    // ==================== FIND ALL PENDING ====================

    @Test
    void deveRetornarSolicitacoesPendentes() {
        when(solicitationService.findAllPending()).thenReturn(List.of(solicitation));

        ResponseEntity<List<Solicitation>> response = solicitationController.findAllPending();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    // ==================== FIND ALL ====================

    @Test
    void deveRetornarTodasAsSolicitacoes() {
        when(solicitationService.findAll()).thenReturn(List.of(solicitation));

        ResponseEntity<List<Solicitation>> response = solicitationController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    // ==================== UPDATE STATUS ====================

    @Test
    void deveAprovarSolicitacaoComSucesso() {
        solicitation.setStatus(MovementType.APPROVED);
        when(solicitationService.updateStatus(1L, MovementType.APPROVED)).thenReturn(solicitation);

        ResponseEntity<Solicitation> response = solicitationController.updateStatus(1L, MovementType.APPROVED);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MovementType.APPROVED, response.getBody().getStatus());
    }

    @Test
    void deveRecusarSolicitacaoComSucesso() {
        solicitation.setStatus(MovementType.REJECTED);
        when(solicitationService.updateStatus(1L, MovementType.REJECTED)).thenReturn(solicitation);

        ResponseEntity<Solicitation> response = solicitationController.updateStatus(1L, MovementType.REJECTED);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MovementType.REJECTED, response.getBody().getStatus());
    }

    // ==================== FIND BY RESPONSIBLE ====================

    @Test
    void deveRetornarSolicitacoesPorResponsavel() {
        when(solicitationService.findByResponsible(1L)).thenReturn(List.of(solicitation));

        ResponseEntity<List<Solicitation>> response = solicitationController.findByResponsible(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void deveRetornarListaVaziaQuandoResponsavelSemSolicitacoes() {
        when(solicitationService.findByResponsible(99L)).thenReturn(List.of());

        ResponseEntity<List<Solicitation>> response = solicitationController.findByResponsible(99L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }
}
