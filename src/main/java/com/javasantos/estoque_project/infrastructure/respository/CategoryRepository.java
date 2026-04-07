package com.javasantos.estoque_project.infrastructure.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javasantos.estoque_project.infrastructure.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
