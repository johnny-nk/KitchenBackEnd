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

import com.praksa.KitchenBackEnd.models.dto.AdminRegisterDTO;
import com.praksa.KitchenBackEnd.models.dto.CookRegisterDTO;
import com.praksa.KitchenBackEnd.models.dto.RegularUserRegisterDTO;
import com.praksa.KitchenBackEnd.models.entities.Cook;
import com.praksa.KitchenBackEnd.models.entities.RegularUser;
import com.praksa.KitchenBackEnd.models.entities.User;
import com.praksa.KitchenBackEnd.runtimeException.UserNotFoundException;
import com.praksa.KitchenBackEnd.services.UserService;

@RestController
@RequestMapping(path = "api/v1/project/register")
public class UserRegisterController {
	
	
	
	@Autowired
	UserService userService;
	
	
	
	
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/admin")
	public ResponseEntity<?> registerAdmin(@Valid @RequestBody AdminRegisterDTO adminDTO) {
		
		return new ResponseEntity<>(userService.addAdmin(adminDTO), HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/regUser")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<?> registerRegUser(@Valid @RequestBody RegularUserRegisterDTO regUserDTO) {
		
		return new ResponseEntity<>(userService.addUser(regUserDTO), HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.POST, value ="/cook")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<?> registerCook(@Valid @RequestBody CookRegisterDTO cookDTO) {
		
		return new ResponseEntity<>(userService.addCook(cookDTO), HttpStatus.CREATED);
	}
	// ------------------------Update za Regular Usera --------------------------------//
	
	@Secured({"REGULARUSER", "ADMINISTRATOR"})
	@RequestMapping(method = RequestMethod.PUT, value="/updateUser") 
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<?> updateRegularUser(@Valid @RequestBody RegularUserRegisterDTO updateRegularUser,Principal p){
		try {
			RegularUserRegisterDTO regUser = userService.updateUser(updateRegularUser, p.getName());
			return new ResponseEntity<>(regUser , HttpStatus.OK);
	    } catch (UserNotFoundException e) {
	        return new ResponseEntity<>("User not found with username: " + p.getName(), HttpStatus.NOT_FOUND);
	    } catch (Exception e) {
	        return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	
	}
	@Secured("ADMINISTRATOR")
	@RequestMapping(method = RequestMethod.PUT, value="/adminUpdateUser/{userId}") 
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<?> updateRegularUser(@Valid @RequestBody RegularUserRegisterDTO updateRegularUser, @PathVariable Long userId){
		try {
			RegularUserRegisterDTO regUser = userService.adminUpdateUser(updateRegularUser, userId);
			return new ResponseEntity<>(regUser , HttpStatus.OK);
	    } catch (UserNotFoundException e) {
	        return new ResponseEntity<>("User not found with this id." , HttpStatus.NOT_FOUND);
	    } catch (Exception e) {
	        return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	
	}
	
	//----------------------Delete za Regular Usera------------------------------------//
	@Secured("ADMINISTRATOR")
	@RequestMapping(method = RequestMethod.DELETE, value="/deleteRegUserFromDB/{id}")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<?> deleteRegluarUser(@PathVariable Long id){
		try {	
			 userService.deleteRegularUser(id);
			return new ResponseEntity<>("User was deleted", HttpStatus.OK);
		  } catch (UserNotFoundException e) {
		        return new ResponseEntity<>("User not found with id: " + id, HttpStatus.NOT_FOUND);
		    } catch (Exception e) {
		        return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
		    }
	}
	
	
	//----------------------GET za Regular Usera po Id-ju-------------------------------------//
	@Secured({"REGULARUSER", "ADMINISTRATOR"})
	@RequestMapping(method = RequestMethod.GET, value="/getRegUser/{id}")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<?> getRegUserById(@PathVariable Long id){
		try {
			RegularUser regUser = userService.getRegularUserById(id);
			return new ResponseEntity<>(regUser , HttpStatus.OK);
		  } catch (UserNotFoundException e) {
		        return new ResponseEntity<>("User not found with id: " + id, HttpStatus.NOT_FOUND);
		    } catch (Exception e) {
		        return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
		    }
	}
	
	@Secured({"ADMINISTRATOR", "REGULARUSER"})
	@RequestMapping(method = RequestMethod.GET, value = "/getUser/{id}")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<?> getUser(@PathVariable Long id) {
		
		return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
	}
	
	@Secured("REGULARUSER")
	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(method = RequestMethod.GET, value = "/getLoggedInUser/{username}")
	public ResponseEntity<?> getLoggedInUser(@PathVariable String username) {
		return new ResponseEntity<>(userService.getLoggedInUser(username), HttpStatus.OK);
	}
	
	//----------------------GET za sve Regluar Usere-------------------------------------//
	@Secured("ADMINISTRATOR")
	@RequestMapping(method = RequestMethod.GET, value="/getUsers")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<?> getAllRegUses(){
		try {
			Iterable<RegularUser> regUsers = userService.getAllRegluarUsers();
			return new ResponseEntity<>(regUsers , HttpStatus.OK);
		  } catch (UserNotFoundException e) {
		        return new ResponseEntity<>("Users not found", HttpStatus.NOT_FOUND);
		    } catch (Exception e) {
		        return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
		    }
	}
	//----------------------GET za COOK po id-ju-------------------------------------//

	@RequestMapping(method = RequestMethod.GET, value="/getCook/{id}")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<?> getCookById(@PathVariable Long id){
		try {
			Cook getCookbyId = userService.getCookById(id);
			return new ResponseEntity<>(getCookbyId , HttpStatus.OK);
		  } catch (UserNotFoundException e) {
		        return new ResponseEntity<>("Cook not found with id: " + id, HttpStatus.NOT_FOUND);
		    } catch (Exception e) {
		        return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
		    }
	}
	//----------------------GET za sve Cooks-------------------------------------//
	@RequestMapping(method = RequestMethod.GET, value="/getCooks")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<?> getAllCooks(){
		try {
			Iterable<Cook> getAllCooks = userService.getAllCooks();
			return new ResponseEntity<>(getAllCooks , HttpStatus.OK);
		  } catch (UserNotFoundException e) {
		        return new ResponseEntity<>("Users not found", HttpStatus.NOT_FOUND);
		    } catch (Exception e) {
		        return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
		    }
	}
	
	//----------------------Delete za COOK------------------------------------//
	@RequestMapping(method = RequestMethod.DELETE, value="/deleteCook/{id}")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<?> deleteCook(@PathVariable Long id){
		try {	
			userService.deleteCook(id);
			return new ResponseEntity<>("Cook was deleted", HttpStatus.OK);
		  } catch (UserNotFoundException e) {
		        return new ResponseEntity<>("User not found with id: " + id, HttpStatus.NOT_FOUND);
		    } catch (Exception e) {
		        return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
		    }
	}
	
	//----------------------Update za COOK------------------------------------//
	@RequestMapping(method = RequestMethod.PUT, value="/updateCook/{id}")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<?> updateCook(@Valid @RequestBody CookRegisterDTO updateCook,@PathVariable Long id){
		try {
			CookRegisterDTO cook = userService.updateCook(updateCook, id);
			return new ResponseEntity<>(cook , HttpStatus.OK);
	    } catch (UserNotFoundException e) {
	        return new ResponseEntity<>("Cook not found with id: " + id, HttpStatus.NOT_FOUND);
	    } catch (Exception e) {
	        return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value="/all")	
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<?> getAll(){
		try {
			Iterable<User> getAll = userService.getAll();
			return new ResponseEntity<>(getAll , HttpStatus.OK);
		  } catch (UserNotFoundException e) {
		        return new ResponseEntity<>("Users not found", HttpStatus.NOT_FOUND);
		    } catch (Exception e) {
		        return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
		    }
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/allbyUserName")	
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<?> getAllbyUserName(){
		try {
			Iterable<String> getAll = userService.getUsernames();
			return new ResponseEntity<>(getAll , HttpStatus.OK);
		  } catch (UserNotFoundException e) {
		        return new ResponseEntity<>("Users not found", HttpStatus.NOT_FOUND);
		    } catch (Exception e) {
		        return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
		    }
	}
	
	
}
