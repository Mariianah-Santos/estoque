package com.javasantos.estoque_project.infrastructure.respository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.javasantos.estoque_project.infrastructure.entity.Responsible;

public interface ResponsibleRepository extends JpaRepository<Responsible, Long> {


    boolean existsByEmail(String email);
    Responsible findByEmail(String email);
    
}
