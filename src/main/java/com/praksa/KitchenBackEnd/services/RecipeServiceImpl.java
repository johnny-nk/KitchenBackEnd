package com.praksa.KitchenBackEnd.services;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.praksa.KitchenBackEnd.models.dto.RecipeDTO;
import com.praksa.KitchenBackEnd.models.dto.RecipeRegisterDTO;
import com.praksa.KitchenBackEnd.models.dto.RegularUserRegisterDTO;
import com.praksa.KitchenBackEnd.models.entities.Cook;
import com.praksa.KitchenBackEnd.models.entities.Ingredient;
import com.praksa.KitchenBackEnd.models.entities.LikedRecipes;
import com.praksa.KitchenBackEnd.models.entities.LimitingFactor;
import com.praksa.KitchenBackEnd.models.entities.LimitingIngredient;
import com.praksa.KitchenBackEnd.models.entities.Recipe;
import com.praksa.KitchenBackEnd.models.entities.RecipeIngredient;
import com.praksa.KitchenBackEnd.models.entities.RegularUser;
import com.praksa.KitchenBackEnd.repositories.CookRepository;
import com.praksa.KitchenBackEnd.repositories.IngredientRepository;
import com.praksa.KitchenBackEnd.repositories.LikedRecipesRepository;
import com.praksa.KitchenBackEnd.repositories.LimitingIngredientRepository;
import com.praksa.KitchenBackEnd.repositories.RecipeIngredientRepository;
import com.praksa.KitchenBackEnd.repositories.RecipeRepository;
import com.praksa.KitchenBackEnd.repositories.UserRepository;

@Service
public class RecipeServiceImpl implements RecipeService {
	
	@Autowired
	private RecipeRepository recipeRepository;
	
	@Autowired
	private IngredientRepository ingredientRepository;
	
	@Autowired
	private LimitingIngredientRepository limIngredientRepo;
	
	@Autowired
	private RecipeIngredientRepository recipeIngreRepo;

	@Autowired 
	private CookService cookService;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CookRepository cookRepository;
	
	@Autowired
	private LikedRecipesRepository likedRecipesRepo;
	
	
	
	
	//=-=-=-=-=-=-=-=-=-=-==--=-=-===-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-///
	
	//IZVLACENJE OMILJENIH RECEPTA
	 private Set<Recipe> getLikedRecipes(RegularUser user) {
		 Set<Recipe> likedRecipes = new HashSet<>();
	 	 likedRecipes = user.getLikedRecipes().stream().map(e -> e.getRecipe()).collect(Collectors.toSet());	 
	 	 return likedRecipes;
}
	//IZVVLACENJE ALERGENE IZ USERA
	private Set<String> userLF(RegularUser user) {
		Set<String> userLF = new HashSet<>();
		userLF = user.getLimitingFactor().stream().map(e -> e.getLimitingFactor().getName()).collect(Collectors.toSet());
		return userLF;
	}
	
	
	//IZLVACENJE ALERGENA IZ RECEPTA
	private Set<String> extractLF(Recipe recipe) {
		
		List<Ingredient> ingredients = recipe.
				getIngredients().stream().map(e -> 
				e.getIngredientId()).toList();
		
		Set<String> limits = new HashSet<>();
		
		for(Ingredient ing : ingredients) { 
			for(LimitingIngredient limIng : ing.getLimitingFactors()) {
				limits.add(limIng.getLimitingFactor().getName());
			}
		}
	
		return limits;
	}
	
	//IZVLACENJE SASTOJAKA 
	private List<Ingredient> extractIng(Recipe recipe) {

		List<Ingredient> ingredients = recipe.
				getIngredients().stream().map(e -> 
				e.getIngredientId()).toList();
		
		return ingredients;
	}
	
