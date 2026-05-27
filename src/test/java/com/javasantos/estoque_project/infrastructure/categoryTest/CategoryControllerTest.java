package com.javasantos.estoque_project.infrastructure.categoryTest;

import com.javasantos.estoque_project.controller.CategoryController;
import com.javasantos.estoque_project.infrastructure.entity.Category;
import com.javasantos.estoque_project.services.CategoryService;
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
public class CategoryControllerTest {


    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Eletrônicos");
    }

    // ==================== CREATE ====================

    @Test
    void deveCriarCategoriaComSucesso() {
        when(categoryService.createCategoryeService(category)).thenReturn(category);

        ResponseEntity<?> response = categoryController.addCategory(category);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(category, response.getBody());
    }

    @Test
    void deveRetornar400AoCriarCategoriaComNomeVazio() {
        when(categoryService.createCategoryeService(category))
            .thenThrow(new RuntimeException("Por favor digite um nome para a categoria"));

        ResponseEntity<?> response = categoryController.addCategory(category);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Por favor digite um nome para a categoria", response.getBody());
    }

    // ==================== GET ALL ====================

    @Test
    void deveRetornarListaDeCategorias() {
        when(categoryService.getAllCategory()).thenReturn(List.of(category));

        ResponseEntity<?> response = categoryController.getCategoryController();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void deveRetornar400QuandoListaVazia() {
        when(categoryService.getAllCategory())
            .thenThrow(new RuntimeException("A lista de categoria esta vazia"));

        ResponseEntity<?> response = categoryController.getCategoryController();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("A lista de categoria esta vazia", response.getBody());
    }

    // ==================== GET BY ID ====================

    @Test
    void deveRetornarCategoriaPorId() {
        when(categoryService.getCategoryById(1L)).thenReturn(category);

        ResponseEntity<?> response = categoryController.getCategoryByIdController(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(category, response.getBody());
    }

    @Test
    void deveRetornar400QuandoCategoriaInexistente() {
        when(categoryService.getCategoryById(99L))
            .thenThrow(new RuntimeException("Categoria inexistente"));

        ResponseEntity<?> response = categoryController.getCategoryByIdController(99L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Categoria inexistente", response.getBody());
    }

    // ==================== DELETE ====================

    @Test
    void deveDeletarCategoriaComSucesso() {
        doNothing().when(categoryService).deleteCategorySerive(1L);

        ResponseEntity<?> response = categoryController.deleteCategoryController(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(categoryService, times(1)).deleteCategorySerive(1L);
    }

    @Test
    void deveRetornar400AoDeletarIdInexistente() {
        doThrow(new RuntimeException("O id não existe para ser deletado"))
            .when(categoryService).deleteCategorySerive(99L);

        ResponseEntity<?> response = categoryController.deleteCategoryController(99L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("O id não existe para ser deletado", response.getBody());
    }

    @Test
    void deveRetornar400AoDeletarCategoriaAssociadaAProduto() {
        doThrow(new RuntimeException("A categoria não pode ser deletada. Por que esta associada a algum produto"))
            .when(categoryService).deleteCategorySerive(1L);

        ResponseEntity<?> response = categoryController.deleteCategoryController(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("A categoria não pode ser deletada. Por que esta associada a algum produto", response.getBody());
    }

    // ==================== EDIT ====================

    @Test
    void deveEditarCategoriaComSucesso() {
        when(categoryService.editCategoryService(1L, category)).thenReturn(category);

        ResponseEntity<?> response = categoryController.editCategoryController(1L, category);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(category, response.getBody());
    }

    @Test
    void deveRetornar400AoEditarIdInexistente() {
        when(categoryService.editCategoryService(99L, category))
            .thenThrow(new RuntimeException("O id para editar não existe"));

        ResponseEntity<?> response = categoryController.editCategoryController(99L, category);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("O id para editar não existe", response.getBody());
    }

    @Test
    void deveRetornar400AoEditarComNomeVazio() {
        when(categoryService.editCategoryService(1L, category))
            .thenThrow(new RuntimeException("Nome não pode ser vazio"));

        ResponseEntity<?> response = categoryController.editCategoryController(1L, category);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Nome não pode ser vazio", response.getBody());
    }
}
