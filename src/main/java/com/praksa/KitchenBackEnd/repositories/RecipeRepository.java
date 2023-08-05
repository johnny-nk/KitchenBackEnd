package com.praksa.KitchenBackEnd.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.praksa.KitchenBackEnd.models.entities.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
	
	public Set<Recipe> findAllByLikedRecipes(Long id); 
	public Recipe findByLikedRecipes(Long id);
	public List<Recipe> findByTitleContainingIgnoreCase(String title);
	
}
