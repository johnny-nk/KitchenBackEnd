package com.praksa.KitchenBackEnd.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.praksa.KitchenBackEnd.models.dto.IngredientDTO;
import com.praksa.KitchenBackEnd.models.entities.Ingredient;
import com.praksa.KitchenBackEnd.models.entities.LimitingFactor;
import com.praksa.KitchenBackEnd.models.entities.LimitingIngredient;
import com.praksa.KitchenBackEnd.models.entities.RecipeIngredient;
import com.praksa.KitchenBackEnd.repositories.IngredientRepository;
import com.praksa.KitchenBackEnd.repositories.LimitingFactorRepository;
import com.praksa.KitchenBackEnd.repositories.LimitingIngredientRepository;
import com.praksa.KitchenBackEnd.repositories.RecipeIngredientRepository;

@Service
public class IngredientServiceImpl implements IngredientService {

	@Autowired
	private IngredientRepository ingredientRepository;
	@Autowired
	private LimitingIngredientRepository limitingIngredientRepo;
	@Autowired
	private RecipeIngredientRepository recipeIngredientRepository;

	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	

	@Autowired
	private LimitingFactorRepository limitingFactorRepository;

	@Override
	public Ingredient addIngredient(IngredientDTO ingredient) {
		logger.info("AddIngredient method invoked.");
		Ingredient ingredients = new Ingredient();
		ingredients.setCalories(ingredient.getCalories());
		ingredients.setCarbs(ingredient.getCarbs());
		ingredients.setFats(ingredient.getFats());
		ingredients.setName(ingredient.getName());
		ingredients.setProteins(ingredient.getProteins());
		ingredients.setSaturatedFats(ingredient.getSaturatedFats());
		ingredients.setSugars(ingredient.getSugars());
		ingredients.setUnit(ingredient.getUnit());
		logger.info("New ingredient added.");
		return ingredientRepository.save(ingredients);

	}

	@Override
	public Ingredient updateIngredient(IngredientDTO ingredientForUpdate, Long id) {
		logger.info("UpdateIngredient method invoked - ingredient with id = " + id + ".");
		Optional<Ingredient> ingredient = ingredientRepository.findById(id);
		if (ingredient.isPresent()) {
			Ingredient updateIngredient = ingredient.get();
			updateIngredient.setCalories(ingredientForUpdate.getCalories());
			updateIngredient.setCarbs(ingredientForUpdate.getCarbs());
			updateIngredient.setFats(ingredientForUpdate.getFats());
			updateIngredient.setName(ingredientForUpdate.getName());
			updateIngredient.setProteins(ingredientForUpdate.getProteins());
			updateIngredient.setSaturatedFats(ingredientForUpdate.getSaturatedFats());
			updateIngredient.setSugars(ingredientForUpdate.getSugars());
			updateIngredient.setUnit(ingredientForUpdate.getUnit());
			logger.info("Finished updating ingredient - ingredient with id = " + id + ".");
			return ingredientRepository.save(updateIngredient);
		} else {
			logger.info("An error occured while updating an ingredient.");
			return null;
		}
	}

	@Override
	public Ingredient getIngredientById(Long id) {
		logger.info("GetIngredientById method invoked - ingredient with id = " + id + ".");
		Optional<Ingredient> ingredientById = ingredientRepository.findById(id);
		if (ingredientById.isPresent()) {
			logger.info("Finished getting ingredient with id = " + id + ".");
			return ingredientById.get();
		} else {
			logger.info("An error occured while getting an ingredient with id = " + id + ".");
			return null;
		}
	}

	@Override
	public Iterable<Ingredient> getAllIngredients() {
		logger.info("GetAllIngredients method invoked.");
		Iterable<Ingredient> allIngredients = ingredientRepository.findAll();
		if (allIngredients != null) {
			logger.info("Finished getting all ingredients.");
			return allIngredients;
		}
		logger.info("An error occured while getting all ingredients.");
		return null;
	}

//	@Override
//	public Ingredient deleteIngredient(Long id) {
//        Ingredient deleteIngredient = ingredientRepository.findById(id).get();
//		if(deleteIngredient != null) {
//			  ingredientRepository.delete(deleteIngredient);
//			  return deleteIngredient; 
//		}
//		return null;
//	}

	@Override
	@Transactional
	public Ingredient deleteIngredient(Long id) {
		logger.info("DeleteIngredient method invoked - ingredient with id = " + id + ".");
		Ingredient ingredient = ingredientRepository.findById(id).orElseThrow();
		List<LimitingIngredient> limits = ingredient.getLimitingFactors();
		List<RecipeIngredient> recipes = recipeIngredientRepository.findByIngredientId(ingredient);
		recipeIngredientRepository.deleteAll(recipes);
		limitingIngredientRepo.deleteAll(limits);
		ingredientRepository.delete(ingredient);
		logger.info("Finished deleting ingredient with id = " + id + ".");
		return ingredient;
	}

	@Override
	public LimitingFactor connectIngredientToLimitingFactor(Long limitingFactorId, Long ingredientId) {
		logger.info("ConnectIngredientToLimitingFactor method invoked - ingredient with id = " + ingredientId + ", limiting factor with id = " + limitingFactorId + ".");
		Optional<LimitingFactor> getLimitingFactor = limitingFactorRepository.findById(limitingFactorId);
	    Optional<Ingredient> getIngredient = ingredientRepository.findById(ingredientId);
	    				// Proveravamo da li konekcija postoji // 
	    if (getLimitingFactor.isPresent() && getIngredient.isPresent()) {
	        LimitingFactor limitingFactor = getLimitingFactor.get();
	        Ingredient ingredient = getIngredient.get();       		
	        List<LimitingIngredient> existingConnections = limitingIngredientRepo.findByLimitingFactorAndIngredients(limitingFactor, ingredient);
	        //Ako konekcija postoji vracamo null i handlamo u Response entityu //
	        if (!existingConnections.isEmpty()) {
	        	return null;
	        }
	        //Ako postoji i postoje i limiting Faktor i Ingredient spajamo ih  //
            LimitingIngredient newLimitingIngredient = new LimitingIngredient();
            newLimitingIngredient.setIngredients(ingredient);
            newLimitingIngredient.setLimitingFactor(limitingFactor);
            limitingIngredientRepo.save(newLimitingIngredient);
            logger.info("Finished connecting ingredient to limiting factor - ingredient with id = " + ingredientId + ", limiting factor with id = " + limitingFactorId + ".");
	        return limitingFactor;
	    } else {
	    	logger.error("An error occured while connecting ingredient to limiting factor - ingredient with id = " + ingredientId + ", limiting factor with id = " + limitingFactorId + ".");
	        return null;
	    }
	}

	
	
	
	
	
	
	
	
	
	
	
	
}
