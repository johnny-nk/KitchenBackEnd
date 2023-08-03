package com.praksa.KitchenBackEnd.repositories;

import org.springframework.data.repository.CrudRepository;

import com.praksa.KitchenBackEnd.models.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	public User findByUsername(String username);
	public Iterable<User> findAll();
}
