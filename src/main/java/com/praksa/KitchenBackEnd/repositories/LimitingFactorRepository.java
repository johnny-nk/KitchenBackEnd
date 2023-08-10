package com.praksa.KitchenBackEnd.repositories;


import org.springframework.data.repository.CrudRepository;

import com.praksa.KitchenBackEnd.models.entities.LimitingFactor;

public interface LimitingFactorRepository extends CrudRepository<LimitingFactor, Long> {

	
	public LimitingFactor findByName(String name);
	public Boolean existsByName(String name);

}
