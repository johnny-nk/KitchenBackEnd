package com.praksa.KitchenBackEnd.controllers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.praksa.KitchenBackEnd.models.dto.LoginDTO;
import com.praksa.KitchenBackEnd.models.entities.User;
import com.praksa.KitchenBackEnd.repositories.UserRepository;
import com.praksa.KitchenBackEnd.services.UserService;
import com.praksa.KitchenBackEnd.util.Encryption;

import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping(path = "/api/v1/project/users")
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private SecretKey secretKey;

	@Value("${spring.security.token-duration}")
	private Integer tokenDuration;
	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	private String getJWTToken(User user) {
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList(user.getRole().toString());
		String token = Jwts.builder().setId("softtekJWT").setSubject(user.getUsername())
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + this.tokenDuration)).signWith(this.secretKey)
				.compact();
		return "Bearer " + token;
	}

	@RequestMapping(path = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<?> login(@RequestParam("user") String username, @RequestParam("password") String pwd) {
		User user = userService.getUserByUsername(username);
		if (user != null && Encryption.validatePassword(pwd, user.getPassword())) {
			String token = getJWTToken(user);
			LoginDTO login = new LoginDTO();
			login.setUser(username);
			login.setToken(token);
			
			logger.info(String.format("[%s] login",login.getUser()));
			
			return new ResponseEntity<>(login, HttpStatus.OK);
		}
		return new ResponseEntity<>("\"Wrong credentials\"", HttpStatus.UNAUTHORIZED);
	}
	
}