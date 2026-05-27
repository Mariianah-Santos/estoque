package com.javasantos.estoque_project.infrastructure.productTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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

import com.javasantos.estoque_project.DTO.ProductRequestDTO;
import com.javasantos.estoque_project.infrastructure.entity.Category;
import com.javasantos.estoque_project.infrastructure.entity.Product;
import com.javasantos.estoque_project.infrastructure.entity.Responsible;
import com.javasantos.estoque_project.infrastructure.respository.CategoryRepository;
import com.javasantos.estoque_project.infrastructure.respository.ProductRepository;
import com.javasantos.estoque_project.infrastructure.respository.ResponsibleRepository;
import com.javasantos.estoque_project.services.ProductService;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {


    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ResponsibleRepository responsibleRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductRequestDTO dto;
    private Category category;
    private Responsible responsible;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Eletrônicos");

        responsible = new Responsible();
        responsible.setId(1L);
        responsible.setName("Admin");
        responsible.setEmail("admin@email.com");

        product = new Product();
        product.setId(1L);
        product.setName("Notebook");
        product.setPrice(3000.0);
        product.setQuantity(10);
        product.setCategory(category);
        product.setResponsible(responsible);
        product.setActive(true);

        dto = new ProductRequestDTO();
        dto.setName("Notebook");
        dto.setPrice(3000.0);
        dto.setQuantity(10);
        dto.setCategoryId(1L);
        dto.setResponsibleId(1L);
    }

    // ==================== CREATE ====================

    @Test
    void deveCriarProdutoComSucesso() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(responsibleRepository.findById(1L)).thenReturn(Optional.of(responsible));
        when(productRepository.existsByCode(anyString())).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product resultado = productService.addProduct(dto);

        assertNotNull(resultado);
        assertEquals("Notebook", resultado.getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void deveLancarExcecaoQuandoNomeVazio() {
        dto.setName("");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.addProduct(dto);
        });

        assertEquals("Nome é obrigatório", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoPrecoNegativo() {
        dto.setPrice(-1.0);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.addProduct(dto);
        });

        assertEquals("Preço não pode ser negativo", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoCategoriaNaoEncontrada() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.addProduct(dto);
        });

        assertEquals("Categoria não existe", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoResponsavelNaoEncontrado() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(responsibleRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.addProduct(dto);
        });

        assertEquals("Responsável não encontrado", exception.getMessage());
    }

    // ==================== GET ALL ====================

    @Test
    void deveRetornarListaDeProdutos() {
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<Product> lista = productService.getAllProductsService();

        assertNotNull(lista);
        assertFalse(lista.isEmpty());
    }

    @Test
    void deveLancarExcecaoQuandoListaVazia() {
        when(productRepository.findAll()).thenReturn(List.of());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.getAllProductsService();
        });

        assertEquals("A lista de produto esta vazia", exception.getMessage());
    }

    // ==================== GET BY ID ====================

    @Test
    void deveRetornarProdutoPorId() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product resultado = productService.findById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoEncontrado() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.findById(99L);
        });

        assertEquals("Produto inexistente", exception.getMessage());
    }

    // ==================== DELETE ====================

    @Test
    void deveDeletarProdutoComSucesso() {
        when(productRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> productService.deleteProductById(1L));
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void deveLancarExcecaoAoDeletarIdInexistente() {
        when(productRepository.existsById(99L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.deleteProductById(99L);
        });

        assertEquals("ID não encontrado. Operação de Deletar cancelada!", exception.getMessage());
    }

    // ==================== EDIT ====================

    @Test
    void deveEditarProdutoComSucesso() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(responsibleRepository.findById(1L)).thenReturn(Optional.of(responsible));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product resultado = productService.editProduceService(1L, dto);

        assertNotNull(resultado);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void deveLancarExcecaoAoEditarProdutoInexistente() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.editProduceService(99L, dto);
        });

        assertEquals("Produto inexistente", exception.getMessage());
    }
}
