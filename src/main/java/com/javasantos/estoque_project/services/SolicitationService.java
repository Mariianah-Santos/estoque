package com.javasantos.estoque_project.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.javasantos.estoque_project.infrastructure.entity.Product;
import com.javasantos.estoque_project.infrastructure.entity.Solicitation;
import com.javasantos.estoque_project.infrastructure.enuns.MovementType;
import com.javasantos.estoque_project.infrastructure.respository.ProductRepository;
import com.javasantos.estoque_project.infrastructure.respository.SolicitationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SolicitationService {
	
	private final SolicitationRepository solicitationRepository;
	private final ProductRepository productRepository;
    // Funcionário cria a solicitação
    public Solicitation create(Solicitation solicitation) {
        solicitation.setStatus(MovementType.PENDING);
        solicitation.setCreateSolicition(LocalDateTime.now());
        return solicitationRepository.save(solicitation);
    }

    // Almoxarifado lista os pedidos pendentes
    public List<Solicitation> findAllPending() {
        return solicitationRepository.findByStatus(MovementType.PENDING);
    }

    // Almoxarifado aprova ou recusa
    public Solicitation updateStatus(Long id, MovementType status) {
        Solicitation solicitation = solicitationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));
        
        solicitation.setStatus(status);

        // se aprovado, decrementa o estoque
        if (status == MovementType.APPROVED) {
            Product product = solicitation.getProduct();

            if (product.getQuantity() < solicitation.getQuantity()) {
                throw new RuntimeException("Quantidade insuficiente no estoque");
            }

            product.setQuantity(product.getQuantity() - solicitation.getQuantity());
            productRepository.save(product);
        }

        return solicitationRepository.save(solicitation);
    }

    // Histórico do funcionário
    public List<Solicitation> findByResponsible(Long responsibleId) {
        return solicitationRepository.findByResponsibleId(responsibleId);
    }

    // trazer todos
    public List<Solicitation> findAll() {
        return solicitationRepository.findAll();
    }
}
