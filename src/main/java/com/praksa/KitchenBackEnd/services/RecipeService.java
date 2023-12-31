package com.praksa.KitchenBackEnd.services;


import java.util.List;
import java.util.Set;

import com.praksa.KitchenBackEnd.models.dto.RecipeRegisterDTO;
import com.praksa.KitchenBackEnd.models.entities.LimitingFactor;
import com.praksa.KitchenBackEnd.models.entities.Recipe;


public interface RecipeService {
	
	
	public Iterable<Recipe> getRecipes();
	
	public RecipeRegisterDTO getRecipe(Long id);
	public RecipeRegisterDTO createRecipe(RecipeRegisterDTO dto, String username);
	public RecipeRegisterDTO updateRecipe(RecipeRegisterDTO dto, Long id);
	public Recipe deleteRecipe(Long id);
	public Iterable<RecipeRegisterDTO> searchByRecipeName(String title);
//	public RecipeDTO getRecipeCaloricValue(Long id);
	public Iterable<RecipeRegisterDTO> getAllRecipes();
	public Iterable<RecipeRegisterDTO> getFormatedRecipes(String username);
	
	public List<RecipeRegisterDTO> myCookbook(String username);
	
	
	
	public Set<LimitingFactor> getLFfromRecipe(Long id);
	
	
}
