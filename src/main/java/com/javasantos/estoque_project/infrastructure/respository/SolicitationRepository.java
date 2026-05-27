package com.javasantos.estoque_project.infrastructure.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javasantos.estoque_project.infrastructure.entity.Solicitation;
import com.javasantos.estoque_project.infrastructure.enuns.MovementType;

public interface SolicitationRepository extends JpaRepository<Solicitation, Long> {

    List<Solicitation> findByStatus(MovementType status);
    List<Solicitation> findByResponsibleId(Long responsibleId);
}
