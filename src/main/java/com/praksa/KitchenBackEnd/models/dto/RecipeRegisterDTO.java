package com.praksa.KitchenBackEnd.models.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.praksa.KitchenBackEnd.models.entities.ERecipeCategory;
import com.praksa.KitchenBackEnd.models.entities.Ingredient;
import com.praksa.KitchenBackEnd.models.entities.LimitingFactor;
import com.praksa.KitchenBackEnd.models.entities.RecipeIngredient;

public class RecipeRegisterDTO {
	
	private Long id;
	private String title;
	private String description;
	private String steps;
	private String cook;
	private Integer amount;
	private Integer timeToPrepare;
    @JsonFormat(pattern = "dd-mm-yyyy")
    private LocalDateTime createdOn;
    @JsonFormat(pattern = "dd-mm-yyyy")
    private LocalDateTime updatedOn;
	private ERecipeCategory category;
	
	private boolean likedByUser;
	private Map<Long, Integer> ingredientMap = new HashMap<>();
	private Set<String> limitingFactors = new HashSet<>();
	private Map<String, Float> nutrition = new HashMap<>();
	private List<Ingredient> ingredients = new ArrayList<>(); //test
	private Map<String, String> ingredientAmount = new HashMap<>();
	
	
	public RecipeRegisterDTO() {
		super();
		// TODO Auto-generated constructor stub
	}


	public RecipeRegisterDTO(Long id, String title, String description, String steps, String cook, Integer amount,
			Integer timeToPrepare, LocalDateTime createdOn, LocalDateTime updatedOn, ERecipeCategory category,
			boolean likedByUser, Map<Long, Integer> ingredientMap, Set<String> limitingFactors,
			Map<String, Float> nutrition, List<Ingredient> ingredients, Map<String, String> ingredientAmount) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.steps = steps;
		this.cook = cook;
		this.amount = amount;
		this.timeToPrepare = timeToPrepare;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.category = category;
		this.likedByUser = likedByUser;
		this.ingredientMap = ingredientMap;
		this.limitingFactors = limitingFactors;
		this.nutrition = nutrition;
		this.ingredients = ingredients;
		this.ingredientAmount = ingredientAmount;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getSteps() {
		return steps;
	}


	public void setSteps(String steps) {
		this.steps = steps;
	}


	public String getCook() {
		return cook;
	}


	public void setCook(String cook) {
		this.cook = cook;
	}


	public Integer getAmount() {
		return amount;
	}


	public void setAmount(Integer amount) {
		this.amount = amount;
	}


	public Integer getTimeToPrepare() {
		return timeToPrepare;
	}


	public void setTimeToPrepare(Integer timeToPrepare) {
		this.timeToPrepare = timeToPrepare;
	}


	public LocalDateTime getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}


	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}


	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}


	public ERecipeCategory getCategory() {
		return category;
	}


	public void setCategory(ERecipeCategory category) {
		this.category = category;
	}


	public boolean isLikedByUser() {
		return likedByUser;
	}


	public void setLikedByUser(boolean likedByUser) {
		this.likedByUser = likedByUser;
	}


	public Map<Long, Integer> getIngredientMap() {
		return ingredientMap;
	}


	public void setIngredientMap(Map<Long, Integer> ingredientMap) {
		this.ingredientMap = ingredientMap;
	}


	public Set<String> getLimitingFactors() {
		return limitingFactors;
	}


	public void setLimitingFactors(Set<String> limitingFactors) {
		this.limitingFactors = limitingFactors;
	}


	public Map<String, Float> getNutrition() {
		return nutrition;
	}


	public void setNutrition(Map<String, Float> nutrition) {
		this.nutrition = nutrition;
	}


	public List<Ingredient> getIngredients() {
		return ingredients;
	}


	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}


	public Map<String, String> getIngredientAmount() {
		return ingredientAmount;
	}


	public void setIngredientAmount(Map<String, String> ingredientAmount) {
		this.ingredientAmount = ingredientAmount;
	}



	
	
	
	
}
