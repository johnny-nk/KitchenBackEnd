package com.praksa.KitchenBackEnd.models.dto;

import java.util.HashMap;
import java.util.Map;


public class IngredientDTO {
	
	private Long id;
	private String name;
	private String unit;
	private Integer calories;
	private Float carbs;
	private Float sugars;
	private Float fats;
	private Float saturatedFats;
	private Float proteins;

	private Map<Long, String> contains = new HashMap<>();
	
	public IngredientDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IngredientDTO(Long id,String name, String unit, Integer calories, Float carbs, Float sugars, Float fats,
			Float saturatedFats, Float proteins, Map<Long, String> contains) {
		super();
		this.id = id;
		this.name = name;
		this.unit = unit;
		this.calories = calories;
		this.carbs = carbs;
		this.sugars = sugars;
		this.fats = fats;
		this.saturatedFats = saturatedFats;
		this.proteins = proteins;
		this.contains = contains;
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

	public Float getCarbs() {
		return carbs;
	}

	public void setCarbs(Float carbs) {
		this.carbs = carbs;
	}

	public Float getSugars() {
		return sugars;
	}

	public void setSugars(Float sugars) {
		this.sugars = sugars;
	}

	public Float getFats() {
		return fats;
	}

	public void setFats(Float fats) {
		this.fats = fats;
	}

	public Float getSaturatedFats() {
		return saturatedFats;
	}

	public void setSaturatedFats(Float saturatedFats) {
		this.saturatedFats = saturatedFats;
	}

	public Float getProteins() {
		return proteins;
	}

	public void setProteins(Float proteins) {
		this.proteins = proteins;
	}

	public Map<Long, String> getContains() {
		return contains;
	}

	public void setContains(Map<Long, String> contains) {
		this.contains = contains;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	

}
