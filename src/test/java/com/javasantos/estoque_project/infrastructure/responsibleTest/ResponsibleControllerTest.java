package com.javasantos.estoque_project.infrastructure.responsibleTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import com.javasantos.estoque_project.controller.ResponsibleController;
import com.javasantos.estoque_project.infrastructure.entity.Responsible;
import com.javasantos.estoque_project.services.ResponsibleService;
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
public class ResponsibleControllerTest {


    @Mock
    private ResponsibleService responsibleService;

    @InjectMocks
    private ResponsibleController responsibleController;

    private Responsible responsible;

    @BeforeEach
    void setUp() {
        responsible = new Responsible();
        responsible.setId(1L);
        responsible.setName("Admin");
        responsible.setEmail("admin@email.com");
        responsible.setPassword("123456");
        responsible.setRole("ADMIN");
        responsible.setActive(true);
    }

    // ==================== CREATE ====================

    @Test
    void deveCriarResponsavelComSucesso() {
        when(responsibleService.createResponsibleService(responsible)).thenReturn(responsible);

        ResponseEntity<?> response = responsibleController.addResponsibleController(responsible);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responsible, response.getBody());
    }

    @Test
    void deveRetornar400AoCriarResponsavelComDadosInvalidos() {
        when(responsibleService.createResponsibleService(responsible))
            .thenThrow(new RuntimeException("Por favor digite um Nome para o responsável"));

        ResponseEntity<?> response = responsibleController.addResponsibleController(responsible);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Por favor digite um Nome para o responsável", response.getBody());
    }

    // ==================== GET ALL ====================

    @Test
    void deveRetornarListaDeResponsaveis() {
        when(responsibleService.getAllResponsible()).thenReturn(List.of(responsible));

        ResponseEntity<?> response = responsibleController.getAllResponsibleController();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void deveRetornar400QuandoListaVazia() {
        when(responsibleService.getAllResponsible())
            .thenThrow(new RuntimeException("Lista de responsável esta vazia"));

        ResponseEntity<?> response = responsibleController.getAllResponsibleController();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Lista de responsável esta vazia", response.getBody());
    }

    // ==================== GET BY ID ====================

    @Test
    void deveRetornarResponsavelPorId() {
        when(responsibleService.getResponsibleById(1L)).thenReturn(responsible);

        ResponseEntity<?> response = responsibleController.getResponsibleByIdControlle(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responsible, response.getBody());
    }

    @Test
    void deveRetornar400QuandoIdNaoEncontrado() {
        when(responsibleService.getResponsibleById(99L))
            .thenThrow(new RuntimeException("Responsável inexistente"));

        ResponseEntity<?> response = responsibleController.getResponsibleByIdControlle(99L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Responsável inexistente", response.getBody());
    }

    // ==================== DELETE ====================

    @Test
    void deveDeletarResponsavelComSucesso() {
        doNothing().when(responsibleService).deleteResponsibleById(1L);

        ResponseEntity<?> response = responsibleController.deleteResponsibleById(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(responsibleService, times(1)).deleteResponsibleById(1L);
    }

    @Test
    void deveRetornar400AoDeletarIdInexistente() {
        doThrow(new RuntimeException("O id para deletar o responsável não existe"))
            .when(responsibleService).deleteResponsibleById(99L);

        ResponseEntity<?> response = responsibleController.deleteResponsibleById(99L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("O id para deletar o responsável não existe", response.getBody());
    }

    // ==================== EDIT ====================

    @Test
    void deveEditarResponsavelComSucesso() {
        when(responsibleService.editResponsibleById(1L, responsible)).thenReturn(responsible);

        ResponseEntity<?> response = responsibleController.editResponsibleById(1L, responsible);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responsible, response.getBody());
    }

    @Test
    void deveRetornar400AoEditarIdInexistente() {
        when(responsibleService.editResponsibleById(99L, responsible))
            .thenThrow(new RuntimeException("O id para editar o responsável não existe"));

        ResponseEntity<?> response = responsibleController.editResponsibleById(99L, responsible);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("O id para editar o responsável não existe", response.getBody());
    }
}
