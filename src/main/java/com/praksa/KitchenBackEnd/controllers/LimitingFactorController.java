
package com.praksa.KitchenBackEnd.controllers;

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
import com.praksa.KitchenBackEnd.models.dto.LimFactorDTO;
import com.praksa.KitchenBackEnd.models.entities.Ingredient;
import com.praksa.KitchenBackEnd.models.entities.LimitingFactor;
import com.praksa.KitchenBackEnd.services.LimitingFactorService;

@RestController
@RequestMapping(path = "/api/v1/project/limitingFactor")
public class LimitingFactorController {

	@Autowired
	private LimitingFactorService limitingFactorService;
	
	
	@Secured("ADMINISTRATOR")
	@RequestMapping(method = RequestMethod.POST, value = "/newlimitingFactor/{Id}") //PRINCIPAL P UMESTO ID
	public ResponseEntity<?> addNewLimitingFactor(@Valid @RequestBody LimFactorDTO limDTO, @PathVariable Long Id) {
		try {
			return new ResponseEntity<>(limitingFactorService.addLimitingFactor(limDTO, Id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "BAD Request"),
					HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
	
	@Secured("ADMINISTRATOR")
	@RequestMapping(method = RequestMethod.PUT, value = "/updateLimitingFactor/{Id}") //PRINCIPAL P UMESTO ID
	public ResponseEntity<?> updateLimitingFactor(@Valid @PathVariable Long Id, @RequestBody LimFactorDTO limDTO) {
		try {
			LimitingFactor updatedLimitingFactor = limitingFactorService.updateLimitingFactor(Id, limDTO);
			if (updatedLimitingFactor != null) {
				return ResponseEntity.ok(updatedLimitingFactor);
			} else {
				return new ResponseEntity<>(new RESTError(HttpStatus.NOT_FOUND.value(), "Limiting Factor not found"),
						HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "BAD Request"),
					HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
	
	
	//????????
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> getLimitingFactorbyId(@PathVariable Long id) {
		try {
			LimitingFactor getLimitingFactorbyId = limitingFactorService.getLimitingFactorbyId(id);
			if (getLimitingFactorbyId != null) {
				return new ResponseEntity<>(getLimitingFactorbyId, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new RESTError(HttpStatus.NOT_FOUND.value(), "Limiting Factor not found"),
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(
					new RESTError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@Secured({"ADMINISTRATOR", "REGULARUSER", "COOK"})
	@RequestMapping(method = RequestMethod.GET, value = "/all")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<?> getAllLimitingFactors() {
		try {
			Iterable<LimitingFactor> getAllLimitingFactor = limitingFactorService.getAllLimitingFactors();
			if (getAllLimitingFactor != null) {
				return new ResponseEntity<>(getAllLimitingFactor, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new RESTError(HttpStatus.NOT_FOUND.value(), "Limiting Factor not found"),
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(
					new RESTError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@Secured("ADMINISTRATOR")
	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteLimitingFactors/{id}")
	public ResponseEntity<?> deleteLimitingFactors(@PathVariable Long id) {
		LimitingFactor deleteLimitingFactors = limitingFactorService.deleteLimitingFactor(id);
		try {
			if (deleteLimitingFactors != null) {
				return new ResponseEntity<>("Limiting Factor was removed from db", HttpStatus.OK);
			} else {
				return new ResponseEntity<>(
						new RESTError(HttpStatus.NOT_FOUND.value(), "Limiting Factor with " + id + " not found"),
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(
					new RESTError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}