package com.praksa.KitchenBackEnd.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.praksa.KitchenBackEnd.models.dto.IngredientDTO;
import com.praksa.KitchenBackEnd.models.dto.RecipeDTO;
import com.praksa.KitchenBackEnd.models.entities.Cook;
import com.praksa.KitchenBackEnd.models.entities.Ingredient;
import com.praksa.KitchenBackEnd.models.entities.LikedRecipes;
import com.praksa.KitchenBackEnd.models.entities.LimitingFactor;
import com.praksa.KitchenBackEnd.models.entities.Recipe;
import com.praksa.KitchenBackEnd.models.entities.RegularUser;
import com.praksa.KitchenBackEnd.repositories.CookRepository;
import com.praksa.KitchenBackEnd.repositories.IngredientRepository;
import com.praksa.KitchenBackEnd.repositories.LikedRecipesRepository;
import com.praksa.KitchenBackEnd.repositories.LimitingFactorRepository;
import com.praksa.KitchenBackEnd.repositories.RecipeIngredientRepository;
import com.praksa.KitchenBackEnd.repositories.RecipeRepository;
import com.praksa.KitchenBackEnd.repositories.RegularUserRepository;

@RestController
public class DummyController {

	@Autowired
	CookRepository cookRepository;
	@Autowired
	RecipeRepository recipeRepository;
	@Autowired
	IngredientRepository ingredientRepo;
	@Autowired
	LimitingFactorRepository limiFactorRepository;
	@Autowired
	RegularUserRepository regUserRepository;
	
	@Autowired
	LikedRecipesRepository likedRecipesRepository;
	@Autowired
	RecipeIngredientRepository recIngRepo;
	
	
	@RequestMapping(method = RequestMethod.GET, path="/getRec/{id}")
	public ResponseEntity<?> getRecIng(@PathVariable Long id) {
		return new ResponseEntity<>(recIngRepo.findById(id).get(), HttpStatus.OK);
	}
	
	
	
	
	
	@RequestMapping(method = RequestMethod.GET, path ="/findIng/{name}")
	public ResponseEntity<?> findIng(@PathVariable String name) {
		Ingredient ing = ingredientRepo.findByNameStartingWith(name);
		return new ResponseEntity<>(ing, HttpStatus.OK);
	}
	
	
	@RequestMapping(method = RequestMethod.POST, path = "/user/{userId}/affectedBy/{lfId}")
	public ResponseEntity<?> addLFtoUser(@PathVariable Long userId, @PathVariable Long lfId) {
		RegularUser regUser = regUserRepository.findById(userId).get();
		LimitingFactor lf = limiFactorRepository.findById(lfId).get();
//		regUser.getLimitingFactor().add(lf);
		regUserRepository.save(regUser);
		return new ResponseEntity<>(regUser, HttpStatus.CREATED);
	}
	
	
	
	
	@RequestMapping(method = RequestMethod.POST, path = "/addIngredient")
	public ResponseEntity<?> addIngredient(@Valid @RequestBody IngredientDTO ingredient) {
		Ingredient ingredients = new Ingredient();
		ingredients.setCalories(ingredient.getCalories());
		ingredients.setCarbs(ingredient.getCarbs());
		ingredients.setFats(ingredient.getFats());
		ingredients.setName(ingredient.getName());
		ingredients.setProteins(ingredient.getProteins());
		ingredients.setSaturatedFats(ingredient.getSaturatedFats());
		ingredients.setSugars(ingredient.getSugars());
		ingredients.setUnit(ingredient.getUnit());
		ingredientRepo.save(ingredients);
		return new ResponseEntity<>(ingredients, HttpStatus.ACCEPTED);
	}
	
	
	
	//----------------------------------------------------------------// get
	@RequestMapping(method = RequestMethod.GET, path = "/getRecipes")
	public Iterable<Recipe> getRecipes() {
		return recipeRepository.findAll();
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/ingredients")
	public Iterable<Ingredient> getIngredients() {
		return ingredientRepo.findAll();
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/limitingFactors")
	public Iterable<LimitingFactor> getLimitingFactors() {
		return limiFactorRepository.findAll();
	}
	
}