	//RACUNANJE HRANLJIVOSTI SASTOJAKA 
	private Map<String, Float> calculateNutrition(Recipe recipe) {
		List<Ingredient> ingredients = new ArrayList<>(extractIng(recipe));
		Integer amount = 0;
		
		Map<String, Float> nutrition = new HashMap<>();
		nutrition.put("proteins", 0.00f);
		nutrition.put("carbs", 0.00f);
		nutrition.put("fats", 0.00f);
		nutrition.put("saturatedFats", 0.00f); 
		nutrition.put("sugars", 0.00f);
		nutrition.put("calories", 0.00f);
		
		for (RecipeIngredient recIng : recipe.getIngredients()) {
			if(ingredients.contains(recIng.getIngredientId())) {
					amount += recIng.getAmount();
				}
			}
		
		for(RecipeIngredient ring : recipeIngreRepo.findAllByRecipeId(recipe)) {
				for (Map.Entry<String, Float> entry : nutrition.entrySet()) {
					if(entry.getKey() == "proteins") {
					entry.setValue(entry.getValue() +  (ring.getIngredientId().getProteins() * (ring.getAmount()/100f)));
					} else if(entry.getKey() == "carbs") {
					entry.setValue(entry.getValue() +  (ring.getIngredientId().getCarbs() * (ring.getAmount()/100f)));
					} else if(entry.getKey() == "fats") {
					entry.setValue(entry.getValue() + (ring.getIngredientId().getFats() * (ring.getAmount()/100f)));
					}else if(entry.getKey() == "saturatedFats") {
					entry.setValue(entry.getValue() + (ring.getIngredientId().getSaturatedFats() * (ring.getAmount()/100f)));
					} else if(entry.getKey() == "sugars") {
					entry.setValue(entry.getValue() + (ring.getIngredientId().getSugars() * (ring.getAmount()/100f)));
					} else if(entry.getKey() == "calories") {
					entry.setValue(entry.getValue() + (ring.getIngredientId().getCalories() * (ring.getAmount()/100f)));
					}
				}
			}
		

		for (Map.Entry<String, Float> entry : nutrition.entrySet()) {
			entry.setValue(entry.getValue()/(amount/100f));
		}

		
		return nutrition;
	}
	
	
	
	//MAPIRANJE IMENA I KOLICINE SASTOJAKA
	private Map<String, Integer> ingredientNamedMap(Recipe recipe) {
		List<RecipeIngredient> recIng = recipe.getIngredients();
		Map<String, Integer> namedIng = new HashMap<>();
		for (RecipeIngredient ri : recIng) {
			namedIng.put(ingredientRepository.findById(ri.getIngredientId().getId()).get().getName(),
					ri.getAmount());
		}
		return namedIng;
	}
	
	//MAPIRANJE IMENA I KOLICINE SASTOJAKA SA DODATOM JEDINICOM MERE
	private Map<String, String> ingredientNamedMapString(Recipe recipe) {
		List<RecipeIngredient> recIng = recipe.getIngredients();
		Map<String, String> namedIng = new HashMap<>();
	
		for (RecipeIngredient ri : recIng) {
			namedIng.put(ingredientRepository.findById(ri.getIngredientId().getId()).get().getName(), //<-key
					ri.getAmount().toString() + ingredientRepository.findById(ri.getIngredientId().getId()).get().getUnit()); //<-value
		}
		return namedIng;
	}
	
	
	
	
	private List<RecipeRegisterDTO> recipeFormater(Iterable<Recipe> recipes) {
		
		List<RecipeRegisterDTO> formatedRecipes = new ArrayList<>();
		 
		for(Recipe recipe : recipes) {
		RecipeRegisterDTO dto = new RecipeRegisterDTO();
		dto.setId(recipe.getId());
		dto.setCategory(recipe.getCategory());
		dto.setDescription(recipe.getDescription());
		dto.setSteps(recipe.getSteps());
		dto.setTitle(recipe.getTitle());
		dto.setTimeToPrepare(recipe.getTimeToPrepare());
		dto.setAmount(recipe.getAmount());
		dto.setCook(recipe.getCook().getFirstName() + " " + recipe.getCook().getLastName());
		dto.setCreatedOn(recipe.getCreatedOn());
		dto.setUpdatedOn(recipe.getUpdatedOn());
													
		dto.setNutrition(calculateNutrition(recipe));  //-> dinamicno racunanje nutritivne vrednosti
		dto.setIngredients(extractIng(recipe));		   //-> prikaz svih satojaka i njihovih nutrutivnih vrednost	
		dto.setIngredientAmount(ingredientNamedMapString(recipe)); //-> samo imena sastojaka i njihove kolicine
		dto.setLimitingFactors(extractLF(recipe)); 	  
		formatedRecipes.add(dto);
		}
		
		return formatedRecipes;
	}
	
	
	//=-=-==-=-==-=-=-=-==-=-==SERVICES-=-=-==-=-==-=-=-=-==-=-===-=-=-=-==-=-==-=-=//
	
	
	//povlaci sve recepte sa izracunatom vrednoscu nutricienata, proverava LF i pravi prikaz da li user voli recept ili ne
	@Override
	public Iterable<RecipeRegisterDTO> getFormatedRecipes(String username) {
		RegularUser user = (RegularUser) userRepo.findByUsername(username);
		Set<String> usersLF = userLF(user);
		Iterable<Recipe> unformatedRecipes = recipeRepository.findAll();
		List<RecipeRegisterDTO> formatedRecipes = recipeFormater(unformatedRecipes);
		formatedRecipes.stream().map(e -> e.getLimitingFactors().retainAll(usersLF)).collect(Collectors.toSet());
		for (RecipeRegisterDTO frRec : formatedRecipes) {
			if(user.getLikedRecipes().stream().anyMatch(e -> e.getRecipe().getId().equals(frRec.getId()))) {
				frRec.setLikedByUser(true);
			} else {
				frRec.setLikedByUser(false);
			}
		}
		
		return formatedRecipes;
	} 
	
	
	@Override
	public Iterable<RecipeRegisterDTO> getAllRecipes() {
		Iterable<Recipe> unformatedRecipes = recipeRepository.findAll();
		List<RecipeRegisterDTO> formatedRecipes = recipeFormater(unformatedRecipes);
		return formatedRecipes;
	}
	
	
	@Override
	public Iterable<Recipe> getRecipes() {
		return recipeRepository.findAll();
	}
	
	
	
	
	@Override
	public RecipeRegisterDTO getRecipe(Long id) {
		Recipe recipe = recipeRepository.findById(id).get();
		List<Recipe> unformatedRecipe = new ArrayList<>();
		unformatedRecipe.add(recipe);
		List<RecipeRegisterDTO> dto = recipeFormater(unformatedRecipe); 
		return dto.get(0);
	}

	
	
