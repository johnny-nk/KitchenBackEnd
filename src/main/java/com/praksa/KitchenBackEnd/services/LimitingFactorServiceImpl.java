package com.praksa.KitchenBackEnd.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.praksa.KitchenBackEnd.models.dto.LimFactorDTO;
import com.praksa.KitchenBackEnd.models.entities.Ingredient;
import com.praksa.KitchenBackEnd.models.entities.LimitingFactor;
import com.praksa.KitchenBackEnd.models.entities.LimitingIngredient;
import com.praksa.KitchenBackEnd.repositories.IngredientRepository;
import com.praksa.KitchenBackEnd.repositories.LimitingFactorRepository;
import com.praksa.KitchenBackEnd.repositories.LimitingIngredientRepository;

@Service
public class LimitingFactorServiceImpl implements LimitingFactorService {
	
	
	@Autowired
	private LimitingFactorRepository limitingFactorRepository;
	@Autowired
	private IngredientRepository ingredientRepository;
	@Autowired
	private LimitingIngredientRepository limitingIngredientRepository;
	
	
	
	
	
	
	@Override
	public LimitingFactor addLimitingFactor(LimFactorDTO limDTO, Long ingredientId) {
		
		LimitingFactor newLimitingFactor = new LimitingFactor();
	    newLimitingFactor.setName(limDTO.getName());
	    limitingFactorRepository.save(newLimitingFactor); // Save the LimitingFactor first
	    
	    Optional<Ingredient> ingredientsId = ingredientRepository.findById(ingredientId);
	    if (ingredientsId.isPresent()) {
	        Ingredient ingredient = ingredientsId.get();
	        LimitingIngredient newLimitingIngredient = new LimitingIngredient();

	        newLimitingIngredient.setIngredients(ingredient);
	        newLimitingIngredient.setLimitingFactor(newLimitingFactor);
	        
	        
	        
	        limitingIngredientRepository.save(newLimitingIngredient);
	        
	        
	      
	        return newLimitingFactor;
	    } else {
	        return null;
	    }
	}
	
	
	
	
	
	
	
	
	
	
}
