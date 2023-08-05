package com.praksa.KitchenBackEnd.controllers;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.praksa.KitchenBackEnd.controllers.util.RESTError;
import com.praksa.KitchenBackEnd.models.dto.RecipeDTO;
import com.praksa.KitchenBackEnd.models.dto.RecipeRegisterDTO;
import com.praksa.KitchenBackEnd.models.entities.Recipe;
import com.praksa.KitchenBackEnd.repositories.RecipeRepository;
import com.praksa.KitchenBackEnd.services.RecipeService;

@RestController
@RequestMapping(path = "/api/v1/project")
public class RecipeController {

	@Autowired
	private RecipeService recipeService;
	
	
	// Test endpoint za "/home", za neulogovanog korisnika
	@RequestMapping(method = RequestMethod.GET, path = "/")
	public ResponseEntity<?> getAllRecipes() {
		try {
	        Iterable<Recipe> recipes = recipeService.getRecipes();
	        if (recipes !=null) {
	            return new ResponseEntity<>(recipes, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(new RESTError(HttpStatus.NOT_FOUND.value(), "Recipes not found"), HttpStatus.NOT_FOUND);
	        }
	    } catch (Exception e) {
	        return new ResponseEntity<>(new RESTError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/recipes")
	public ResponseEntity<?> getRecipes() {
		try {
	        Iterable<Recipe> recipes = recipeService.getRecipes();
	        if (recipes !=null) {
	            return new ResponseEntity<>(recipes, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(new RESTError(HttpStatus.NOT_FOUND.value(), "Recipes not found"), HttpStatus.NOT_FOUND);
	        }
	    } catch (Exception e) {
	        return new ResponseEntity<>(new RESTError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@GetMapping(path = "/recipes/{id}")
	public ResponseEntity<?> getRecipe(@Valid @PathVariable Long id) {
		  try {
		        RecipeRegisterDTO recipe = recipeService.getRecipe(id);
		        if (recipe != null) {
		            return new ResponseEntity<>(recipe, HttpStatus.OK);
		        } else {
		            return new ResponseEntity<>(new RESTError(HttpStatus.NOT_FOUND.value(), "Recipe not found"), HttpStatus.NOT_FOUND);
		        }
		    } catch (Exception e) {
		        return new ResponseEntity<>(new RESTError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		    }
	}
	
	
	// Bio bi isti metod kao i na updejtu, sa istim endpointom "/recipes/{id}, 
	@PostMapping(path = "/recipes")
	public ResponseEntity<?> createRecipe(@Valid @RequestBody RecipeRegisterDTO newRecipe, @RequestParam Long cookId) {
		try {
			return new ResponseEntity<>(recipeService.createRecipe(newRecipe, cookId), HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<RESTError>(
					new RESTError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"BAD Request"), HttpStatus.INTERNAL_SERVER_ERROR);
			
		}	
	}
	
	@DeleteMapping(path = "/recipes/{id}")
	public ResponseEntity<?> deleteRecipe(@Valid @PathVariable Long id) {
	   
	    try {
	    	 recipeService.deleteRecipe(id);
	 	    return new ResponseEntity<>("Recipe with ID " + id + " deleted successfully.", HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<RESTError>(
					new RESTError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"No recipes with that ID"), HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}

	
	@PutMapping(path = "/recipes/{id}")
	public ResponseEntity<?> updateRecipe(@Valid @RequestBody RecipeRegisterDTO recipe, @PathVariable Long id) {
		 try {		
		RecipeRegisterDTO updateRecipe = recipeService.updateRecipe(recipe, id);
		if(updateRecipe !=null ) {
			return new ResponseEntity<>(updateRecipe, HttpStatus.OK);
		}else {
            return new ResponseEntity<>(new RESTError(HttpStatus.NOT_FOUND.value(), "Recipe not found"), HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
	        return new ResponseEntity<>(new RESTError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
	
	//vraca recepte preko dto-a i ujedanko izracuna sve alergene i nutriciente za sve recepte
	@RequestMapping(method = RequestMethod.GET, path = "/recipes/getFormatedRecipes")
	public ResponseEntity<?> getFormatedRecipes() {
		return new ResponseEntity<>(recipeService.getFormatedRecipes(), HttpStatus.OK);
	}
	
	//pretraga po imenu
	@RequestMapping(method = RequestMethod.GET, path = "/recipes/search")
	public ResponseEntity<?> searchRecipe(@RequestParam String title) {
		return new ResponseEntity<>(recipeService.searchByRecipeName(title), HttpStatus.OK);
	}
	
	

	//------------------DINAMICKA PRETRAGA ALERGENA I HRANLJIVOSTI-------------------------------//
	//ne treba ali nek ostane za sada
	
	@GetMapping(path = "/recipeLF/{recId}")
	public ResponseEntity<?> getRecipeAndLF( @PathVariable Long recId) {
		 try {
		        RecipeRegisterDTO recipeDTO = recipeService.getRecipe(recId);
		        return new ResponseEntity<>(recipeDTO, HttpStatus.OK);
		    } catch (NoSuchElementException e) {
		        return new ResponseEntity<RESTError>(
		            new RESTError(HttpStatus.NOT_FOUND.value(), "Recipe with ID " + recId + " not found"),
		            HttpStatus.NOT_FOUND
		        );
		    } catch (Exception e) {
		        return new ResponseEntity<RESTError>(
		            new RESTError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error"),
		            HttpStatus.INTERNAL_SERVER_ERROR
		        );
		    }
	
}

	
}

