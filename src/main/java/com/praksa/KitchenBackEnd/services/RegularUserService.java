package com.praksa.KitchenBackEnd.services;

import java.util.Optional;
import java.util.Set;

import com.praksa.KitchenBackEnd.models.dto.RegularUserRegisterDTO;
import com.praksa.KitchenBackEnd.models.entities.AffectedUsers;
import com.praksa.KitchenBackEnd.models.entities.LikedRecipes;
import com.praksa.KitchenBackEnd.models.entities.LimitingFactor;
import com.praksa.KitchenBackEnd.models.entities.Recipe;

public interface RegularUserService {
	
	
	public RegularUserRegisterDTO getLimFactors(String username);
	public Set<LimitingFactor> getLimitingFactors(String username);
	public AffectedUsers addLimitingFactor(String username, Long lfId);
	public Optional<AffectedUsers> removeLimitingFactor(Long lfId, String username);
	
	
	
	public Set<Recipe> getUserRecipes(Long userId);
	public Recipe addRecipeToUser(String username, Long recId);
	public Optional<LikedRecipes> removeRecipe(String username, Long recId);
}
