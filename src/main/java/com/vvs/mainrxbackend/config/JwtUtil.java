package com.vvs.mainrxbackend.config;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.vvs.mainrxbackend.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.expiration}")
	private String expiration;
	
	public String extractUsername(String token) {
		return getClaimsFromToken(token)
					.getSubject();
	}
	
	public Boolean validateToken(String token) {
		return getClaimsFromToken(token)
					.getExpiration()
					.after(new Date());
	}
	
	public Claims getClaimsFromToken(String token) {
		String key = Base64.getEncoder().encodeToString(secret.getBytes());
		return Jwts.parserBuilder()
								.setSigningKey(key)
								.build()
								.parseClaimsJws(token) // NOT .parseClaimsJwt !!!!!!!!!!!!!!!!
								.getBody();
	}
	
	public String generateToken(User user) {
		HashMap<String, Object> claims = new HashMap<>();
		claims.put("role", List.of(user.getRole()));
		
		Long expirationSeconds = Long.parseLong(expiration);
		Date creationDate = new Date();
		Date expirationDate = new Date(creationDate.getTime() + expirationSeconds * 1000);
		
		return Jwts.builder()
								.setClaims(claims)
								.setSubject(user.getUsername())
								.setIssuedAt(creationDate)
								.setExpiration(expirationDate)
								.signWith(Keys.hmacShaKeyFor(secret.getBytes()))
								.compact();		
	}

}
