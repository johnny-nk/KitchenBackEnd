
package com.praksa.KitchenBackEnd.services;


import com.praksa.KitchenBackEnd.models.dto.LimFactorDTO;
import com.praksa.KitchenBackEnd.models.entities.LimitingFactor;

public interface LimitingFactorService {
	
	
	public Iterable<LimFactorDTO> getLF();
	
	public LimitingFactor getLimitingFactorbyId(Long id);
	public Iterable<LimitingFactor> getAllLimitingFactors();
	public LimitingFactor  addLimitingFactor(LimFactorDTO limDTO, Long ingredientId);
	public LimitingFactor updateLimitingFactor( Long id,  LimFactorDTO limDTO);
	public LimitingFactor deleteLimitingFactor (Long id);
	
	
	
	
}