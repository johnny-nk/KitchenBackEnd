package com.praksa.KitchenBackEnd.services;

import com.praksa.KitchenBackEnd.models.dto.IngredientDTO;
import com.praksa.KitchenBackEnd.models.entities.Ingredient;
import com.praksa.KitchenBackEnd.models.entities.LimitingFactor;

public interface IngredientService {

	
	
	public Ingredient addIngredient(IngredientDTO ingredient);
	public Ingredient updateIngredient(IngredientDTO ingredient, Long id);
    public Ingredient getIngredientById(Long id);
	public Iterable<Ingredient> getAllIngredients();
	public Ingredient deleteIngredient(Long id);
	public LimitingFactor connectIngredientToLimitingFactor(Long limitingFactorId, Long ingredientId);
	
	public IngredientDTO addLfToIngredient(Long ingId, Long lfId);
	public Iterable<IngredientDTO> getIngredients();
	
	
	
	
	
	
	
}
