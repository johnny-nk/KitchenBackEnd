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
		logger.info("GetLimitingFactorbyId method invoked.");
		Optional<LimitingFactor> limitingFactorById = limitingFactorRepository.findById(id);
		if (limitingFactorById.isPresent()) {
			logger.info("Limiting factor with id= " + id + " found.");
			return limitingFactorById.get();
		} else {
			logger.error("An error occured while getting limiting factor with id= " + id + ".");
			return limitingFactorById.get();
		} else {
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
			return allLimitingFactors;
		} else {
			return null;
		}
	}

	@Override
	public LimitingFactor addLimitingFactor(LimFactorDTO limDTO, Long ingredientId) {
		logger.info("AddLimitingFactor method invoked.");
		LimitingFactor newLimitingFactor = new LimitingFactor();
		newLimitingFactor.setName(limDTO.getName());
		limitingFactorRepository.save(newLimitingFactor); // Save the LimitingFactor first


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
			logger.info("New limiting factor added.");
			return newLimitingFactor;
		} else {
			logger.error("An error occured while adding new limiting factor.");


			limitingIngredientRepository.save(newLimitingIngredient);

			return newLimitingFactor;
		} else {

			return null;
		}

	}

	@Override
	public LimitingFactor updateLimitingFactor(Long id, LimFactorDTO limDTO) {

		logger.info("UpdateLimitingFactor method invoked.");


		Optional<LimitingFactor> updateLimitingFactor = limitingFactorRepository.findById(id);
		if (updateLimitingFactor.isPresent()) {
			LimitingFactor existingLimitingFactor = updateLimitingFactor.get();
			existingLimitingFactor.setName(limDTO.getName());
			LimitingFactor updatedLimitingFactor = limitingFactorRepository.save(existingLimitingFactor);

			logger.info("Limiting factor updated.");
			return updatedLimitingFactor;
		} else {
			logger.error("An error occured while updating imiting factor.");

			return updatedLimitingFactor;
		} else {

			return null;
		}

	}

	@Override
	public LimitingFactor deleteLimitingFactor(Long id) {
		Optional<LimitingFactor> getLimitingFactor = limitingFactorRepository.findById(id);
		if (getLimitingFactor.isPresent()) {
			LimitingFactor deleteLimitingFactor = getLimitingFactor.get();

			List<LimitingIngredient> associatedIngredients = limitingIngredientRepository
					.findByLimitingFactor(deleteLimitingFactor);
			limitingIngredientRepository.deleteAll(associatedIngredients);

			limitingFactorRepository.delete(deleteLimitingFactor);

			return deleteLimitingFactor;
		} else {
			return null;
		}

	}

}
