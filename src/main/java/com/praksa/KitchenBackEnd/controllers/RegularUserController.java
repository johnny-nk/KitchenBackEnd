package com.praksa.KitchenBackEnd.controllers;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.praksa.KitchenBackEnd.controllers.util.RESTError;
import com.praksa.KitchenBackEnd.models.dto.RegularUserRegisterDTO;
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
	
	
	
	
	@RequestMapping(method = RequestMethod.PUT, path = "/updateUser")
	public ResponseEntity<?> updatUser(@RequestBody RegularUserRegisterDTO dto, Principal p) {
//		System.out.println(p.getName().toString());
		return new ResponseEntity<>(userService.updateUser(dto, p.getName()), HttpStatus.OK);
	}
	
	
	@Secured("REGULARUSER")
	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(method = RequestMethod.GET, path = "/getLf")
	public ResponseEntity<?> getLimitingFactors(@Valid Principal p) {
		return new ResponseEntity<>(regUserService.getLimFactors(p.getName()), HttpStatus.OK);
	}
	
	@Secured("REGULARUSER")
	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(method = RequestMethod.POST, path = "/addLf/{lFid}")  
	public ResponseEntity<?> addLimitingFactor(@Valid @PathVariable Long lFid, Principal p) {
	    try {
	        return new ResponseEntity<>(regUserService.addLimitingFactor(p.getName(), lFid), HttpStatus.OK);
	    } catch (UserNotFoundException e) {
	        return new ResponseEntity<>(new RESTError(HttpStatus.NOT_FOUND.value(), "User not found with name: " + p.getName()), HttpStatus.NOT_FOUND);
	    } catch (LimitingFactorNotFoundException e) {
	        return new ResponseEntity<>(new RESTError(HttpStatus.NOT_FOUND.value(), "Limiting Factor not found with id: " + lFid), HttpStatus.NOT_FOUND);
	    } catch (Exception e) {
	        return new ResponseEntity<>(new RESTError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@Secured("REGULARUSER")
	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(method = RequestMethod.DELETE, path = "/deleteLf/{lfId}")
	public ResponseEntity<?> removeLimitingFactor(@PathVariable Long lfId, Principal p) {
		return new ResponseEntity<>(regUserService.removeLimitingFactor(lfId, p.getName()), HttpStatus.OK);
	}
	
	
	//=-==-=-==-=-==-==-=-==-=-==-==- USER'S LIKED RECIPES=-=-==-==-=-==-=-==-==-=-==-=-==-==-=-==-=-= //
	
	@Secured("REGULARUSER")
	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(method = RequestMethod.GET, path = "/myCookbook")
	public ResponseEntity<?> getMyCookbook(Principal p) {
		return new ResponseEntity<>(recipeService.myCookbook(p.getName()), HttpStatus.OK);
	}
	
	@Secured("REGULARUSER")
	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(method = RequestMethod.POST, path = "/rec/{recId}")
	public ResponseEntity<?> addRecToUser(@PathVariable Long recId, Principal p) {
		return new ResponseEntity<>(regUserService.addRecipeToUser(p.getName(), recId), HttpStatus.OK);
		
	}
	
	@Secured("REGULARUSER")
	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(method = RequestMethod.DELETE, path = "/rec/{recId}") 
	public ResponseEntity<?> removeLikedRecipe(@PathVariable Long recId, Principal p) {
		return new ResponseEntity<>(regUserService.removeRecipe(p.getName(), recId), HttpStatus.ACCEPTED);
	}
	
	
}
