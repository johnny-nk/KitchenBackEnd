package com.praksa.KitchenBackEnd.models.dto;

import com.praksa.KitchenBackEnd.models.entities.Cook;

public class RecipeDTO {
	
	private String title;
	private String description;
	private Integer amount;
	private String steps;
	private Integer timeToPrepare;
	private Cook cook;
	
	public RecipeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RecipeDTO(String title, String description, Integer amount, String steps, Integer timeToPrepare, Cook cook) {
		super();
		this.title = title;
		this.description = description;
		this.amount = amount;
		this.steps = steps;
		this.timeToPrepare = timeToPrepare;
		this.cook = cook;
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
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getSteps() {
		return steps;
	}
	public void setSteps(String steps) {
		this.steps = steps;
	}
	public Integer getTimeToPrepare() {
		return timeToPrepare;
	}
	public void setTimeToPrepare(Integer timeToPrepare) {
		this.timeToPrepare = timeToPrepare;
	}
	public Cook getCook() {
		return cook;
	}
	public void setCook(Cook cook) {
		this.cook = cook;
	}
	
	
}