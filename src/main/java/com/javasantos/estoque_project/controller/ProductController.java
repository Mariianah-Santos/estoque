package com.javasantos.estoque_project.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javasantos.estoque_project.DTO.ProductDTO;
import com.javasantos.estoque_project.DTO.ProductRequestDTO;
import com.javasantos.estoque_project.infrastructure.entity.Product;
import com.javasantos.estoque_project.services.ProductService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
	
	 private final ProductService productService;
	
	// vai criar dentro da tabela essas informações
	 
	 @PostMapping
	 public ResponseEntity<?> createProduct(@RequestBody ProductRequestDTO dto) {
	     try {
	         Product savedProduct = productService.addProduct(dto);
	         return ResponseEntity.ok(new ProductDTO(savedProduct));
	     } catch (RuntimeException e) {
	         return ResponseEntity.badRequest().body(e.getMessage());
	     }
	 }
	
	// vai exibir toda a lista de produtos
	/*@GetMapping
	public ResponseEntity<?> getAllProductsController() {
	    try {
	        List<Product> products = productService.getAllProductsService();	        
	        return ResponseEntity.ok(products);
	        
	    } catch (Exception e) {
	    	return ResponseEntity.badRequest().body(e.getMessage());
	    	
	    }
	} */
	
	@GetMapping
	public ResponseEntity<?> getAllProductsController() {
	    try {
	        List<Product> products = productService.getAllProductsService();
	        List<ProductDTO> dtos = products.stream().map(ProductDTO::new).toList();
	        
	        return ResponseEntity.ok(dtos);
	        
	    } catch (Exception e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	    }
	}
	

	// e vai exibir pelo id o produto pedido
/*	@GetMapping("/{id}")
	public ResponseEntity<?> getProductById(@PathVariable Long id) {
		
	    try {
	        Product product = productService.findById(id); 
	        return ResponseEntity.ok(product);
	        
	    } catch (Exception e) {
	    	return ResponseEntity.badRequest().body(e.getMessage());
	    }
	    
	}*/
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getProductById(@PathVariable Long id) {
	    try {
	        Product product = productService.findById(id);
	        return ResponseEntity.ok(new ProductDTO(product));
	    } catch (Exception e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	    }
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProductByIdController(@PathVariable Long id) {
		
		try {
			productService.deleteProductById(id);
			return ResponseEntity.noContent().build();
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> editProductController(@PathVariable Long id, @RequestBody ProductRequestDTO dto) {
	    try {
	        Product productUpdate = productService.editProduceService(id, dto);
	        return ResponseEntity.ok(new ProductDTO(productUpdate));
	    } catch (Exception e) {
	    	return ResponseEntity.badRequest().body(e.getMessage());
	    }
	}
	
	/*@PutMapping("/{id}")
	public ResponseEntity<?> editProductController(@PathVariable Long id, @RequestBody Product product) {
		
		try {
			Product productUpdate = productService.editProduceService(id, product);
			return ResponseEntity.ok("Produto editado com sucesso!");
			
		} catch (Exception e) {
			return ResponseEntity.status(404).body("EDIÇÃOs CANCELADO! O Produto não encontrado com o ID: " + id);
		}
	} */
	
	
	
	
	/*@GetMapping
	public ResponseEntity<List<Product>> getAllProducts() {
	
		List<Product> products = productService.getAllProducts();
		return ResponseEntity.ok(products);

	} */
	
	

}
