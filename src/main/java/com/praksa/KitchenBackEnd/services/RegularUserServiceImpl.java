package com.praksa.KitchenBackEnd.services;

import java.util.HashSet;
import java.util.List;
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
	public Set<LimitingFactor> getLimitingFactors(Long userId) {
		RegularUser user = (RegularUser) userRepository.findById(userId).get();
		Set<LimitingFactor> lf = new HashSet<>();
		for(AffectedUsers af : affectedUsersRepo.findAll()) {
			if(user.getId().equals(af.getRegularUser().getId())) {
				lf.add(af.getLimitingFactor());
			}
		}
		return lf;
	}

	@Override
	public AffectedUsers addLimitingFactor(Long userId, Long lfId) {
		Optional<AffectedUsers> af = affectedUsersRepo.findByRegularUserIdAndLimitingFactorId(userId, lfId);
		
		if(af.isEmpty()) {
		RegularUser user = (RegularUser) userRepository.findById(userId).get();
		LimitingFactor lf = limFactorRepo.findById(lfId).get();
		AffectedUsers aff = new AffectedUsers();
		aff.setLimitingFactor(lf);
		aff.setRegularUser(user);
		affectedUsersRepo.save(aff);
		return aff;
		} else {
		
		return null;	
		}
	}
	
	
	@Override
	public Optional<AffectedUsers> removeLimitingFactor(Long userId, Long lfId) {		
		Optional<AffectedUsers> af = affectedUsersRepo.findByRegularUserIdAndLimitingFactorId(userId, lfId);
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
	public Recipe addRecipeToUser(Long userId, Long recipeId) {
		
		Optional<LikedRecipes> likedRecipe = likedRecRepo.findByRegularUserIdAndRecipeId(userId, recipeId);
		if(likedRecipe.isEmpty()) {
			LikedRecipes likesRecipe = new LikedRecipes();
			Recipe rec = recipeRepo.findById(recipeId).get();
			RegularUser user = (RegularUser) userRepository.findById(userId).get();
			likesRecipe.setRegularUser(user);
			likesRecipe.setRecipe(rec);
			likedRecRepo.save(likesRecipe);
			return rec;
		} else {
		
		return null;
		}
	}

	@Override
	public Optional<LikedRecipes> removeRecipe(Long userId, Long recId) {
		Optional<LikedRecipes> likesRecipe = likedRecRepo.findByRegularUserIdAndRecipeId(userId, recId);
		if(likesRecipe.isPresent()) {
			likedRecRepo.deleteById(likesRecipe.get().getId());
			return likesRecipe;
		} else {
		
		return null;
		}
	}

	
	
	
	
}
