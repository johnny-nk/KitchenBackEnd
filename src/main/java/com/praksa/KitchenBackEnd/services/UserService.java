package com.praksa.KitchenBackEnd.services;

import java.util.List;

import com.praksa.KitchenBackEnd.models.dto.AdminRegisterDTO;
import com.praksa.KitchenBackEnd.models.dto.CookRegisterDTO;
import com.praksa.KitchenBackEnd.models.dto.RegularUserRegisterDTO;
import com.praksa.KitchenBackEnd.models.entities.Administrator;
import com.praksa.KitchenBackEnd.models.entities.Cook;
import com.praksa.KitchenBackEnd.models.entities.RegularUser;
import com.praksa.KitchenBackEnd.models.entities.User;

public interface UserService {
	
	/*------------GET----------------*/
	
	public Iterable<User> getAll();
	public RegularUser getUserById(Long id);
	public RegularUserRegisterDTO getUser(Long id);
	/*------------POST-------------*/
	
	
	
	public RegularUser addUser(RegularUserRegisterDTO dto);
	public Administrator addAdmin(AdminRegisterDTO dto);
	public Cook addCook(CookRegisterDTO dto);

	/*-----------DELETE-------------*/
	public RegularUser deleteRegularUser(Long id);	
	public Cook deleteCook(Long id);
	/*------------PUT--------------*/
	public RegularUser updateRegularUser(RegularUserRegisterDTO dto, Long id);
	public Cook updateCook(CookRegisterDTO dto, Long id);
	/*-----------GET-------------*/
	public RegularUser getRegularUserById(Long id);
	public Iterable<RegularUser> getAllRegluarUsers();
	/*-----------GET COOK-------------*/
	public Cook getCookById(Long id);
	
	public Iterable<Cook> getAllCooks();
	
	public User getUserByUsername(String username);
	public List<String> getUsernames();
	

}
	

