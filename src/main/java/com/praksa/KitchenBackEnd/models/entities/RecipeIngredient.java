package com.praksa.KitchenBackEnd.models.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RecipeIngredient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Integer amount;

	@JsonBackReference(value = "recipe-recipeIngredients")
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "recipeId")
	private Recipe recipeId;
	
	@JsonBackReference(value = "recipeIngredient-ingredient")
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "ingredientId")
	private Ingredient ingredientId;
	

	public RecipeIngredient() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RecipeIngredient(Long id, Recipe recipeId, Ingredient ingredientId, Integer amount) {
		super();
		this.id = id;
		this.recipeId = recipeId;
		this.ingredientId = ingredientId;
		this.amount = amount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Recipe getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(Recipe recipeId) {
		this.recipeId = recipeId;
	}

	public Ingredient getIngredientId() {
		return ingredientId;
	}

	public void setIngredientId(Ingredient ingredientId) {
		this.ingredientId = ingredientId;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	
	
	
	
	
	
	
}
