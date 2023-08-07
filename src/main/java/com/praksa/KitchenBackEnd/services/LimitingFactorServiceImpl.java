package com.praksa.KitchenBackEnd.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
	
	
	@Override
	public Iterable<LimFactorDTO> getLF() {
		List<LimFactorDTO> dto = new ArrayList<>();
		Iterable<LimitingFactor> lfs = limitingFactorRepository.findAll();
		for(LimitingFactor lf : lfs ) {
			LimFactorDTO l = new LimFactorDTO();
			l.setId(lf.getId());
			l.setName(lf.getName());
			dto.add(l);
		}
		return dto;
	}
	
	@Override
	public LimitingFactor getLimitingFactorbyId(Long id) {
		Optional<LimitingFactor> limitingFactorById = limitingFactorRepository.findById(id);
		if (limitingFactorById.isPresent()) {
			return limitingFactorById.get();
		} else {

			return null;
		}
	}

	@Override
	public Iterable<LimitingFactor> getAllLimitingFactors() {
		Iterable<LimitingFactor> allLimitingFactors = limitingFactorRepository.findAll();
		if (allLimitingFactors != null) {
			return allLimitingFactors;
		} else {

			return null;
		}
	}

	@Override
	public LimitingFactor addLimitingFactor(LimFactorDTO limDTO, Long ingredientId) {


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

			return newLimitingFactor;
		} else {
			return null;
		}

	}

	@Override
	public LimitingFactor updateLimitingFactor(Long id, LimFactorDTO limDTO) {
		Optional<LimitingFactor> updateLimitingFactor = limitingFactorRepository.findById(id);
		if (updateLimitingFactor.isPresent()) {
			LimitingFactor existingLimitingFactor = updateLimitingFactor.get();
			existingLimitingFactor.setName(limDTO.getName());
			LimitingFactor updatedLimitingFactor = limitingFactorRepository.save(existingLimitingFactor);
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
