package com.praksa.KitchenBackEnd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.praksa.KitchenBackEnd.models.dto.RecipeDTO;
import com.praksa.KitchenBackEnd.models.entities.Cook;
import com.praksa.KitchenBackEnd.models.entities.Ingredient;
import com.praksa.KitchenBackEnd.models.entities.LimitingFactor;
import com.praksa.KitchenBackEnd.models.entities.Recipe;
import com.praksa.KitchenBackEnd.models.entities.RegularUser;
import com.praksa.KitchenBackEnd.repositories.CookRepository;
import com.praksa.KitchenBackEnd.repositories.IngredientRepository;
import com.praksa.KitchenBackEnd.repositories.LimitingFactorRepository;
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
	
	//recimo da se kuvar ulogovao i da mozemo da izvucemo njegov id iz tokena
	@RequestMapping(method = RequestMethod.POST, path = "/createRecipe/{cookId}")
	public ResponseEntity<?> createRecipe(@RequestBody RecipeDTO recDTO, @PathVariable Long cookId) {
		Recipe recipe = new Recipe();
		Cook cook = cookRepository.findById(cookId).get();
		recipe.setAmount(recDTO.getAmount());
		recipe.setCook(cook);
		recipe.setSteps(recDTO.getSteps());
		recipe.setTimeToPrepare(recDTO.getTimeToPrepare());
		recipe.setTitle(recDTO.getTitle());
		recipe.setDescription(recDTO.getDescription());
		recipeRepository.save(recipe);
		return new ResponseEntity<>(recipe, HttpStatus.CREATED);
	}
	
	
	@RequestMapping(method = RequestMethod.POST, path = "/addLimFactor/{ingId}")
	public ResponseEntity<?> addLimitingFactor(@RequestBody String name, @PathVariable Long ingId) {
		Ingredient ing = ingredientRepo.findById(ingId).get();
		LimitingFactor lf = new LimitingFactor();
		lf.setName(name);
		lf.setIngredient(ing);
		limiFactorRepository.save(lf);
		return new ResponseEntity<>(lf, HttpStatus.CREATED);
	}
	
	
	@RequestMapping(method = RequestMethod.POST, path = "/user/{userId}/affectedBy/{lfId}")
	public ResponseEntity<?> addLFtoUser(@PathVariable Long userId, @PathVariable Long lfId) {
		RegularUser regUser = regUserRepository.findById(userId).get();
		LimitingFactor lf = limiFactorRepository.findById(lfId).get();
		regUser.getLimitingFactor().add(lf);
		regUserRepository.save(regUser);
		return new ResponseEntity<>(regUser, HttpStatus.CREATED);
	}
	
	
	@RequestMapping(method = RequestMethod.POST, path = "/user/{userId}/likes/{recId}")
	public ResponseEntity<?> addRecipeToUser(@PathVariable Long userId, @PathVariable Long recId) {
		RegularUser regUser = regUserRepository.findById(userId).get();
		Recipe recipe = recipeRepository.findById(recId).get();
		regUser.getLikedRecipes().add(recipe);
		regUserRepository.save(regUser);
		
		return new ResponseEntity<>(regUser, HttpStatus.OK);
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