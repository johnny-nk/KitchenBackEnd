 package com.praksa.KitchenBackEnd.controllers;

import java.security.Principal;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.praksa.KitchenBackEnd.models.dto.CookRegisterDTO;
import com.praksa.KitchenBackEnd.services.CookService;
import com.praksa.KitchenBackEnd.services.UserService;

@RestController
@RequestMapping(path = "api/v1/project/admin")
public class AdminController {
	
	@Autowired
	private CookService cookService;
	
	@Autowired
	private UserService userService;
	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	
	//Create
	@RequestMapping(method = RequestMethod.POST, path = "/addCook" )
	public ResponseEntity<?> addCook(@Valid @RequestBody CookRegisterDTO dto) {
		logger.info("New cook added.");
		return null;
	}
	
}
