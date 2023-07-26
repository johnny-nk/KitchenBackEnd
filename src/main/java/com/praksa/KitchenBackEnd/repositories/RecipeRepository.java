package com.praksa.KitchenBackEnd.repositories;

import org.springframework.data.repository.CrudRepository;

import com.praksa.KitchenBackEnd.models.entities.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

}
