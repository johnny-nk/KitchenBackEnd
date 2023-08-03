package com.praksa.KitchenBackEnd.controllers;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.praksa.KitchenBackEnd.controllers.util.RESTError;
import com.praksa.KitchenBackEnd.models.dto.UserRegisterDTO;
import com.praksa.KitchenBackEnd.models.entities.LikedRecipes;
import com.praksa.KitchenBackEnd.models.entities.LimitingFactor;
import com.praksa.KitchenBackEnd.models.entities.RegularUser;
import com.praksa.KitchenBackEnd.runtimeException.LimitingFactorNotFoundException;
import com.praksa.KitchenBackEnd.runtimeException.UserNotFoundException;
import com.praksa.KitchenBackEnd.services.RecipeService;
import com.praksa.KitchenBackEnd.services.RegularUserService;
import com.praksa.KitchenBackEnd.services.UserService;

@RestController
@RequestMapping(path = "api/v1/project/regUser")
public class RegularUserController {
	
	@Autowired
	UserService userService;
	@Autowired
	RegularUserService regUserService;
	
	@Autowired
	RecipeService recipeService;
	
	//moguce da ne radi jos
	//=-==-=-==-=-==-==-=-==-=-==-==-LIMITING FACTOR=-=-==-==-=-==-=-==-==-=-==-=-==-==-=-==-=-= //
	
	@RequestMapping(method = RequestMethod.GET, path = "/getLf/{userId}")
	public ResponseEntity<?> getLimFactors(@Valid @PathVariable Long userId) {
	    try {
	        return new ResponseEntity<>(regUserService.getLimitingFactors(userId), HttpStatus.OK);
	    } catch (UserNotFoundException e) {
	        return new ResponseEntity<>(new RESTError(HttpStatus.NOT_FOUND.value(), "User not found with id: " + userId), HttpStatus.NOT_FOUND);
	    } catch (Exception e) {
	        return new ResponseEntity<>(new RESTError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/user/{userId}/addLf/{lFid}")
	public ResponseEntity<?> addLimitingFactor(@Valid @PathVariable Long userId, @PathVariable Long lFid) {
	    try {
	        return new ResponseEntity<>(regUserService.addLimitingFactor(userId, lFid), HttpStatus.OK);
	    } catch (UserNotFoundException e) {
	        return new ResponseEntity<>(new RESTError(HttpStatus.NOT_FOUND.value(), "User not found with id: " + userId), HttpStatus.NOT_FOUND);
	    } catch (LimitingFactorNotFoundException e) {
	        return new ResponseEntity<>(new RESTError(HttpStatus.NOT_FOUND.value(), "Limiting Factor not found with id: " + lFid), HttpStatus.NOT_FOUND);
	    } catch (Exception e) {
	        return new ResponseEntity<>(new RESTError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path = "/user/{userId}")
	public ResponseEntity<?> removeLimitingFactor(@PathVariable Long userId) {
		return new ResponseEntity<>(regUserService.removeLimitingFactor(userId), HttpStatus.OK);
	}
	
	
	//=-==-=-==-=-==-==-=-==-=-==-==- RECIPE=-=-==-==-=-==-=-==-==-=-==-=-==-==-=-==-=-= //
	@RequestMapping(method = RequestMethod.GET, path = "/userRec/{id}")
	public ResponseEntity<?> getUsersFav(@PathVariable Long id ) {
		return new ResponseEntity<>(regUserService.getUserRecipes(id), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/user/{userId}/rec/{recId}")
	public ResponseEntity<?> addRecToUser(@PathVariable Long userId, @PathVariable Long recId) {
		return new ResponseEntity<>(regUserService.addRecipeToUser(userId, recId), HttpStatus.OK);
		
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path = "/user/{userId}/rec/{recId}")
	public ResponseEntity<?> removeLikedRecipe(@PathVariable Long userId, @PathVariable Long recId) {
		return new ResponseEntity<>(regUserService.removeRecipe(userId, recId), HttpStatus.ACCEPTED);
	}
}
