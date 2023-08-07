package com.praksa.KitchenBackEnd.controllers;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
	
	
	//=-==-=-==-=-==-==-=-==-=-==-==- USER'S LIMITING FACTORS=-=-==-==-=-==-=-==-==-=-==-=-==-==-=-==-=-= //
	
	@Secured("REGULARUSER")
	@RequestMapping(method = RequestMethod.GET, path = "/getLf/{userId}") //PRINCIPAL PI A NE ID
	public ResponseEntity<?> getLimFactors(@Valid @PathVariable Long userId) {
	    try {
	        return new ResponseEntity<>(regUserService.getLimitingFactors(userId), HttpStatus.OK);
	    } catch (UserNotFoundException e) {
	        return new ResponseEntity<>(new RESTError(HttpStatus.NOT_FOUND.value(), "User not found with id: " + userId), HttpStatus.NOT_FOUND);
	    } catch (Exception e) {
	        return new ResponseEntity<>(new RESTError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	//kroz Principal
	@Secured("REGULARUSER")
	@RequestMapping(method = RequestMethod.GET, path = "/getLf")
	public ResponseEntity<?> getLimitingFactors(@Valid Principal p) {
		return new ResponseEntity<>(regUserService.getLimFactors(p.getName()), HttpStatus.OK);
	}
	
	@Secured("REGULARUSER")
	@RequestMapping(method = RequestMethod.POST, path = "/user/{userId}/addLf/{lFid}")  //PRINCIPAL PI A NE USERID
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
	
	@Secured("REGULARUSER")
	@RequestMapping(method = RequestMethod.DELETE, path = "/user/{userId}/lf/{lfId}") //PRINCIPAL PI A NE USERID
	public ResponseEntity<?> removeLimitingFactor(@PathVariable Long userId, @PathVariable Long lfId) {
		return new ResponseEntity<>(regUserService.removeLimitingFactor(userId, lfId), HttpStatus.OK);
	}
	
	
	//=-==-=-==-=-==-==-=-==-=-==-==- USER'S LIKED RECIPES=-=-==-==-=-==-=-==-==-=-==-=-==-==-=-==-=-= //
	
	@Secured("REGULARUSER")
	@RequestMapping(method = RequestMethod.GET, path = "/myCookbook")
	public ResponseEntity<?> getMyCookbook(@PathVariable String username, Principal p) {
		return new ResponseEntity<>(recipeService.myCookbook(p.getName()), HttpStatus.OK);
	}
	
	@Secured("REGULARUSER")
	@RequestMapping(method = RequestMethod.POST, path = "/user/{userId}/rec/{recId}") //PRINCIPAL PI A NE USER ID
	public ResponseEntity<?> addRecToUser(@PathVariable Long userId, @PathVariable Long recId) {
		return new ResponseEntity<>(regUserService.addRecipeToUser(userId, recId), HttpStatus.OK);
		
	}
	
	@Secured("REGULARUSER")
	@RequestMapping(method = RequestMethod.DELETE, path = "/user/{userId}/rec/{recId}") //PRINCIPAL PI A NE USER ID
	public ResponseEntity<?> removeLikedRecipe(@PathVariable Long userId, @PathVariable Long recId) {
		return new ResponseEntity<>(regUserService.removeRecipe(userId, recId), HttpStatus.ACCEPTED);
	}
	
	
}
