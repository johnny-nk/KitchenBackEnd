package com.praksa.KitchenBackEnd.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.praksa.KitchenBackEnd.models.dto.IngredientDTO;
import com.praksa.KitchenBackEnd.models.dto.LimFactorDTO;
import com.praksa.KitchenBackEnd.models.entities.Ingredient;
import com.praksa.KitchenBackEnd.models.entities.LimitingFactor;
import com.praksa.KitchenBackEnd.models.entities.LimitingIngredient;
import com.praksa.KitchenBackEnd.repositories.IngredientRepository;
import com.praksa.KitchenBackEnd.repositories.LimitingFactorRepository;



@Service
public class LimitingFactorServiceImpl implements LimitingFactorService{

	@Autowired
	private LimitingFactorRepository limitingFactorRepository;
	@Autowired 
	private IngredientRepository ingredientRepository;
	
	
	@Override
	public LimitingFactor getLimitingFactorbyId(Long id) {
		Optional<LimitingFactor> limitingFactorById = limitingFactorRepository.findById(id);
		if(limitingFactorById.isPresent()) {
			return limitingFactorById.get();
		}else {
				
		return null;
		}
	}

	@Override
	public Iterable<LimitingFactor> getAllLimitingFactors() {
		Iterable<LimitingFactor> allLimitingFactors = limitingFactorRepository.findAll();
		if(allLimitingFactors != null) {
			return allLimitingFactors;
		}else {
				
		return null;
		}
	}

	@Override
	public LimitingFactor addLimitingFactor(LimFactorDTO limDTO,  Long ingredientId) {
		LimitingFactor newLimitingFactor = new LimitingFactor();
		newLimitingFactor.setName(limDTO.getName());
		Optional<Ingredient> ingredientsId = ingredientRepository.findById(ingredientId);
		if(ingredientsId.isPresent()){
			Ingredient ingredient = ingredientsId.get();
	        LimitingIngredient newLimitingIngredient = new LimitingIngredient();
	        
	        newLimitingIngredient.setIngredients(ingredient);
	        newLimitingIngredient.setLimitingFactor(newLimitingFactor);
	        
	        
	        newLimitingFactor.getIngredients().add(newLimitingIngredient);
			LimitingFactor savedLimitingFactor = limitingFactorRepository.save(newLimitingFactor);
			return savedLimitingFactor;
		}else {	
			return null;
		}
	}
}