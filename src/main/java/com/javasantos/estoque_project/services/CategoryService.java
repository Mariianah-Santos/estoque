package com.javasantos.estoque_project.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.javasantos.estoque_project.infrastructure.entity.Category;
import com.javasantos.estoque_project.infrastructure.respository.CategoryRepository;
import com.javasantos.estoque_project.infrastructure.respository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
	
	private final CategoryRepository categoryRepository;
	private final ProductRepository productRepository;
	
	//criar uma categoria nova
	public Category createCategoryeService(Category category) {
		
		if (category.getName() == null || category.getName().isBlank()) {
			throw new RuntimeException("Por favor digite um nome para a categoria");
		}
		
		if (category.getId() != null && categoryRepository.existsById(category.getId())) {
			throw new RuntimeException("Id já existente");
		}
		
		return categoryRepository.save(category);
	}
	
	// pegar todos da lista
	public List<Category> getAllCategory() {
		
		List<Category> category = categoryRepository.findAll();
		
		if (category.isEmpty()) {
			throw new RuntimeException("A lista de categoria esta vazia");
			
		}
		
		return categoryRepository.findAll();
	}
	
	
	// pegar por id
	public Category getCategoryById(Long id) {
	    return categoryRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Categoria inexistente"));
	}
	
	//deleta por id
	public void deleteCategorySerive(Long id) {
	
		if (!categoryRepository.existsById(id)) {
			throw new RuntimeException("O id não existe para ser deletado");
			
		}
		
		if (productRepository.existsByCategoryId(id)) {
			throw new RuntimeException("A categoria não pode ser deletada. Por que esta associada a algum produto");
		}
		
		categoryRepository.deleteById(id);
		
	}
	
	//editar categoria
	public Category editCategoryService(Long id, Category category) {
		
		
		if (!categoryRepository.existsById(id)) {
			throw new RuntimeException("O id para editar não existe");
			
		}
		
		Category newCategory = categoryRepository.getReferenceById(id);
		
		if (category.getName() == null || category.getName().isBlank()) {
			throw new RuntimeException("Nome não pode ser vazio");
		}

		newCategory.setName(category.getName());
		
		
		return categoryRepository.save(newCategory);
	}

}
