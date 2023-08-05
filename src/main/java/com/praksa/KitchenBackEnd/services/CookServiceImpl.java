package com.praksa.KitchenBackEnd.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.praksa.KitchenBackEnd.models.entities.Cook;
import com.praksa.KitchenBackEnd.repositories.CookRepository;

@Service
public class CookServiceImpl implements CookService {
	
	@Autowired
	private CookRepository cookRepository;
	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Override
	public Cook getCook(Long id) {
		logger.info("Cook with id = " + id + " found.");
		return cookRepository.findById(id).get();
	}

}
