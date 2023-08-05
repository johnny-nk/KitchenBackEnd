package com.praksa.KitchenBackEnd.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.praksa.KitchenBackEnd.controllers.factory.UserFactory;
import com.praksa.KitchenBackEnd.models.dto.AdminRegisterDTO;
import com.praksa.KitchenBackEnd.models.dto.CookRegisterDTO;
import com.praksa.KitchenBackEnd.models.dto.RegularUserRegisterDTO;
import com.praksa.KitchenBackEnd.models.entities.Administrator;
import com.praksa.KitchenBackEnd.models.entities.Cook;
import com.praksa.KitchenBackEnd.models.entities.RegularUser;
import com.praksa.KitchenBackEnd.models.entities.User;
import com.praksa.KitchenBackEnd.repositories.CookRepository;
import com.praksa.KitchenBackEnd.repositories.LikedRecipesRepository;
import com.praksa.KitchenBackEnd.repositories.RegularUserRepository;
import com.praksa.KitchenBackEnd.repositories.UserRepository;
import com.praksa.KitchenBackEnd.runtimeException.UserNotFoundException;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RegularUserRepository regularUserRepository;
	@Autowired
	private CookRepository cookRepository;
	@Autowired
	private LikedRecipesRepository likedRecipesRepository;
	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	
	/*----------------GET--------------------*/
	@Override
	public Iterable<User> getAll() {
		logger.info("Listed all users.");
		return userRepository.findAll();
	}
	
	@Override
	public RegularUser getUserById(Long id) {
		RegularUser user = (RegularUser) userRepository.findById(id).get();
		logger.info("User with id = " + id + " found.");
		return user;
	}
	

	public User getUserByUsername(String username) {
		User user = userRepository.findByUsername(username);
		logger.info("User with username = " + username + " found.");
		return user;
	}
	

	

	/*------------------POST-----------------*/
	@Override
	public RegularUser addUser(RegularUserRegisterDTO dto) {
		logger.info("AddUser method invoked.");
		RegularUser regUser = (RegularUser) UserFactory.createUser(dto);
//		LikedRecipes likes = new LikedRecipes();
//		likedRecipesRepository.save(likes);
//		regUser.setLikedRecipes(likes);
		userRepository.save(regUser);
		logger.info("Finished adding new regular user.");
		return regUser;
		
	}

	@Override
	public Administrator addAdmin(AdminRegisterDTO dto) {
		logger.info("AddAdmin method invoked.");
		Administrator admin = (Administrator) UserFactory.createUser(dto);
		userRepository.save(admin);
		logger.info("Finished adding new admin.");
		return admin;
	}

	@Override
	public Cook addCook(CookRegisterDTO dto) {
		logger.info("AddCook method invoked.");
		Cook cook = (Cook) UserFactory.createUser(dto);
		userRepository.save(cook);
		logger.info("Finished adding new cook.");
		return cook;
	}

	@Override
	public RegularUser updateRegularUser(RegularUserRegisterDTO dto, Long id) {
		logger.info("UpdateRegularUser method invoked - user with id = " + id + ".");
		Optional<RegularUser> regularUser = regularUserRepository.findById(id);
		if (regularUser.isPresent()) {
			RegularUser updateRegularUser = regularUser.get();
			updateRegularUser.setFirstName(dto.getFirstName());
			updateRegularUser.setLastName(dto.getLastName());
			updateRegularUser.setPassword(dto.getPassword());
			updateRegularUser.setUsername(dto.getUsername());
			logger.info("Finished updating regular user with id = " + id + ".");
			return regularUserRepository.save(updateRegularUser);
		}else {
			logger.error("An error occured while updating regular user - user with id = " + id + " doesn't exist.");
			throw new UserNotFoundException();
		}
	}

	@Override
	public RegularUser deleteRegularUser(Long id) {
		logger.info("DeleteRegularUser method invoked - user with id = " + id + ".");
		Optional<RegularUser> deleteRegularUser = regularUserRepository.findById(id);
		if(deleteRegularUser.isPresent()) {
			logger.info("Finished deleting regular user with id = " + id + ".");
			regularUserRepository.delete(deleteRegularUser.get());
		}else {
			logger.error("An error occured while deleting regular user - user with id = " + id + " doesn't exist.");
			throw new UserNotFoundException();
		}
		return null;
	}

	@Override
	public RegularUser getRegularUserById(Long id) {
		logger.info("GetRegularUserById method invoked - user with id = " + id + ".");
		Optional<RegularUser> getRegularUser = regularUserRepository.findById(id);
		if(getRegularUser.isPresent()) {
			logger.info("Finished getting regular user with id = " + id + ".");
			return getRegularUser.get();
		}else {
			logger.error("An error occured while getting regular user by id - user with id = " + id + " doesn't exist.");
			throw new UserNotFoundException();
		}
	}

	@Override
	public Iterable<RegularUser> getAllRegluarUsers() {
		logger.info("GetAllRegluarUsers method invoked.");
		Iterable<RegularUser> getAllRegluarUsers = regularUserRepository.findAll();
		if(getAllRegluarUsers !=null) {
			logger.info("Finished getting all regular users.");
			return getAllRegluarUsers;
		}else {
			logger.error("An error occured while getting all regular users.");
			throw new UserNotFoundException();
		}
	}

	@Override
	public Cook getCookById(Long id) {
		logger.info("GetCookById method invoked.");
		Optional<Cook> getCookbyId = cookRepository.findById(id);
		if(getCookbyId.isPresent()) {
			logger.info("Finished getting cook with id = " + id + ".");
			return getCookbyId.get();
		}else {
			logger.error("An error occured while getting cook by id - cook with id = " + id + " doesn't exist.");
			throw new UserNotFoundException();
		}
	}

	@Override
	public Iterable<Cook> getAllCooks() {
		logger.info("GetAllCooks method invoked.");
		Iterable<Cook> getAllCooks = cookRepository.findAll();
		if(getAllCooks !=null) {
			logger.info("Finished getting all cooks.");
			return getAllCooks;
		}else {
			logger.error("An error occured while getting all cooks.");
			throw new UserNotFoundException();
		}
	}

	@Override
	public Cook deleteCook(Long id) {
		logger.info("DeleteCook method invoked - cook with id = " + id + ".");
		Optional<Cook> deleteCook = cookRepository.findById(id);
		if(deleteCook.isPresent()) {
			logger.info("Finished deleting cook with id = " + id + ".");
			cookRepository.delete(deleteCook.get());
		}else {
			logger.error("An error occured while deleting cook - cook with id = " + id + " doesn't exist.");
			throw new UserNotFoundException();
		}
		return null;
	}

	@Override
	@Transactional
	public Cook updateCook(CookRegisterDTO dto, Long id) {
		logger.info("UpdateCook method invoked - cook with id = " + id + ".");
		Optional<Cook> cook = cookRepository.findById(id);
		if (cook.isPresent()) {
			Cook updateCook = cook.get();
			updateCook.setFirstName(dto.getFirstName());
			updateCook.setLastName(dto.getLastName());
			updateCook.setPassword(dto.getPassword());
			updateCook.setUsername(dto.getUsername());
			updateCook.setAboutMe(dto.getAboutMe());
			logger.info("Finished updating cook with id = " + id + ".");
			return cookRepository.save(updateCook);
		}else {
			logger.error("An error occured while updating cook - cook with id = " + id + " doesn't exist.");
			throw new UserNotFoundException();
		}
	}


	@Override
	public List<String> getUsernames() {
		logger.info("GetUsernames method invoked.");
	    Iterable<User> allUsers = userRepository.findAll();
	    List<String> usernames = new ArrayList<>();

	    for (User user : allUsers) {
			usernames.add(user.getUsername());
	    }
	    logger.info("Finished getting all usernames.");
	    return usernames;
	}



	
	
	
}
	
	

