package com.praksa.KitchenBackEnd.models.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "recipes")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Recipe {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(nullable = false)
	@NotBlank(message = "Recipe must have a title")
	@Size(min = 5, max = 100, message = "The title has to be between {min} and {max} characters long.")
	private String title;
	
	@Column(columnDefinition = "TEXT")
	@NotBlank(message = "Recipe has to have a description.")
	private String description;
	
	@Column(columnDefinition = "TEXT")
	@NotBlank(message = "You need to provide steps on how to prepare the meal.")
	private String steps;		
	
	@Column(name = "time_to_prepare")
	private Integer timeToPrepare;
	
	@Column(name = "created")
    @CreationTimestamp
    @JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDateTime createdOn;

    @Column(name = "updated")
    @UpdateTimestamp
    @JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDateTime updatedOn;
	
	private Integer amount;
	
	@Column
	@Enumerated(EnumType.STRING)
	private ERecipeCategory category;
	
	@Version
	@JsonIgnore
	private Integer version;
	
	
	@JsonManagedReference(value = "recipe-recipeIngredients")
	@OneToMany(mappedBy = "recipeId", cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	private List<RecipeIngredient> ingredients = new ArrayList<>();
	
	@JsonBackReference(value = "recipe-cook")
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "cook")
	private Cook cook;
	
	@JsonIgnore
	@JsonManagedReference(value = "likedRecipes-recipes")
	@OneToMany(mappedBy = "recipe", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private Set<LikedRecipes> likedRecipes = new HashSet<>();


	public Recipe() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Recipe(Long id,
			@NotBlank(message = "Recipe must have a title") @Size(min = 5, max = 100, message = "The title has to be between {min} and {max} characters long.") String title,
			@NotBlank(message = "Recipe has to have a description.") String description,
			@NotBlank(message = "You need to provide steps on how to prepare the meal.") String steps,
			Integer timeToPrepare, LocalDateTime createdOn, LocalDateTime updatedOn, Integer amount,
			ERecipeCategory category, Integer version, List<RecipeIngredient> ingredients, Cook cook,
			Set<LikedRecipes> likedRecipes) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.steps = steps;
		this.timeToPrepare = timeToPrepare;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.amount = amount;
		this.category = category;
		this.version = version;
		this.ingredients = ingredients;
		this.cook = cook;
		this.likedRecipes = likedRecipes;
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


	public Integer getAmount() {
		return amount;
	}


	public void setAmount(Integer amount) {
		this.amount = amount;
	}


	public ERecipeCategory getCategory() {
		return category;
	}


	public void setCategory(ERecipeCategory category) {
		this.category = category;
	}


	public Integer getVersion() {
		return version;
	}


	public void setVersion(Integer version) {
		this.version = version;
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


	public Set<LikedRecipes> getLikedRecipes() {
		return likedRecipes;
	}


	public void setLikedRecipes(Set<LikedRecipes> likedRecipes) {
		this.likedRecipes = likedRecipes;
	}




	
	
	
	
	
}
