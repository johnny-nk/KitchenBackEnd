package com.praksa.KitchenBackEnd.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.praksa.KitchenBackEnd.models.entities.Ingredient;
import com.praksa.KitchenBackEnd.models.entities.LimitingFactor;
import com.praksa.KitchenBackEnd.models.entities.LimitingIngredient;

public interface LimitingIngredientRepository extends CrudRepository<LimitingIngredient, Long>{

	public List<LimitingIngredient> findByIngredients(Long id);
	public List<LimitingIngredient> findByLimitingFactor(LimitingFactor limitingFactor);
	public List<LimitingIngredient> findByLimitingFactorAndIngredients(LimitingFactor limitingFactor,
			Ingredient ingredient);

	
	

}
