package com.praksa.KitchenBackEnd.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.praksa.KitchenBackEnd.models.dto.RegularUserRegisterDTO;
import com.praksa.KitchenBackEnd.models.entities.AffectedUsers;
import com.praksa.KitchenBackEnd.models.entities.LikedRecipes;
import com.praksa.KitchenBackEnd.models.entities.LimitingFactor;
import com.praksa.KitchenBackEnd.models.entities.Recipe;
import com.praksa.KitchenBackEnd.models.entities.RegularUser;
import com.praksa.KitchenBackEnd.repositories.AffectedUserRepository;
import com.praksa.KitchenBackEnd.repositories.LikedRecipesRepository;
import com.praksa.KitchenBackEnd.repositories.LimitingFactorRepository;
import com.praksa.KitchenBackEnd.repositories.RecipeRepository;
import com.praksa.KitchenBackEnd.repositories.RegularUserRepository;
import com.praksa.KitchenBackEnd.repositories.UserRepository;


@Service
public class RegularUserServiceImpl implements RegularUserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RecipeRepository recipeRepo;
	
	@Autowired
	private LimitingFactorRepository limFactorRepo;
	
	@Autowired
	private RegularUserRepository regUserRepo;
	
	@Autowired
	private AffectedUserRepository affectedUsersRepo;
	
	@Autowired
	private LikedRecipesRepository likedRecRepo;
	
	
	
	
	//=========================FOR LIMITING FACTORS=======================//
	
	
	
	@Override
	public RegularUserRegisterDTO getLimFactors(String username) {
		RegularUser user = (RegularUser) userRepository.findByUsername(username);
		RegularUserRegisterDTO dto = new RegularUserRegisterDTO();
		
		for(AffectedUsers a : affectedUsersRepo.findAll()) {
			if(user.getId().equals(a.getRegularUser().getId()))
				dto.getMyLimitigFactors().add(a.getLimitingFactor().getName());
		}
		
		
		return dto;
	}
	
	
	
	@Override
	public Set<LimitingFactor> getLimitingFactors(String username) {
		RegularUser user = (RegularUser) userRepository.findByUsername(username);
		Set<LimitingFactor> lf = new HashSet<>();
		for(AffectedUsers af : affectedUsersRepo.findAll()) {
			if(user.getId().equals(af.getRegularUser().getId())) {
				lf.add(af.getLimitingFactor());
			}
		}
		return lf;
	}

	@Override
	public AffectedUsers addLimitingFactor(String username, Long lfId) {
		RegularUser user = (RegularUser) userRepository.findByUsername(username);
		Optional<AffectedUsers> af = affectedUsersRepo.findByRegularUserIdAndLimitingFactorId(user.getId(), lfId);
		
		if(af.isEmpty()) {
		RegularUser nUser = (RegularUser) userRepository.findByUsername(username);
		LimitingFactor lf = limFactorRepo.findById(lfId).get();
		AffectedUsers aff = new AffectedUsers();
		aff.setLimitingFactor(lf);
		aff.setRegularUser(nUser);
		affectedUsersRepo.save(aff);
		return aff;
		} else {
		
		return null;	
		}
	}
	
	
	@Override
	public Optional<AffectedUsers> removeLimitingFactor(Long lfId, String username) {
		RegularUser user = (RegularUser) userRepository.findByUsername(username);
		Optional<AffectedUsers> af = affectedUsersRepo.findByRegularUserIdAndLimitingFactorId(user.getId(), lfId);
		if(af.isPresent()) {
			affectedUsersRepo.delete(af.get());
			return af;
		} else {
		return null;
		}
	}
	
	
	
	//=========================FOR RECIPES=========================//
	
	@Override
	public Set<Recipe> getUserRecipes(Long userId) {
		Set<LikedRecipes> likedRecipes = likedRecRepo.findByRegularUserId(userId);
		Set<Recipe> recipes = likedRecipes.stream().map(e -> e.getRecipe()).collect((Collectors.toSet()));
		
		return recipes;
	}

	
	
	@Override
	public Recipe addRecipeToUser(String username, Long recipeId) {
		RegularUser userCheck = (RegularUser) userRepository.findByUsername(username);
		Optional<LikedRecipes> likedRecipe = likedRecRepo.findByRegularUserIdAndRecipeId(userCheck.getId(), recipeId);
		if(likedRecipe.isEmpty()) {
			LikedRecipes likesRecipe = new LikedRecipes();
			Recipe rec = recipeRepo.findById(recipeId).get();
			RegularUser user = (RegularUser) userRepository.findByUsername(username);
			likesRecipe.setRegularUser(user);
			likesRecipe.setRecipe(rec);
			likedRecRepo.save(likesRecipe);
			return rec;
		} else {
		
		return null;
		}
	}

	@Override
	public Optional<LikedRecipes> removeRecipe(String username, Long recId) {
		RegularUser user = (RegularUser) userRepository.findByUsername(username);
		Optional<LikedRecipes> likesRecipe = likedRecRepo.findByRegularUserIdAndRecipeId(user.getId(), recId);
		if(likesRecipe.isPresent()) {
			likedRecRepo.deleteById(likesRecipe.get().getId());
			return likesRecipe;
		} else {
		
		return null;
		}
	}

	
	
	
	
}
