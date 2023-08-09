package com.praksa.KitchenBackEnd.repositories;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.praksa.KitchenBackEnd.models.entities.LikedRecipes;

public interface LikedRecipesRepository extends CrudRepository<LikedRecipes, Long> {
	
	public Set<LikedRecipes> findByRegularUserId(Long id);
	public Optional<LikedRecipes> findByRegularUserIdAndRecipeId(Long userId, Long recId);
	public void deleteAllByRegularUserId(Long userId);
	
	public void deleteAllByRecipeId(Long recId);
}
