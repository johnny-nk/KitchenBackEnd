package com.praksa.KitchenBackEnd.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.praksa.KitchenBackEnd.models.entities.AffectedUsers;

public interface AffectedUserRepository extends CrudRepository<AffectedUsers, Long> {
	
//	public List<AffectedUsers> findByLimitingFactorIdAndRegularUserId(Long limId, Long userId);
	
	public Optional<AffectedUsers> findByRegularUserIdAndLimitingFactorId(Long userId, Long lfId);
}