	@Override
	public Iterable<RecipeRegisterDTO> searchByRecipeName(String title) {
		Iterable<Recipe> recipes = recipeRepository.findByTitleContainingIgnoreCase(title);
		List<RecipeRegisterDTO> dto = recipeFormater(recipes);
		return dto;
	}
	
	
	
	//moglo je elegantnije ali za sada copy/paste
	@Override
	public List<RecipeRegisterDTO> myCookbook(String username) {
		RegularUser user = (RegularUser) userRepo.findByUsername(username);
		Set<LikedRecipes> likedRecipes = likedRecipesRepo.findByRegularUserId(user.getId());
		Iterable<Recipe> recipes = likedRecipes.stream().map(e -> e.getRecipe()).toList();
		Set<String> usersLF = userLF(user);
		List<RecipeRegisterDTO> dto = recipeFormater(recipes);
		dto.stream().map(e -> e.getLimitingFactors().retainAll(usersLF)).collect(Collectors.toSet());
		for (RecipeRegisterDTO frRec : dto) {
			if(user.getLikedRecipes().stream().anyMatch(e -> e.getRecipe().getId().equals(frRec.getId()))) {
				frRec.setLikedByUser(true);
			} else {
				frRec.setLikedByUser(false);
			}
		}
		return dto;
	}
	
	
	
	
	@Override
	public Recipe deleteRecipe(Long id) {
		Recipe recipe = recipeRepository.findById(id).get();
		List<RecipeIngredient> rings = recipeIngreRepo.findAllByRecipeId(recipe);
		recipeIngreRepo.deleteAll(rings);
		recipeRepository.deleteById(id);
		return recipe;
	}
	


