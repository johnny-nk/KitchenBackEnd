package com.praksa.KitchenBackEnd.services;

import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.HashSet;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.praksa.KitchenBackEnd.controllers.factory.UserFactory;
import com.praksa.KitchenBackEnd.models.dto.AdminRegisterDTO;
import com.praksa.KitchenBackEnd.models.dto.CookRegisterDTO;
import com.praksa.KitchenBackEnd.models.dto.RegularUserRegisterDTO;
import com.praksa.KitchenBackEnd.models.entities.Administrator;
import com.praksa.KitchenBackEnd.models.entities.AffectedUsers;
import com.praksa.KitchenBackEnd.models.entities.Cook;
import com.praksa.KitchenBackEnd.models.entities.LikedRecipes;
import com.praksa.KitchenBackEnd.models.entities.RegularUser;
import com.praksa.KitchenBackEnd.models.entities.User;
import com.praksa.KitchenBackEnd.repositories.AffectedUserRepository;
import com.praksa.KitchenBackEnd.repositories.CookRepository;
import com.praksa.KitchenBackEnd.repositories.LikedRecipesRepository;
import com.praksa.KitchenBackEnd.repositories.LimitingFactorRepository;
import com.praksa.KitchenBackEnd.repositories.RecipeRepository;
import com.praksa.KitchenBackEnd.repositories.RegularUserRepository;
import com.praksa.KitchenBackEnd.repositories.UserRepository;
import com.praksa.KitchenBackEnd.runtimeException.UserNotFoundException;







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
	@Autowired
	private AffectedUserRepository affUsersRepo;
	@Autowired
	private RecipeRepository recipeRepo;
	@Autowired
	private LimitingFactorRepository limFactorRepo;
	@Autowired
	private RecipeService recipeService;
	
	
	
	private RegularUserRegisterDTO userFormater(RegularUser user) {
		RegularUserRegisterDTO dto = new RegularUserRegisterDTO();
		dto.setId(user.getId());
		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		dto.setEmail(user.getEmail());
		dto.setUsername(user.getUsername());
		dto.setRole(user.getRole());
		dto.setMyCookbook(recipeService.myCookbook(user.getUsername()));
		dto.setMyLimitigFactors(user.getLimitingFactor().stream().map(e->e.getLimitingFactor().getName()).collect(Collectors.toSet()));

		return dto;
	}
	
	
	/*----------------GET--------------------*/
	@Override
	public Iterable<User> getAll() {
		return userRepository.findAll();
	}
	
	@Override
	public RegularUser getUserById(Long id) {
		RegularUser user = (RegularUser) userRepository.findById(id).get();
		return user;
	}
	
	
	@Override
	public RegularUserRegisterDTO getUser(Long id) {
		RegularUser user = (RegularUser) userRepository.findById(id).get();		
		return userFormater(user);
	}

	public User getUserByUsername(String username) {
		User user = userRepository.findByUsername(username);
		return user;
	}
	

	

	/*------------------POST-----------------*/
	@Override
	public RegularUser addUser(RegularUserRegisterDTO dto) {
		RegularUser regUser = (RegularUser) UserFactory.createUser(dto);
//		LikedRecipes likes = new LikedRecipes();
//		likedRecipesRepository.save(likes);
//		regUser.setLikedRecipes(likes);
		userRepository.save(regUser);
		return regUser;
		
	}

	@Override
	public Administrator addAdmin(AdminRegisterDTO dto) {
		Administrator admin = (Administrator) UserFactory.createUser(dto);
		userRepository.save(admin);
		return admin;
	}

	@Override
	public Cook addCook(CookRegisterDTO dto) {
		Cook cook = (Cook) UserFactory.createUser(dto);
		userRepository.save(cook);
		return cook;
	}

	@Override
	public RegularUser updateRegularUser(RegularUserRegisterDTO dto, Long id) {
		Optional<RegularUser> regularUser = regularUserRepository.findById(id);
		if (regularUser.isPresent()) {
			RegularUser updateRegularUser = regularUser.get();
			updateRegularUser.setFirstName(dto.getFirstName());
			updateRegularUser.setLastName(dto.getLastName());
			updateRegularUser.setPassword(dto.getPassword());
			updateRegularUser.setUsername(dto.getUsername());
			return regularUserRepository.save(updateRegularUser);
		}else {
			throw new UserNotFoundException();
		}
	}

	@Override
	public RegularUserRegisterDTO updateUser(RegularUserRegisterDTO updateUser, String username) {

		RegularUser user = (RegularUser) userRepository.findByUsername(username);
		
		if(updateUser.getUsername() != null && !updateUser.getUsername().equals(user.getUsername())) {
			user.setUsername(updateUser.getUsername());
		}
		if(updateUser.getPassword() != null && !updateUser.getPassword().equals(user.getPassword())) {
			user.setPassword(updateUser.getPassword());
		}
		if(updateUser.getFirstName() != null && !updateUser.getFirstName().equals(user.getFirstName())) {
			user.setFirstName(updateUser.getFirstName());
		}
		if(updateUser.getLastName() != null && !updateUser.getLastName().equals(user.getLastName())) {
			user.setLastName(updateUser.getLastName());
		}
		if(updateUser.getEmail() != null && !updateUser.getEmail().equals(user.getEmail())) {
			user.setEmail(updateUser.getEmail());
		}
		
		
		Set<LikedRecipes> usersRecipes = likedRecipesRepository.findByRegularUserId(user.getId());
		Set<LikedRecipes> updateRecipes = new HashSet<>();
		Set<Long> intSet = new HashSet<>();
		for(Long recipeId : updateUser.getFavRecipesId()) {			
			intSet.add(recipeId);	
		}
		if(intSet.equals(null)) {
			likedRecipesRepository.deleteAllByRegularUserId(user.getId());
		} 
		
		if (intSet != null) {
		for(Long recId : intSet) {
				LikedRecipes newLike = new LikedRecipes(null, user, recipeRepo.findById(recId).get());
				updateRecipes.add(newLike);
			}
		}
		
		Set<AffectedUsers> usersLimFactors = affUsersRepo.findByRegularUserId(user.getId());
		Set<AffectedUsers> updateLimFactors = new HashSet<>();
		Set<Long> lfSet = new HashSet<>();
		for(Long limId : updateUser.getMyLimFactorsId()) {
			lfSet.add(limId);
		}
		if(lfSet.equals(null)) {
			affUsersRepo.deleteAllByRegularUserId(user.getId());
		}
		if(lfSet != null) {
			for(Long lf : lfSet) {
				AffectedUsers affUser = new AffectedUsers(null, user, limFactorRepo.findById(lf).get());
				updateLimFactors.add(affUser);
			}
		}
		
		regularUserRepository.save(user);
		affUsersRepo.deleteAll(usersLimFactors);
		affUsersRepo.saveAll(updateLimFactors);
		likedRecipesRepository.deleteAll(usersRecipes);
		likedRecipesRepository.saveAll(updateRecipes);
		return updateUser;
	}
	
	
	@Override
	public RegularUser deleteRegularUser(Long id) {
		Optional<RegularUser> deleteRegularUser = regularUserRepository.findById(id);
		if(deleteRegularUser.isPresent()) {
			regularUserRepository.delete(deleteRegularUser.get());
		}else {
			throw new UserNotFoundException();
		}
		return null;
	}

	@Override
	public RegularUser getRegularUserById(Long id) {
		Optional<RegularUser> getRegularUser = regularUserRepository.findById(id);
		if(getRegularUser.isPresent()) {
			return getRegularUser.get();
		}else {
			throw new UserNotFoundException();
		}
	}

	@Override
	public Iterable<RegularUser> getAllRegluarUsers() {
		Iterable<RegularUser> getAllRegluarUsers = regularUserRepository.findAll();
		if(getAllRegluarUsers !=null) {
			return getAllRegluarUsers;
		}else {
			throw new UserNotFoundException();
		}
	}

	@Override
	public Cook getCookById(Long id) {
		Optional<Cook> getCookbyId = cookRepository.findById(id);
		if(getCookbyId.isPresent()) {
			return getCookbyId.get();
		}else {
			throw new UserNotFoundException();
		}
	}

	@Override
	public Iterable<Cook> getAllCooks() {
		Iterable<Cook> getAllCooks = cookRepository.findAll();
		if(getAllCooks !=null) {
			return getAllCooks;
		}else {
			throw new UserNotFoundException();
		}
	}

	@Override
	public Cook deleteCook(Long id) {
		Optional<Cook> deleteCook = cookRepository.findById(id);
		if(deleteCook.isPresent()) {
			cookRepository.delete(deleteCook.get());
		}else {
			throw new UserNotFoundException();
		}
		return null;
	}

	@Override
	@Transactional
	public Cook updateCook(CookRegisterDTO dto, Long id) {
		Optional<Cook> cook = cookRepository.findById(id);
		if (cook.isPresent()) {
			Cook updateCook = cook.get();
			updateCook.setFirstName(dto.getFirstName());
			updateCook.setLastName(dto.getLastName());
			updateCook.setPassword(dto.getPassword());
			updateCook.setUsername(dto.getUsername());
			updateCook.setAboutMe(dto.getAboutMe());
			return cookRepository.save(updateCook);
		}else {
			throw new UserNotFoundException();
		}
	}


	@Override
	public List<String> getUsernames() {
	    Iterable<User> allUsers = userRepository.findAll();
	    List<String> usernames = new ArrayList<>();

	    for (User user : allUsers) {
	        usernames.add(user.getUsername());
	    }

	    return usernames;
	}



	
	
	
}
	
	

