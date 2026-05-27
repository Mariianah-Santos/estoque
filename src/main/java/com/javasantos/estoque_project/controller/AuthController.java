package com.javasantos.estoque_project.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javasantos.estoque_project.DTO.LoginRequest;
import com.javasantos.estoque_project.services.AuthService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
	
	private final AuthService authService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest dto) {
		 try {
		       Map<String, Object> response = authService.login(dto);
		        return ResponseEntity.ok(response);
		    } catch (RuntimeException e) {
		        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		    }
	}

}
