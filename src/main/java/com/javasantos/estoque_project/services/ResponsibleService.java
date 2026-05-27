package com.javasantos.estoque_project.services;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.javasantos.estoque_project.infrastructure.entity.Responsible;
import com.javasantos.estoque_project.infrastructure.respository.ProductRepository;
import com.javasantos.estoque_project.infrastructure.respository.ResponsibleRepository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResponsibleService {

	private final ResponsibleRepository responsibleRepository;
	private final ProductRepository productRepository;
	private final PasswordEncoder passwordEncoder;
		
	// criar um responsavel
	public Responsible createResponsibleService(Responsible responsible) {
			
		if (responsible.getName() == null || responsible.getName().isBlank()) {
			throw new RuntimeException("Por favor digite um Nome para o responsável");
		}
			
		if (responsible.getId() != null && responsibleRepository.existsById(responsible.getId())) {
			throw new RuntimeException("Id já existente");
		}
			
		if (responsible.getEmail() == null || responsible.getEmail().isBlank()) {
			throw new RuntimeException("Por favor digite um Email para o responsável");
		}
		
		if (responsibleRepository.existsByEmail(responsible.getEmail())) {
            throw new RuntimeException("Este email já está cadastrado");
        }
		
		if (responsible.getPassword() == null || responsible.getPassword().isBlank()) {
			throw new RuntimeException("A senha é um campo obrigatório");
		}
		
		
		responsible.setPassword(passwordEncoder.encode(responsible.getPassword()));
		responsible.setActive(true);
			
		return responsibleRepository.save(responsible);
	} 
	
	// pegar todos os responsaveis
	public List<Responsible> getAllResponsible() {
		
		List<Responsible> listResponsible = responsibleRepository.findAll();
		
		if (listResponsible.isEmpty()) {
			throw new RuntimeException("Lista de responsável esta vazia");
		}
		
		return responsibleRepository.findAll();
	}
	
	// pegar responsavel por id
	public Responsible getResponsibleById(Long id) {
		return responsibleRepository.findById(id).orElseThrow(() -> new RuntimeException("Responsável inexistente"));
	}
	
	// deleta responsavel por id
	public void deleteResponsibleById(Long id) {
		
		if (!responsibleRepository.existsById(id)) {
			throw new RuntimeException("O id para deletar o responsável não existe");
			
		} 
		
		if (productRepository.existsByResponsibleId(id)) {
			throw new RuntimeException("O responsável não pode ser deletado. Esta associado a algum produto");
		}
		
		responsibleRepository.deleteById(id);
	}
	
	// editar o responsavel por id
	public Responsible editResponsibleById(Long id, Responsible responsible) {
		
		if (!responsibleRepository.existsById(id)) {
			throw new RuntimeException("O id para editar o responsável não existe");
		}
		
		Responsible existing = responsibleRepository.findByEmail(responsible.getEmail());
		
		if (existing != null && !existing.getId().equals(id)) {
		    throw new RuntimeException("O Email inserido já existe");
		}
		
		Responsible newResponsible = responsibleRepository.findById(id)
			    .orElseThrow(() -> new RuntimeException("Responsável não encontrado"));
		
		if (responsible.getName() == null || responsible.getName().isBlank()) {
			throw new RuntimeException("Por favor digite um Nome para o responsável");
		}
			
		if (responsible.getEmail() == null || responsible.getEmail().isBlank()) {
			throw new RuntimeException("Por favor digite um Email para o responsável");
		}
		
		if (responsible.getPassword() == null || responsible.getPassword().isBlank()) {
			throw new RuntimeException("A senha é um campo obrigatório");
		}
		
		newResponsible.setName(responsible.getName());
		newResponsible.setEmail(responsible.getEmail());
		newResponsible.setTelefone(responsible.getTelefone());
		newResponsible.setActive(responsible.getActive());
		newResponsible.setPassword(responsible.getPassword());
		
		return responsibleRepository.save(newResponsible);
	}
			
}
