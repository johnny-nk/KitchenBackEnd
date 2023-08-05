package com.praksa.KitchenBackEnd.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	
	//=========================FOR LIMITING FACTORS=======================//
	
	@Override
	public Set<LimitingFactor> getLimitingFactors(Long userId) {
		logger.info("GetLimitingFactors method invoked - for user with id = " + userId + ".");
		RegularUser user = (RegularUser) userRepository.findById(userId).get();
		Set<LimitingFactor> lf = new HashSet<>();
		for(AffectedUsers af : affectedUsersRepo.findAll()) {
			if(user.getId().equals(af.getRegularUser().getId())) {
				lf.add(af.getLimitingFactor());
			}
		}
		logger.info("Finished getting limiting factor for user with  id = " + userId + " .");
		return lf;
	}

	@Override
	public AffectedUsers addLimitingFactor(Long userId, Long lfId) {
		logger.info("AddLimitingFactor method invoked - limiting factor with id = " + lfId + ", for user with id = " + userId + ".");
		Optional<AffectedUsers> af = affectedUsersRepo.findByRegularUserIdAndLimitingFactorId(userId, lfId);
		
		if (af.isEmpty()) {
			RegularUser user = (RegularUser) userRepository.findById(userId).get();
			LimitingFactor lf = limFactorRepo.findById(lfId).get();
			AffectedUsers aff = new AffectedUsers();
			aff.setLimitingFactor(lf);
			aff.setRegularUser(user);
			affectedUsersRepo.save(aff);
			logger.info("Finished adding limiting factor with id = " + lfId + " for user with id = " + userId + ".");
			return aff;
		} else {
			logger.error("An error occured while adding limiting factor with id = " + lfId + " for user with id = " + userId + ".");
			return null;
		}
	}
	
	
	@Override
	public Optional<AffectedUsers> removeLimitingFactor(Long userId, Long lfId) {
		logger.info("RemoveLimitingFactor method invoked - limiting factor with id = " + lfId + ", for user with id = " + userId + ".");
		Optional<AffectedUsers> af = affectedUsersRepo.findByRegularUserIdAndLimitingFactorId(userId, lfId);
		if (af.isPresent()) {
			affectedUsersRepo.delete(af.get());
			logger.info("Finished removing limiting factor with id = " + lfId + " for user with id = " + userId + ".");
			return af;
		} else {
			logger.error("An error occured while removing limiting factor with id = " + lfId + " for user with id = " + userId + ".");
			return null;
		}
	}
	
	
	//=========================FOR RECIPES=========================//
	
	@Override
	public Set<Recipe> getUserRecipes(Long userId) {
		logger.info("GetUserRecipes method invoked - recipes for user with id = " + userId + ".");
		Set<LikedRecipes> likedRecipes = likedRecRepo.findByRegularUserId(userId);
		Set<Recipe> recipes = likedRecipes.stream().map(e -> e.getRecipe()).collect((Collectors.toSet()));
		
		logger.info("Finished getting recipes for user with id = " + userId + ".");
		return recipes;
	}

	
	
	@Override
	public Recipe addRecipeToUser(Long userId, Long recipeId) {
		logger.info("AddRecipeToUser method invoked - recipe with id = " + recipeId + ", for user with id = " + userId 	+ ".");
		Optional<LikedRecipes> likedRecipe = likedRecRepo.findByRegularUserIdAndRecipeId(userId, recipeId);
		if (likedRecipe.isEmpty()) {
			LikedRecipes likesRecipe = new LikedRecipes();
			Recipe rec = recipeRepo.findById(recipeId).get();
			RegularUser user = (RegularUser) userRepository.findById(userId).get();
			likesRecipe.setRegularUser(user);
			likesRecipe.setRecipe(rec);
			likedRecRepo.save(likesRecipe);
			logger.info("Finished adding recipe with id = " + recipeId + ", for user with id = " + userId + ".");
			return rec;
		} else {
			logger.info("An error occured while adding recipe with id = " + recipeId + ", for user with id = " + userId + ".");
			return null;
		}
	}

	@Override
	public Optional<LikedRecipes> removeRecipe(Long userId, Long recId) {
		logger.info("RemoveRecipe method invoked - recipe with id = " + recId + ", for user with id = " + userId + ".");
		Optional<LikedRecipes> likesRecipe = likedRecRepo.findByRegularUserIdAndRecipeId(userId, recId);
		if (likesRecipe.isPresent()) {
			likedRecRepo.deleteById(likesRecipe.get().getId());
			logger.info("Finished removing recipe with id = " + recId + ", for user with id = " + userId + ".");
			return likesRecipe;
		} else {
			logger.info("An error occured while removing recipe with id = " + recId + ", for user with id = " + userId + ".");
			return null;
		}
	}

	
	
	
	
}
