package com.praksa.KitchenBackEnd.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.praksa.KitchenBackEnd.controllers.util.RESTError;
import com.praksa.KitchenBackEnd.models.dto.IngredientDTO;
import com.praksa.KitchenBackEnd.models.entities.Ingredient;
import com.praksa.KitchenBackEnd.models.entities.LimitingFactor;
import com.praksa.KitchenBackEnd.services.IngredientService;

@RestController
@RequestMapping(path = "/api/v1/project/ingredient")
public class IngredientController {

	@Autowired
	private IngredientService ingredientService;

	@RequestMapping(method = RequestMethod.POST, value = "/newIngredient")
	public ResponseEntity<?> addNewIngredient(@Valid @RequestBody IngredientDTO ingredients) {
		try {
			return new ResponseEntity<>(ingredientService.addIngredient(ingredients), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "BAD Request"),
					HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/updateIngredient/{id}")
	public ResponseEntity<?> updateIngredient(@Valid @PathVariable Long id,
			@RequestBody IngredientDTO ingredientForUpdate) {
		try {
			Ingredient updatedIngredient = ingredientService.updateIngredient(ingredientForUpdate, id);
			if (updatedIngredient != null) {
				return new ResponseEntity<>(updatedIngredient, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new RESTError(HttpStatus.NOT_FOUND.value(), "Ingredient not found"),
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(
					new RESTError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> getIngredientById(@PathVariable Long id) {
		try {
			Ingredient getIngredientById = ingredientService.getIngredientById(id);
			if (getIngredientById != null) {
				return new ResponseEntity<>(getIngredientById, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new RESTError(HttpStatus.NOT_FOUND.value(), "Ingredient not found"),
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(
					new RESTError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/allIngredients")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<?> getAllIngredients() {
		try {
			Iterable<Ingredient> getAllIngredients = ingredientService.getAllIngredients();
			if (getAllIngredients != null) {
				return new ResponseEntity<>(getAllIngredients, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new RESTError(HttpStatus.NOT_FOUND.value(), "Ingredient not found"),
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(
					new RESTError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteIngredient/{id}")
	public ResponseEntity<?> deleteIngredient(@PathVariable Long id) {
		Ingredient deletedIngredient = ingredientService.deleteIngredient(id);

		return new ResponseEntity<>(deletedIngredient, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.POST, value = "/connectIngredientToLimitingFactor/{ingredientId}/to/{limitingFactorId}")
	public ResponseEntity<?> connectIngredientToLimitingFactor(@PathVariable Long ingredientId,
			@PathVariable Long limitingFactorId) {
		LimitingFactor connectedLimitingFactor = ingredientService.connectIngredientToLimitingFactor(limitingFactorId,
				ingredientId);
		try {
			if (connectedLimitingFactor != null) {
				return new ResponseEntity<>(connectedLimitingFactor, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(
						new RESTError(HttpStatus.NOT_FOUND.value(), "Ingredient or Limiting Factor not found or Connection already exists"),
						HttpStatus.NOT_FOUND);

			}
		} catch (Exception e) {
			return new ResponseEntity<>(
					new RESTError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error"),
					HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

}
