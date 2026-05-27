package com.javasantos.estoque_project.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javasantos.estoque_project.infrastructure.entity.Solicitation;
import com.javasantos.estoque_project.infrastructure.enuns.MovementType;
import com.javasantos.estoque_project.services.SolicitationService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/solicitations")
public class SolicitationController {
	
    private final SolicitationService solicitationService;

    // Funcionário cria a solicitação
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')") 
    public ResponseEntity<Solicitation> create(@RequestBody Solicitation solicitation) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(solicitationService.create(solicitation));
    }

    // Almoxarifado lista os pendentes
    @GetMapping("/pending")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')") 
    public ResponseEntity<List<Solicitation>> findAllPending() {
        return ResponseEntity.ok(solicitationService.findAllPending());
    }
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')") 
    public ResponseEntity<List<Solicitation>> findAll() {
        return ResponseEntity.ok(solicitationService.findAll());
    }

    // Almoxarifado aprova ou recusa
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')") 
    public ResponseEntity<Solicitation> updateStatus(
            @PathVariable Long id,
            @RequestParam MovementType status) {
        return ResponseEntity.ok(solicitationService.updateStatus(id, status));
    }

    // Histórico do funcionário
    @GetMapping("/responsible/{responsibleId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<Solicitation>> findByResponsible(
            @PathVariable Long responsibleId) {
        return ResponseEntity.ok(solicitationService.findByResponsible(responsibleId));
    }

}
