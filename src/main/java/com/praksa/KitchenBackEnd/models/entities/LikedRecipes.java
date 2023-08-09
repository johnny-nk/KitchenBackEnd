package com.praksa.KitchenBackEnd.models.entities;



import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LikedRecipes {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Long id;
	
	
	
	@JsonBackReference(value = "regularUser-likedRecipes")
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	public RegularUser regularUser;
	
	
	@JsonBackReference(value = "likedRecipes-recipes")
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	public Recipe recipe;


	public LikedRecipes() {
		super();
		// TODO Auto-generated constructor stub
	}


	public LikedRecipes(Long id, RegularUser regularUser, Recipe recipe) {
		super();
		this.id = id;
		this.regularUser = regularUser;
		this.recipe = recipe;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public RegularUser getRegularUser() {
		return regularUser;
	}


	public void setRegularUser(RegularUser regularUser) {
		this.regularUser = regularUser;
	}


	public Recipe getRecipe() {
		return recipe;
	}


	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}




	
	
	
}