	@Override
	@Transactional
	public RecipeRegisterDTO updateRecipe(RecipeRegisterDTO updatedRecipe, Long id) {
		
		Recipe recipe = recipeRepository.findById(id).get();
		
		List<RecipeIngredient> updateRing = new ArrayList<>();
//		List<RecipeIngredient> testRing = recipeIngreRepo.findAllByRecipeId(recipe);
		List<RecipeIngredient> deleteRecing = new ArrayList<>();
		if(updatedRecipe.getAmount() != null && !updatedRecipe.getAmount().equals(recipe.getAmount())) {
			recipe.setAmount(updatedRecipe.getAmount());
		}
		if(updatedRecipe.getSteps() != null && !updatedRecipe.getSteps().equals(recipe.getSteps())) {
			recipe.setSteps(updatedRecipe.getSteps());			
		}
		if(updatedRecipe.getTimeToPrepare() != null && updatedRecipe.getTimeToPrepare() != recipe.getTimeToPrepare()) {
			recipe.setTimeToPrepare(updatedRecipe.getTimeToPrepare());			
		}
		if(updatedRecipe.getTitle() != null && !updatedRecipe.getTitle().equals(recipe.getTitle())) {
			recipe.setTitle(updatedRecipe.getTitle());			
		}
		if(updatedRecipe.getDescription() != null && !updatedRecipe.getDescription().equals(recipe.getDescription())) {
			recipe.setDescription(updatedRecipe.getDescription());			
		}
		if(updatedRecipe.getCategory() != null && !updatedRecipe.getCategory().equals(recipe.getCategory())) {
			recipe.setCategory(updatedRecipe.getCategory());
		}
		
	
		
		
		//Radi dodavanje sastojaka, izmenu kolicine sastojaka i brisanje sastojaka ako im prosledis kolicinu da je 0
		//kada skontam kako da implementiram da brise preko id-a neka ovako ostane; imamo dosta posla
		for (Map.Entry<Long, Integer> entry : updatedRecipe.getIngredientMap().entrySet()) {
			RecipeIngredient ring = recipeIngreRepo.findByIngredientIdIdAndRecipeId(entry.getKey(), recipe);
			try {
				if(ring != null) {
				ring.setAmount(entry.getValue());
				updateRing.add(ring);
				}

		} catch (NullPointerException npe) {
			System.out.println(npe);
		} finally {
			if(ring == null) {
			RecipeIngredient newRing = new RecipeIngredient();
			newRing.setIngredientId(ingredientRepository.findById(entry.getKey()).get());
			newRing.setAmount(entry.getValue());
			newRing.setRecipeId(recipe);
			recipeIngreRepo.save(newRing);
			} 
			if(entry.getValue().equals(0)) {
				 deleteRecing.add(recipeIngreRepo.findByIngredientIdIdAndRecipeId(entry.getKey(), recipe));
				
				}
				
			}
		}	
		
		recipe.setIngredients(updateRing);
		recipeRepository.save(recipe);
		recipeIngreRepo.deleteAll(deleteRecing); 
		recipeIngreRepo.saveAll(updateRing);
		return updatedRecipe;
	}
	
	@Override
	public RecipeRegisterDTO createRecipe(RecipeRegisterDTO dto, String username) {
		Recipe recipe = new Recipe();
		Cook cook = (Cook) userRepo.findByUsername(username);
		List<RecipeIngredient> recIng = new ArrayList<>();
		
		
		recipe.setDescription(dto.getDescription());
		recipe.setSteps(dto.getSteps());
		recipe.setTitle(dto.getTitle());
		recipe.setCategory(dto.getCategory());
		recipe.setTimeToPrepare(dto.getTimeToPrepare());
		recipe.setAmount(dto.getAmount());
		recipe.setCook(cook);
		
		
		for (Map.Entry<Long, Integer> entry : dto.getIngredientMap().entrySet()) {
			RecipeIngredient ring = new RecipeIngredient();
			ring.setIngredientId(ingredientRepository.findById(entry.getKey()).get());
			ring.setRecipeId(recipe);
			ring.setAmount(entry.getValue());
			recIng.add(ring);
		}
		recipe.setIngredients(recIng);
		
		
		recipeRepository.save(recipe);
		recipeIngreRepo.saveAll(recIng);
		return dto;
	}
	
	
	
	//mozda zatreba
	@Override
	public Set<LimitingFactor> getLFfromRecipe(Long id) {
		Recipe recipe = recipeRepository.findById(id).get();
		List<Ingredient> ingredients = recipe.
				getIngredients().stream().map(e -> 
				e.getIngredientId()).toList();
		
		Set<LimitingFactor> limits = new HashSet<>();
		for(Ingredient ing : ingredients) { 
			for(LimitingIngredient limIng : ing.getLimitingFactors()) {
				limits.add(limIng.getLimitingFactor());
			}
		}
	
		return limits;
	}
	
	
	

	

	
	
	
	
	
	

}
