package com.javasantos.estoque_project.controller;

import java.util.List;

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

import com.javasantos.estoque_project.infrastructure.entity.Category;
import com.javasantos.estoque_project.services.CategoryService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
	
	private final CategoryService categoryService;
	
	// criando a category
	@PostMapping
	public ResponseEntity<?> addCategory(@RequestBody Category category) {
		
		try {
			
			Category savedCategory = categoryService.createCategoryeService(category);
			return ResponseEntity.status(201).body(savedCategory);
			
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	// pegar a lista toda e categoria
	@GetMapping
	public ResponseEntity<?> getCategoryController() {
		
		try {
			List<Category> listCategory = categoryService.getAllCategory();
			
			return ResponseEntity.ok(listCategory);
			
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getCategoryByIdController(@PathVariable Long id) {
		
		try {
			Category category = categoryService.getCategoryById(id);
			return ResponseEntity.ok(category);
			
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
			 
		}
		

	}
	
	
	// deleta categoria pelo id
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategoryController(@PathVariable Long id) {
		
		try {
			categoryService.deleteCategorySerive(id);
			return ResponseEntity.noContent().build();
			
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> editCategoryController(@PathVariable Long id, @RequestBody Category category) {
		
		try {
			Category newCategory = categoryService.editCategoryService(id, category);
			return ResponseEntity.ok(newCategory);
			
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	

}
