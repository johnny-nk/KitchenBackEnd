package com.praksa.KitchenBackEnd.models.dto;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.praksa.KitchenBackEnd.models.entities.ERecipeCategory;
import com.praksa.KitchenBackEnd.models.entities.LimitingFactor;
import com.praksa.KitchenBackEnd.models.entities.RecipeIngredient;


public class RecipeDTO {
	
	
	private Long id;
	private String title;
	private String description;
	private String steps;
	private String cook;
	private Integer amount;
	private Integer timeToPrepare;
	private ERecipeCategory category;
	private Set<LimitingFactor> limitingFactors;
	private List<RecipeIngredient> ingredients;
	private Map<String, Float> nutrition;
	
	public RecipeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getCook() {
		return cook;
	}


	public void setCook(String cook) {
		this.cook = cook;
	}


	


	public RecipeDTO(Long id, String title, String description, String steps, String cook, Integer amount,
			Integer timeToPrepare, ERecipeCategory category, Set<LimitingFactor> limitingFactors,
			List<RecipeIngredient> ingredients, Map<String, Float> nutrition) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.steps = steps;
		this.cook = cook;
		this.amount = amount;
		this.timeToPrepare = timeToPrepare;
		this.category = category;
		this.limitingFactors = limitingFactors;
		this.ingredients = ingredients;
		this.nutrition = nutrition;
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

	public ERecipeCategory getCategory() {
		return category;
	}

	public void setCategory(ERecipeCategory category) {
		this.category = category;
	}

	public Set<LimitingFactor> getLimitingFactors() {
		return limitingFactors;
	}

	public void setLimitingFactors(Set<LimitingFactor> limitingFactors) {
		this.limitingFactors = limitingFactors;
	}

	public List<RecipeIngredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<RecipeIngredient> ingredients) {
		this.ingredients = ingredients;
	}

	public Map<String, Float> getNutrition() {
		return nutrition;
	}

	public void setNutrition(Map<String, Float> nutrition) {
		this.nutrition = nutrition;
	}

	

	

	
	
}
