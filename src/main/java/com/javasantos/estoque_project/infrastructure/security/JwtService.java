package com.javasantos.estoque_project.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.javasantos.estoque_project.infrastructure.entity.Responsible;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class JwtService {
	
	private static final String SECRET = "mari341287fhmah37psn834ss2m87645";

	 // tempo de expiração: 24 horas em milissegundos
	private static final long EXPIRACAO = 1000 * 60 * 60 * 24;
	
	// gera o token com o email e role (permissoes) do usuário
    public String gerarToken(Responsible responsible) {
        Algorithm algoritmo = Algorithm.HMAC256(SECRET);

        return JWT.create()
                .withSubject(responsible.getEmail())
                .withClaim("role", responsible.getRole())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRACAO))
                .sign(algoritmo);
    }
    
    // extrai o email que está dentro do token
    public String extrairEmail(String token) {
        Algorithm algoritmo = Algorithm.HMAC256(SECRET);

        return JWT.require(algoritmo).build().verify(token).getSubject();
    }
    
    // extrai o tipo e user pelo token
    public String extrairRole(String token) {
        Algorithm algoritmo = Algorithm.HMAC256(SECRET);

        return JWT.require(algoritmo).build().verify(token).getClaim("role").asString();
    }
    
    // valida se o token é válido e não expirou
    public boolean validarToken(String token) {
    	
        try {
        	
            Algorithm algoritmo = Algorithm.HMAC256(SECRET);
            JWT.require(algoritmo).build().verify(token);
            
            return true;
            
        } catch (JWTVerificationException e) {
            return false;
        }
    }
}
