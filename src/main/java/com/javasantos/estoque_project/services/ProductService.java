package com.javasantos.estoque_project.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.javasantos.estoque_project.DTO.ProductRequestDTO;
import com.javasantos.estoque_project.infrastructure.entity.Category;
import com.javasantos.estoque_project.infrastructure.entity.Product;
import com.javasantos.estoque_project.infrastructure.entity.Responsible;
import com.javasantos.estoque_project.infrastructure.respository.CategoryRepository;
import com.javasantos.estoque_project.infrastructure.respository.ProductRepository;
import com.javasantos.estoque_project.infrastructure.respository.ResponsibleRepository;

import lombok.RequiredArgsConstructor;

@Service // vai ser do tipo serviço
@RequiredArgsConstructor // vai criar o construtor
public class ProductService {

	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	private final ResponsibleRepository responsibleRepository;
	
	
	public Product addProduct(ProductRequestDTO dto) {

	    validateProduct(dto);

	    Product product = new Product();

	    product.setName(dto.getName());
	    product.setPrice(dto.getPrice());
	    product.setQuantity(dto.getQuantity() == null ? 0 : dto.getQuantity());

	    product.setCategory(findCategory(dto.getCategoryId()));
	    product.setResponsible(findResponsible(dto.getResponsibleId()));

	    product.setCode(generateUniqueCode());

	    product.setActive(true);
	    product.setDateCreate(LocalDateTime.now());

	    return productRepository.save(product);
	}
	
/*public Product addProduct(ProductRequestDTO dto) {
		
	    Product product = new Product();

	    // nome
	    if (dto.getName() == null || dto.getName().isBlank()) {
	        throw new RuntimeException("O campo nome é obrigatório");
	    }
	    product.setName(dto.getName());

	    // responsável
	    if (dto.getResponsibleId() == null) {
	        throw new RuntimeException("O responsável é obrigatório");
	    }

	    Responsible responsible = responsibleRepository
	            .findById(dto.getResponsibleId())
	            .orElseThrow(() -> new RuntimeException("Responsável não encontrado"));

	    product.setResponsible(responsible);

	    //  categoria
	    if (dto.getCategoryId() == null) {
	        throw new RuntimeException("Categoria é obrigatória");
	    }

	    Category category = categoryRepository
	            .findById(dto.getCategoryId())
	            .orElseThrow(() -> new RuntimeException("Categoria não existe"));

	    product.setCategory(category);

	    // preço
	    if (dto.getPrice() == null) {
	        throw new RuntimeException("O campo preço é obrigatório");
	    }

	    if (dto.getPrice() < 0) {
	        throw new RuntimeException("O preço não pode ser negativo");
	    }

	    product.setPrice(dto.getPrice());

	    // quantidade
	    if (dto.getQuantity() == null) {
	        product.setQuantity(0);
	    } else {
	        product.setQuantity(dto.getQuantity());
	    }

	    // código automático (com proteção contra duplicado)
	    String code;

	    do {
	        code = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
	    } while (productRepository.existsByCode(code));

	    product.setCode(code);

	    // segurança extra (quase nunca vai cair aqui)
	    if (productRepository.existsByCode(product.getCode())) {
	        throw new RuntimeException("Erro ao gerar código do produto. Tente novamente.");
	    }

	    // padrão do sistema
	    product.setActive(true);
	    product.setDateCreate(LocalDateTime.now());

	    return productRepository.save(product);
	} */
	
	// adicionar na lista da tabela
	/* public Product addProduct(Product product) {

	    if (product.getId() != null && productRepository.existsById(product.getId())) {
	        throw new RuntimeException("Este ID já existe no sistema!");
	    }

	    if (product.getCode() != null && productRepository.existsByCode(product.getCode())) {
	        throw new RuntimeException("Esse código já existe");
	    }

	    if (product.getName() == null || product.getName().isEmpty()) {
	        throw new RuntimeException("O campo nome é obrigatorio");
	    }

	   
	    if (product.getResponsible() == null || product.getResponsible().getId() == null) {
	        throw new RuntimeException("O responsável é obrigatorio");
	    }

	    Responsible responsible = responsibleRepository
	            .findById(product.getResponsible().getId())
	            .orElseThrow(() -> new RuntimeException("Responsável não encontrado"));

	    product.setResponsible(responsible); // ⭐ CORREÇÃO PRINCIPAL

	  
	    if (product.getCategory() == null || product.getCategory().getId() == null) {
	        throw new RuntimeException("Categoria é obrigatoria");
	    }

	    Category category = categoryRepository
	            .findById(product.getCategory().getId())
	            .orElseThrow(() -> new RuntimeException("Categoria não existe"));

	    product.setCategory(category); 

	    if (product.getPrice() == null) {
	        throw new RuntimeException("O campo Preço é obrigatorio");
	    }

	    if (product.getPrice() < 0) {
	        throw new RuntimeException("O preço não pode ser negativo");
	    }

	    if (product.getQuantity() == null) {
	        product.setQuantity(0);
	    }

	    // 🔒 mantém seu código como está
	    String randomCode = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
	    product.setCode(randomCode);

	    product.setActive(true);
	    product.setDateCreate(LocalDateTime.now());

	    return productRepository.save(product);
	} */
	
