package com.praksa.KitchenBackEnd.models.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Ingredient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Version
	private Integer version;
	private String name;
	private String unit;
	private Integer calories;
	private Integer carbs;
	private Integer sugars;
	private Integer fats;
	@Column(name = "saturated_fats")
	private Integer saturatedFats;
	private Integer proteins;
	
	@Column(name = "limiting_factors")
	@OneToMany(mappedBy = "ingredient", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonBackReference
	private List<LimitingFactor> limitingFactors;
	
	@OneToMany(mappedBy = "ingredientId", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonBackReference
	private List<RecipeIngredient> recipes;

	public Ingredient() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Ingredient(Long id, Integer version, String name, String unit, Integer calories, Integer carbs,
			Integer sugars, Integer fats, Integer saturatedFats, Integer proteins, List<LimitingFactor> limitingFactors,
			List<RecipeIngredient> recipes) {
		super();
		this.id = id;
		this.version = version;
		this.name = name;
		this.unit = unit;
		this.calories = calories;
		this.carbs = carbs;
		this.sugars = sugars;
		this.fats = fats;
		this.saturatedFats = saturatedFats;
		this.proteins = proteins;
		this.limitingFactors = limitingFactors;
		this.recipes = recipes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getCalories() {
		return calories;
	}

	public void setCalories(Integer calories) {
		this.calories = calories;
	}

	public Integer getCarbs() {
		return carbs;
	}

	public void setCarbs(Integer carbs) {
		this.carbs = carbs;
	}

	public Integer getSugars() {
		return sugars;
	}

	public void setSugars(Integer sugars) {
		this.sugars = sugars;
	}

	public Integer getFats() {
		return fats;
	}

	public void setFats(Integer fats) {
		this.fats = fats;
	}

	public Integer getSaturatedFats() {
		return saturatedFats;
	}

	public void setSaturatedFats(Integer saturatedFats) {
		this.saturatedFats = saturatedFats;
	}

	public Integer getProteins() {
		return proteins;
	}

	public void setProteins(Integer proteins) {
		this.proteins = proteins;
	}

	public List<LimitingFactor> getLimitingFactors() {
		return limitingFactors;
	}

	public void setLimitingFactors(List<LimitingFactor> limitingFactors) {
		this.limitingFactors = limitingFactors;
	}

	public List<RecipeIngredient> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<RecipeIngredient> recipes) {
		this.recipes = recipes;
	}
	
	

}