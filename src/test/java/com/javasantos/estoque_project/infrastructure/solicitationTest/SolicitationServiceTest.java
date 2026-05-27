package com.javasantos.estoque_project.infrastructure.solicitationTest;

import com.javasantos.estoque_project.infrastructure.entity.Solicitation;
import com.javasantos.estoque_project.infrastructure.enuns.MovementType;
import com.javasantos.estoque_project.infrastructure.respository.SolicitationRepository;
import com.javasantos.estoque_project.services.SolicitationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SolicitationServiceTest {

		 @Mock
		 private SolicitationRepository solicitationRepository;
	
		 @InjectMocks
		 private SolicitationService solicitationService;
	
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
	        when(solicitationRepository.save(any(Solicitation.class))).thenReturn(solicitation);

	        Solicitation resultado = solicitationService.create(solicitation);

	        assertNotNull(resultado);
	        assertEquals(MovementType.PENDING, resultado.getStatus());
	        verify(solicitationRepository, times(1)).save(solicitation);
	    }

	    // ==================== FIND ALL PENDING ====================

	    @Test
	    void deveRetornarSolicitacoesPendentes() {
	        when(solicitationRepository.findByStatus(MovementType.PENDING))
	            .thenReturn(List.of(solicitation));

	        List<Solicitation> lista = solicitationService.findAllPending();

	        assertNotNull(lista);
	        assertFalse(lista.isEmpty());
	        assertEquals(MovementType.PENDING, lista.get(0).getStatus());
	    }

	    @Test
	    void deveRetornarListaVaziaQuandoNaoHaPendentes() {
	        when(solicitationRepository.findByStatus(MovementType.PENDING))
	            .thenReturn(List.of());

	        List<Solicitation> lista = solicitationService.findAllPending();

	        assertTrue(lista.isEmpty());
	    }

	    // ==================== UPDATE STATUS ====================

	    @Test
	    void deveAprovarSolicitacaoComSucesso() {
	        when(solicitationRepository.findById(1L)).thenReturn(Optional.of(solicitation));
	        when(solicitationRepository.save(solicitation)).thenReturn(solicitation);

	        Solicitation resultado = solicitationService.updateStatus(1L, MovementType.APPROVED);

	        assertNotNull(resultado);
	        assertEquals(MovementType.APPROVED, resultado.getStatus());
	        verify(solicitationRepository, times(1)).save(solicitation);
	    }

	    @Test
	    void deveRecusarSolicitacaoComSucesso() {
	        when(solicitationRepository.findById(1L)).thenReturn(Optional.of(solicitation));
	        when(solicitationRepository.save(solicitation)).thenReturn(solicitation);

	        Solicitation resultado = solicitationService.updateStatus(1L, MovementType.REJECTED);

	        assertNotNull(resultado);
	        assertEquals(MovementType.REJECTED, resultado.getStatus());
	    }

	    @Test
	    void deveLancarExcecaoQuandoSolicitacaoNaoEncontrada() {
	        when(solicitationRepository.findById(99L)).thenReturn(Optional.empty());

	        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
	            solicitationService.updateStatus(99L, MovementType.APPROVED);
	        });

	        assertEquals("Solicitação não encontrada", exception.getMessage());
	    }

	    // ==================== FIND BY RESPONSIBLE ====================

	    @Test
	    void deveRetornarSolicitacoesPorResponsavel() {
	        when(solicitationRepository.findByResponsibleId(1L)).thenReturn(List.of(solicitation));

	        List<Solicitation> lista = solicitationService.findByResponsible(1L);

	        assertNotNull(lista);
	        assertFalse(lista.isEmpty());
	    }

	    @Test
	    void deveRetornarListaVaziaQuandoResponsavelSemSolicitacoes() {
	        when(solicitationRepository.findByResponsibleId(99L)).thenReturn(List.of());

	        List<Solicitation> lista = solicitationService.findByResponsible(99L);

	        assertTrue(lista.isEmpty());
	    }

	    // ==================== FIND ALL ====================

	    @Test
	    void deveRetornarTodasAsSolicitacoes() {
	        when(solicitationRepository.findAll()).thenReturn(List.of(solicitation));

	        List<Solicitation> lista = solicitationService.findAll();

	        assertNotNull(lista);
	        assertFalse(lista.isEmpty());
	    }
}
