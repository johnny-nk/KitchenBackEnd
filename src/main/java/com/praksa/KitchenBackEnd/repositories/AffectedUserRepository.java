package com.praksa.KitchenBackEnd.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.praksa.KitchenBackEnd.models.entities.AffectedUsers;

public interface AffectedUserRepository extends CrudRepository<AffectedUsers, Long> {
	
//	public List<AffectedUsers> findByLimitingFactorIdAndRegularUserId(Long limId, Long userId);
	
	public Optional<AffectedUsers> findByRegularUserIdAndLimitingFactorId(Long userId, Long lfId);
	public Set<AffectedUsers> findByRegularUserId(Long id);
	
	public void deleteAllByRegularUserId(Long id);
}
