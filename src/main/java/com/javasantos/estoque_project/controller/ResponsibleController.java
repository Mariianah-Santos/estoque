package com.javasantos.estoque_project.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javasantos.estoque_project.infrastructure.entity.Responsible;
import com.javasantos.estoque_project.services.ResponsibleService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/responsible")
public class ResponsibleController {

	private final ResponsibleService responsibleService;
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addResponsibleController(@RequestBody Responsible responsible) {
		
		try {
			Responsible savedResponsible = responsibleService.createResponsibleService(responsible);
			return ResponseEntity.status(201).body(savedResponsible);
			
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	} 
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<?> getAllResponsibleController() {
		
		try {
			List<Responsible> listResponsible = responsibleService.getAllResponsible();
			return ResponseEntity.ok(listResponsible);
			
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
			
		}
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getResponsibleByIdControlle(@PathVariable Long id) {
		
		try {
			Responsible responsible = responsibleService.getResponsibleById(id);
			return ResponseEntity.ok(responsible);
			
			
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
			
		}
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteResponsibleById(@PathVariable Long id) {
		
		try {
			responsibleService.deleteResponsibleById(id);
			return ResponseEntity.noContent().build();
			
			
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
			
		}
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> editResponsibleById(@PathVariable Long id, @RequestBody Responsible responsible) {
		
		try {
			
			Responsible newResponsible = responsibleService.editResponsibleById(id, responsible);
			return ResponseEntity.ok(newResponsible);
			
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());

			
		}
	}
	
}
