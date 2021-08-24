package com.vvs.mainrxbackend.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;

import reactor.core.publisher.Mono;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {
	
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		
		String token = authentication.getCredentials().toString();
		String username;
		
		try {
			username = jwtUtil.extractUsername(token);
		} catch (Exception e) {
			username = null;
			System.out.println(e);
		}
		
		if (username != null && jwtUtil.validateToken(token)) {
			Claims claims = jwtUtil.getClaimsFromToken(token);
			
			List<String> roles = new ArrayList<String>();
			roles = castList(String.class, claims.get("role", List.class));

			List<SimpleGrantedAuthority> authorities = roles.stream()
																											.map(SimpleGrantedAuthority::new)
																											.collect(Collectors.toList());
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
			return Mono.just(authenticationToken);
		} else {
			return Mono.empty();			
		}		
	}

	public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c) {
		List<T> r = new ArrayList<T>();
		for(Object o: c) {
			r.add(clazz.cast(o));
		}
		return r;
	}

}
