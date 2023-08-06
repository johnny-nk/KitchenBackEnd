package com.praksa.KitchenBackEnd.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.praksa.KitchenBackEnd.models.dto.LimFactorDTO;
import com.praksa.KitchenBackEnd.models.entities.Ingredient;
import com.praksa.KitchenBackEnd.models.entities.LimitingFactor;
import com.praksa.KitchenBackEnd.models.entities.LimitingIngredient;
import com.praksa.KitchenBackEnd.repositories.IngredientRepository;
import com.praksa.KitchenBackEnd.repositories.LimitingFactorRepository;
import com.praksa.KitchenBackEnd.repositories.LimitingIngredientRepository;

@Service
public class LimitingFactorServiceImpl implements LimitingFactorService {

	@Autowired
	private LimitingFactorRepository limitingFactorRepository;
	@Autowired
	private IngredientRepository ingredientRepository;
	@Autowired
	private LimitingIngredientRepository limitingIngredientRepository;


	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());


	@Override
	public LimitingFactor getLimitingFactorbyId(Long id) {
		logger.info("GetLimitingFactorbyId method invoked - limiting factor with id = " + id + ".");
		Optional<LimitingFactor> limitingFactorById = limitingFactorRepository.findById(id);
		if (limitingFactorById.isPresent()) {
			logger.info("Limiting factor with id = " + id + " found.");
			return limitingFactorById.get();
		} else {
			logger.error("An error occured while getting limiting factor with id = " + id + ".");
			return null;
		}
	}

	@Override
	public Iterable<LimitingFactor> getAllLimitingFactors() {
		logger.info("GetAllLimitingFactors method invoked.");
		Iterable<LimitingFactor> allLimitingFactors = limitingFactorRepository.findAll();
		if (allLimitingFactors != null) {

			logger.info("All limiting factors listed.");
			return allLimitingFactors;
		} else {
			logger.error("An error occured while getting all limiting factors.");
			return null;
		}
	}

	@Override
	public LimitingFactor addLimitingFactor(LimFactorDTO limDTO, Long ingredientId) {
		logger.info("AddLimitingFactor method invoked - for ingredient with id = " + ingredientId + ".");
		LimitingFactor newLimitingFactor = new LimitingFactor();
		newLimitingFactor.setName(limDTO.getName());
		limitingFactorRepository.save(newLimitingFactor); // Save the LimitingFactor first

		Optional<Ingredient> ingredientsId = ingredientRepository.findById(ingredientId);
		if (ingredientsId.isPresent()) {
			Ingredient ingredient = ingredientsId.get();
			LimitingIngredient newLimitingIngredient = new LimitingIngredient();

			newLimitingIngredient.setIngredients(ingredient);
			newLimitingIngredient.setLimitingFactor(newLimitingFactor);
			
			limitingIngredientRepository.save(newLimitingIngredient);
			logger.info("Finished adding new limiting factor for ingredient with id = " + ingredientId + ".");
			return newLimitingFactor;
		} else {
			logger.error("An error occured while adding new limiting factor for ingredient with id = " + ingredientId + ".");
			return null;
		}

	}

	@Override
	public LimitingFactor updateLimitingFactor(Long id, LimFactorDTO limDTO) {

		logger.info("UpdateLimitingFactor method invoked - limiting factor with id = " + id + ".");
		Optional<LimitingFactor> updateLimitingFactor = limitingFactorRepository.findById(id);
		if (updateLimitingFactor.isPresent()) {
			LimitingFactor existingLimitingFactor = updateLimitingFactor.get();
			existingLimitingFactor.setName(limDTO.getName());
			LimitingFactor updatedLimitingFactor = limitingFactorRepository.save(existingLimitingFactor);

			logger.info("Finished updating limiting factor with id = " + id  + ".");
			return updatedLimitingFactor;
		} else {
		    logger.error("An error occured while updating limiting factor with id = " + id  + ".");
			return null;
		}

	}

	@Override
	public LimitingFactor deleteLimitingFactor(Long id) {
		logger.info("DeleteLimitingFactor method invoked - limiting factor with id = " + id + ".");
		Optional<LimitingFactor> getLimitingFactor = limitingFactorRepository.findById(id);
		if (getLimitingFactor.isPresent()) {
			LimitingFactor deleteLimitingFactor = getLimitingFactor.get();

			List<LimitingIngredient> associatedIngredients = limitingIngredientRepository
					.findByLimitingFactor(deleteLimitingFactor);
			limitingIngredientRepository.deleteAll(associatedIngredients);

			limitingFactorRepository.delete(deleteLimitingFactor);
			
			logger.info("Finished deleting limiting factor with id = " + id  + ".");
			return deleteLimitingFactor;
		} else {
			logger.error("An error occured while deleting limiting factor with id = " + id  + ".");
			return null;
		}

	}

}
