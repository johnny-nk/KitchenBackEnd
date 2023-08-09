package com.praksa.KitchenBackEnd.util.startup;

import java.util.ArrayList;
import java.util.List;

import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;


public class IngredientStartupDTO {

	@CsvBindByName(column = "Name")
	private String name;
	@CsvBindByName(column = "Unit")
	private String unit;
	@CsvBindByName(column = "Calories (kcal)")
	private Integer calories;
	@CsvBindByName(column = "Carbohydrates")
	private Float carbs;
	@CsvBindByName(column = "Sugars")
	private Float sugars;
	@CsvBindByName(column = "Fats")
	private Float fats;
	@CsvBindByName(column = "Saturated Fats")
	private Float saturatedFats;
	@CsvBindByName(column = "Proteins")
	private Float proteins;
	@CsvBindAndSplitByName(column = "Limiting Factors", elementType = String.class, splitOn = ",")
	private List<String> limitingFactors = new ArrayList<>();

//	private List<RecipeIngredient> recipes = new ArrayList<>();

	public IngredientStartupDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IngredientStartupDTO(String name, String unit, Integer calories, Float carbs, Float sugars, Float fats,
			Float saturatedFats, Float proteins, List<String> limitingFactors) {
		super();
		this.name = name;
		this.unit = unit;
		this.calories = calories;
		this.carbs = carbs;
		this.sugars = sugars;
		this.fats = fats;
		this.saturatedFats = saturatedFats;
		this.proteins = proteins;
		this.limitingFactors = limitingFactors;
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

	public List<String> getLimitingFactors() {
		return limitingFactors;
	}

	public void setLimitingFactors(List<String> limitingFactors) {
		this.limitingFactors = limitingFactors;
	}

	@Override
	public String toString() {
		return "IngredientStartupDTO [name=" + name + ", unit=" + unit + ", calories=" + calories + ", carbs=" + carbs
				+ ", sugars=" + sugars + ", fats=" + fats + ", saturatedFats=" + saturatedFats + ", proteins="
				+ proteins + ", limitingFactors=" + limitingFactors + "]";
	}
	
	

}
