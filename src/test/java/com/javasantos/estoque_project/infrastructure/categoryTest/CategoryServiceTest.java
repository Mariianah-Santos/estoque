package com.javasantos.estoque_project.infrastructure.categoryTest;

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

import com.javasantos.estoque_project.infrastructure.entity.Category;
import com.javasantos.estoque_project.infrastructure.respository.CategoryRepository;
import com.javasantos.estoque_project.infrastructure.respository.ProductRepository;
import com.javasantos.estoque_project.services.CategoryService;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

	 @Mock
	    private CategoryRepository categoryRepository;

	    @Mock
	    private ProductRepository productRepository;

	    @InjectMocks
	    private CategoryService categoryService;

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
	        when(categoryRepository.save(category)).thenReturn(category);

	        Category resultado = categoryService.createCategoryeService(category);

	        assertNotNull(resultado);
	        assertEquals("Eletrônicos", resultado.getName());
	        verify(categoryRepository, times(1)).save(category);
	    }

	    @Test
	    void deveLancarExcecaoQuandoNomeVazio() {
	        category.setName("");

	        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
	            categoryService.createCategoryeService(category);
	        });

	        assertEquals("Por favor digite um nome para a categoria", exception.getMessage());
	    }

	    @Test
	    void deveLancarExcecaoQuandoIdJaExistente() {
	        when(categoryRepository.existsById(1L)).thenReturn(true);

	        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
	            categoryService.createCategoryeService(category);
	        });

	        assertEquals("Id já existente", exception.getMessage());
	    }

	    // ==================== GET ALL ====================

	    @Test
	    void deveRetornarListaDeCategorias() {
	        when(categoryRepository.findAll()).thenReturn(List.of(category));

	        List<Category> lista = categoryService.getAllCategory();

	        assertNotNull(lista);
	        assertFalse(lista.isEmpty());
	    }

	    @Test
	    void deveLancarExcecaoQuandoListaVazia() {
	        when(categoryRepository.findAll()).thenReturn(List.of());

	        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
	            categoryService.getAllCategory();
	        });

	        assertEquals("A lista de categoria esta vazia", exception.getMessage());
	    }

	    // ==================== GET BY ID ====================

	    @Test
	    void deveRetornarCategoriaPorId() {
	        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

	        Category resultado = categoryService.getCategoryById(1L);

	        assertNotNull(resultado);
	        assertEquals(1L, resultado.getId());
	    }

	    @Test
	    void deveLancarExcecaoQuandoCategoriaInexistente() {
	        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

	        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
	            categoryService.getCategoryById(99L);
	        });

	        assertEquals("Categoria inexistente", exception.getMessage());
	    }

	    // ==================== DELETE ====================

	    @Test
	    void deveDeletarCategoriaComSucesso() {
	        when(categoryRepository.existsById(1L)).thenReturn(true);
	        when(productRepository.existsByCategoryId(1L)).thenReturn(false);

	        assertDoesNotThrow(() -> categoryService.deleteCategorySerive(1L));
	        verify(categoryRepository, times(1)).deleteById(1L);
	    }

	    @Test
	    void deveLancarExcecaoAoDeletarIdInexistente() {
	        when(categoryRepository.existsById(99L)).thenReturn(false);

	        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
	            categoryService.deleteCategorySerive(99L);
	        });

	        assertEquals("O id não existe para ser deletado", exception.getMessage());
	    }

	    @Test
	    void deveLancarExcecaoAoDeletarCategoriaAssociadaAProduto() {
	        when(categoryRepository.existsById(1L)).thenReturn(true);
	        when(productRepository.existsByCategoryId(1L)).thenReturn(true);

	        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
	            categoryService.deleteCategorySerive(1L);
	        });

	        assertEquals("A categoria não pode ser deletada. Por que esta associada a algum produto", exception.getMessage());
	    }

	    // ==================== EDIT ====================

	    @Test
	    void deveEditarCategoriaComSucesso() {
	        when(categoryRepository.existsById(1L)).thenReturn(true);
	        when(categoryRepository.getReferenceById(1L)).thenReturn(category);
	        when(categoryRepository.save(category)).thenReturn(category);

	        Category resultado = categoryService.editCategoryService(1L, category);

	        assertNotNull(resultado);
	        verify(categoryRepository, times(1)).save(category);
	    }

	    @Test
	    void deveLancarExcecaoAoEditarIdInexistente() {
	        when(categoryRepository.existsById(99L)).thenReturn(false);

	        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
	            categoryService.editCategoryService(99L, category);
	        });

	        assertEquals("O id para editar não existe", exception.getMessage());
	    }

	    @Test
	    void deveLancarExcecaoAoEditarComNomeVazio() {
	        category.setName("");
	        when(categoryRepository.existsById(1L)).thenReturn(true);
	        when(categoryRepository.getReferenceById(1L)).thenReturn(category);

	        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
	            categoryService.editCategoryService(1L, category);
	        });

	        assertEquals("Nome não pode ser vazio", exception.getMessage());
	    }
}
