package com.javasantos.estoque_project.infrastructure.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javasantos.estoque_project.infrastructure.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	
	boolean existsByCode(String code);
	boolean existsByResponsibleId(Long responsibleId);
	boolean existsByCategoryId(Long categoryId);
}
