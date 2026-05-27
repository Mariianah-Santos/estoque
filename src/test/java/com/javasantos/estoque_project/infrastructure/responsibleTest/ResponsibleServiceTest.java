package com.javasantos.estoque_project.infrastructure.responsibleTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.javasantos.estoque_project.infrastructure.entity.Responsible;
import com.javasantos.estoque_project.infrastructure.respository.ProductRepository;
import com.javasantos.estoque_project.infrastructure.respository.ResponsibleRepository;
import com.javasantos.estoque_project.services.ResponsibleService;

@ExtendWith(MockitoExtension.class)
public class ResponsibleServiceTest {


    @Mock
    private ResponsibleRepository responsibleRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ResponsibleService responsibleService;

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
        when(responsibleRepository.existsByEmail(responsible.getEmail())).thenReturn(false);
        when(passwordEncoder.encode("123456")).thenReturn("senhaCriptografada");
        when(responsibleRepository.save(responsible)).thenReturn(responsible);

        Responsible resultado = responsibleService.createResponsibleService(responsible);

        assertNotNull(resultado);
        assertEquals("admin@email.com", resultado.getEmail());
        verify(responsibleRepository, times(1)).save(responsible);
    }

    @Test
    void deveLancarExcecaoQuandoNomeVazio() {
        responsible.setName("");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            responsibleService.createResponsibleService(responsible);
        });

        assertEquals("Por favor digite um Nome para o responsável", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoEmailJaCadastrado() {
        when(responsibleRepository.existsByEmail(responsible.getEmail())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            responsibleService.createResponsibleService(responsible);
        });

        assertEquals("Este email já está cadastrado", exception.getMessage());
    }

    // ==================== GET ALL ====================

    @Test
    void deveRetornarListaDeResponsaveis() {
        when(responsibleRepository.findAll()).thenReturn(List.of(responsible));

        List<Responsible> lista = responsibleService.getAllResponsible();

        assertNotNull(lista);
        assertFalse(lista.isEmpty());
    }

    @Test
    void deveLancarExcecaoQuandoListaVazia() {
        when(responsibleRepository.findAll()).thenReturn(List.of());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            responsibleService.getAllResponsible();
        });

        assertEquals("Lista de responsável esta vazia", exception.getMessage());
    }

    // ==================== GET BY ID ====================

    @Test
    void deveRetornarResponsavelPorId() {
        when(responsibleRepository.findById(1L)).thenReturn(Optional.of(responsible));

        Responsible resultado = responsibleService.getResponsibleById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void deveLancarExcecaoQuandoIdNaoEncontrado() {
        when(responsibleRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            responsibleService.getResponsibleById(99L);
        });

        assertEquals("Responsável inexistente", exception.getMessage());
    }

    // ==================== DELETE ====================

    @Test
    void deveDeletarResponsavelComSucesso() {
        when(responsibleRepository.existsById(1L)).thenReturn(true);
        when(productRepository.existsByResponsibleId(1L)).thenReturn(false);

        assertDoesNotThrow(() -> responsibleService.deleteResponsibleById(1L));
        verify(responsibleRepository, times(1)).deleteById(1L);
    }

    @Test
    void deveLancarExcecaoAoDeletarIdInexistente() {
        when(responsibleRepository.existsById(99L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            responsibleService.deleteResponsibleById(99L);
        });

        assertEquals("O id para deletar o responsável não existe", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoAoDeletarResponsavelAssociadoAProduto() {
        when(responsibleRepository.existsById(1L)).thenReturn(true);
        when(productRepository.existsByResponsibleId(1L)).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            responsibleService.deleteResponsibleById(1L);
        });

        assertEquals("O responsável não pode ser deletado. Esta associado a algum produto", exception.getMessage());
    }

    // ==================== EDIT ====================

    @Test
    void deveEditarResponsavelComSucesso() {
        when(responsibleRepository.existsById(1L)).thenReturn(true);
        when(responsibleRepository.findByEmail(responsible.getEmail())).thenReturn(responsible);
        when(responsibleRepository.findById(1L)).thenReturn(Optional.of(responsible));
        when(responsibleRepository.save(responsible)).thenReturn(responsible);

        Responsible resultado = responsibleService.editResponsibleById(1L, responsible);

        assertNotNull(resultado);
        verify(responsibleRepository, times(1)).save(responsible);
    }

    @Test
    void deveLancarExcecaoAoEditarIdInexistente() {
        when(responsibleRepository.existsById(99L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            responsibleService.editResponsibleById(99L, responsible);
        });

        assertEquals("O id para editar o responsável não existe", exception.getMessage());
    }
}
