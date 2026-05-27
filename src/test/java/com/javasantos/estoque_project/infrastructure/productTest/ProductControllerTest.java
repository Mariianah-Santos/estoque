package com.javasantos.estoque_project.infrastructure.productTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.javasantos.estoque_project.DTO.ProductRequestDTO;
import com.javasantos.estoque_project.controller.ProductController;
import com.javasantos.estoque_project.infrastructure.entity.Category;
import com.javasantos.estoque_project.infrastructure.entity.Product;
import com.javasantos.estoque_project.infrastructure.entity.Responsible;
import com.javasantos.estoque_project.services.ProductService;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

	 @Mock
	    private ProductService productService;

	    @InjectMocks
	    private ProductController productController;

	    private Product product;
	    private ProductRequestDTO dto;

	    @BeforeEach
	    void setUp() {
	        Category category = new Category();
	        category.setId(1L);
	        category.setName("Eletrônicos");

	        Responsible responsible = new Responsible();
	        responsible.setId(1L);
	        responsible.setName("Admin");

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
	        when(productService.addProduct(dto)).thenReturn(product);

	        ResponseEntity<?> response = productController.createProduct(dto);

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertNotNull(response.getBody());
	    }

	    @Test
	    void deveRetornar400AoCriarProdutoComDadosInvalidos() {
	        when(productService.addProduct(dto))
	            .thenThrow(new RuntimeException("Nome é obrigatório"));

	        ResponseEntity<?> response = productController.createProduct(dto);

	        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	        assertEquals("Nome é obrigatório", response.getBody());
	    }

	    // ==================== GET ALL ====================

	    @Test
	    void deveRetornarListaDeProdutos() {
	        when(productService.getAllProductsService()).thenReturn(List.of(product));

	        ResponseEntity<?> response = productController.getAllProductsController();

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertNotNull(response.getBody());
	    }

	    @Test
	    void deveRetornar400QuandoListaVazia() {
	        when(productService.getAllProductsService())
	            .thenThrow(new RuntimeException("A lista de produto esta vazia"));

	        ResponseEntity<?> response = productController.getAllProductsController();

	        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	        assertEquals("A lista de produto esta vazia", response.getBody());
	    }

	    // ==================== GET BY ID ====================

	    @Test
	    void deveRetornarProdutoPorId() {
	        when(productService.findById(1L)).thenReturn(product);

	        ResponseEntity<?> response = productController.getProductById(1L);

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertNotNull(response.getBody());
	    }

	    @Test
	    void deveRetornar400QuandoProdutoNaoEncontrado() {
	        when(productService.findById(99L))
	            .thenThrow(new RuntimeException("Produto inexistente"));

	        ResponseEntity<?> response = productController.getProductById(99L);

	        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	        assertEquals("Produto inexistente", response.getBody());
	    }

	    // ==================== DELETE ====================

	    @Test
	    void deveDeletarProdutoComSucesso() {
	        doNothing().when(productService).deleteProductById(1L);

	        ResponseEntity<?> response = productController.deleteProductByIdController(1L);

	        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	        verify(productService, times(1)).deleteProductById(1L);
	    }

	    @Test
	    void deveRetornar400AoDeletarIdInexistente() {
	        doThrow(new RuntimeException("ID não encontrado. Operação de Deletar cancelada!"))
	            .when(productService).deleteProductById(99L);

	        ResponseEntity<?> response = productController.deleteProductByIdController(99L);

	        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	        assertEquals("ID não encontrado. Operação de Deletar cancelada!",
	            ((Map<?, ?>) response.getBody()).get("message"));
	    }

	    // ==================== EDIT ====================

	    @Test
	    void deveEditarProdutoComSucesso() {
	        when(productService.editProduceService(1L, dto)).thenReturn(product);

	        ResponseEntity<?> response = productController.editProductController(1L, dto);

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertNotNull(response.getBody());
	    }

	    @Test
	    void deveRetornar400AoEditarProdutoInexistente() {
	        when(productService.editProduceService(99L, dto))
	            .thenThrow(new RuntimeException("Produto inexistente"));

	        ResponseEntity<?> response = productController.editProductController(99L, dto);

	        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	        assertEquals("Produto inexistente", response.getBody());
	    }
}
