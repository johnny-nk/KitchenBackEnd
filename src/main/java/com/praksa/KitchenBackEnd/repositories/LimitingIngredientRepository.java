package com.praksa.KitchenBackEnd.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.praksa.KitchenBackEnd.models.entities.LimitingIngredient;
import com.praksa.KitchenBackEnd.models.entities.Recipe;

public interface LimitingIngredientRepository extends CrudRepository<LimitingIngredient, Long>{

	public List<LimitingIngredient> findByIngredients(Long id);
	
	

}
