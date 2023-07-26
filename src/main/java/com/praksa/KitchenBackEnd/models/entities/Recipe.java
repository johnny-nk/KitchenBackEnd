package com.praksa.KitchenBackEnd.models.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "recipes")
public class Recipe {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	private String title;
	private String decription;   
	private String steps;		
	@Column(name = "time_to_prepare")
	private Integer timeToPrepare;
	private Integer amount;
	
	@OneToMany(mappedBy = "recipeId", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonBackReference
	private List<RecipeIngredient> ingredients;
	
	@JsonManagedReference
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "cook")
	private Cook cook;
	
	@ManyToMany(mappedBy = "likedRecipes")
	private Set<RegularUser> recipes = new HashSet<>();
	
	@Version
	private Integer version;

	public Recipe() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Recipe(Long id, String title, String decription, String steps, Integer timeToPrepare, Integer amount,
			List<RecipeIngredient> ingredients, Cook cook, Set<RegularUser> recipes, Integer version) {
		super();
		this.id = id;
		this.title = title;
		this.decription = decription;
		this.steps = steps;
		this.timeToPrepare = timeToPrepare;
		this.amount = amount;
		this.ingredients = ingredients;
		this.cook = cook;
		this.recipes = recipes;
		this.version = version;
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

	public String getDecription() {
		return decription;
	}

	public void setDecription(String decription) {
		this.decription = decription;
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

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public List<RecipeIngredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<RecipeIngredient> ingredients) {
		this.ingredients = ingredients;
	}

	public Cook getCook() {
		return cook;
	}

	public void setCook(Cook cook) {
		this.cook = cook;
	}

	public Set<RegularUser> getRecipes() {
		return recipes;
	}

	public void setRecipes(Set<RegularUser> recipes) {
		this.recipes = recipes;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	
	
}