	// pegar toda a lista
	public List<Product> getAllProductsService() {
		
		List<Product> listProduct = productRepository.findAll();
		
		if (listProduct.isEmpty()) {
			throw new RuntimeException("A lista de produto esta vazia");
		}
		
		return productRepository.findAll();
	}
	
	// pegar o produto pelo id
	/*public Optional<Product> getProductById(Long id) {
		return productRepository.findById(id);
	} */
	
	// pegar o produto pelo id
	public Product findById(Long id) {
	    return productRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Produto inexistente"));
	}
	
	// deletar o produto
	public void deleteProductById(Long id) {
		
		if (!productRepository.existsById(id)) {
			throw new RuntimeException("ID não encontrado. Operação de Deletar cancelada!");
			
		}
		
		productRepository.deleteById(id); 
		
	}
	
	public Product editProduceService(Long id, ProductRequestDTO dto) {

	    Product product = findById(id);

	    validateProduct(dto);

	    product.setName(dto.getName());
	    product.setPrice(dto.getPrice());
	    product.setQuantity(dto.getQuantity());

	    product.setCategory(findCategory(dto.getCategoryId()));
	    product.setResponsible(findResponsible(dto.getResponsibleId()));

	    product.setModifiedData(LocalDateTime.now());

	    return productRepository.save(product);
	}
	
	/*public Product editProduceService(Long id, ProductRequestDTO dto) {

	    Product productUpdate = productRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("ID " + id + " não encontrado!"));

	    productUpdate.setName(dto.getName());
	    productUpdate.setPrice(dto.getPrice());

	    Category category = categoryRepository.findById(dto.getCategoryId())
	            .orElseThrow(() -> new RuntimeException("Categoria não existe"));

	    productUpdate.setCategory(category);

	    Responsible responsible = responsibleRepository.findById(dto.getResponsibleId())
	            .orElseThrow(() -> new RuntimeException("Responsável não encontrado"));

	    productUpdate.setResponsible(responsible);

	    productUpdate.setQuantity(dto.getQuantity());
	    productUpdate.setModifiedData(LocalDateTime.now());

	    return productRepository.save(productUpdate);
	}*/
	
	private void validateProduct(ProductRequestDTO dto) {

	    if (dto.getName() == null || dto.getName().isBlank()) {
	        throw new RuntimeException("Nome é obrigatório");
	    }

	    if (dto.getPrice() == null) {
	        throw new RuntimeException("Preço é obrigatório");
	    }

	    if (dto.getPrice() < 0) {
	        throw new RuntimeException("Preço não pode ser negativo");
	    }

	    if (dto.getCategoryId() == null) {
	        throw new RuntimeException("Categoria é obrigatória");
	    }

	    if (dto.getResponsibleId() == null) {
	        throw new RuntimeException("Responsável é obrigatório");
	    }
	    

	}
	
	private Category findCategory(Long id) {
	    return categoryRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Categoria não existe"));
	}
	
	private Responsible findResponsible(Long id) {
	    return responsibleRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Responsável não encontrado"));
	}
	
	private String generateUniqueCode() {
	    String code;

	    do {
	        code = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
	    } while (productRepository.existsByCode(code));

	    return code;
	}
	
	/* public Product editProduceService(Long id, Product product) {
		
		Product productUpdate = productRepository.findById(id)
		        .orElseThrow(() -> new RuntimeException("ID " + id + " não encontrado!"));
		
		productUpdate.setName(product.getName());
		productUpdate.setPrice(product.getPrice());
		productUpdate.setCategory(product.getCategory());
		productUpdate.setQuantity(product.getQuantity());
		productUpdate.setResponsible(product.getResponsible());
		productUpdate.setModifiedData(LocalDateTime.now());
		
		return productRepository.save(productUpdate);
	} */
	
	/* public Product editProduceService(Long id, Product product) {
		
		if (!productRepository.existsById(id)) {
			throw new RuntimeException("ID não encontrado. Operação de Editar cancelada!");
			
		} 
		
		Product productUpdate = productRepository.findById(id).get();
		
		productUpdate.setName(product.getName());
		productUpdate.setPrice(product.getPrice());
		productUpdate.setCategory(product.getCategory());
		productUpdate.setQuantity(product.getQuantity());
		productUpdate.setResponsible(product.getResponsible());
		productUpdate.setModifiedData(LocalDateTime.now());
		
		return productRepository.save(productUpdate);
	} */
}
