package com.javasantos.estoque_project.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.javasantos.estoque_project.DTO.LoginRequest;
import com.javasantos.estoque_project.infrastructure.entity.Responsible;
import com.javasantos.estoque_project.infrastructure.respository.ResponsibleRepository;
import com.javasantos.estoque_project.infrastructure.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final ResponsibleRepository responsibleRepository;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;

	public Map<String, Object> login(LoginRequest dto) {

	    Responsible user = responsibleRepository.findByEmail(dto.getEmail());

	    if (user == null) {
	        throw new RuntimeException("Usuário não encontrado");
	    }

	    if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
	        throw new RuntimeException("Senha inválida");
	    }

	    Map<String, Object> response = new HashMap<>();
	    response.put("token", jwtService.gerarToken(user));
	    response.put("id", user.getId());
	    response.put("name", user.getName());
	    response.put("role", user.getRole());

	    return response;
	}
}
