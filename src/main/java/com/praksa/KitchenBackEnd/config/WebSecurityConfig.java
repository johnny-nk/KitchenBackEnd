package com.praksa.KitchenBackEnd.config;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

	private SecretKey secretKey;

	public WebSecurityConfig() {
		super();
		this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
	}

	@Bean
	public SecretKey secretKey() {
		return this.secretKey;
	}

//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http.cors().and().csrf().disable()
////		http.csrf().disable()
//				.addFilterAfter(new JWTAuthorizationFilter(secretKey), UsernamePasswordAuthenticationFilter.class)
//				.authorizeRequests()
//				.antMatchers(HttpMethod.POST, "/api/v1/project/users/login", "/api/v1/project/register/regUser").permitAll()
//				.and()
//				.authorizeRequests()
//				.antMatchers(HttpMethod.GET, "/api/v1/project/register/allbyUserName**", "api/v1/project/recipes").permitAll()
//				.and()
//				.authorizeRequests()
////				.antMatchers(HttpMethod.GET, "api/v1/project/reci**").permitAll()
//				.anyRequest().authenticated();
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
//			http.csrf().disable()
				.addFilterAfter(new JWTAuthorizationFilter(secretKey), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/api/v1/project/users/login", "/api/v1/project/register/regUser")
				.permitAll().and().authorizeRequests()
				.antMatchers(HttpMethod.GET, "/api/v1/project/register/allbyUserName**", "/api/v1/project/recipes")
				.permitAll().and().authorizeRequests()
//					.antMatchers(HttpMethod.GET, "api/v1/project/reci**").permitAll()
				.anyRequest().authenticated();
		return http.build();

	}
}
