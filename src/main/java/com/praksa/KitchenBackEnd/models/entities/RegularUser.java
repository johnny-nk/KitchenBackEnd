package com.praksa.KitchenBackEnd.models.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "regular_users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RegularUser extends User {
	
	@Column(nullable = false)
	@Size(min = 2, max = 30, message = "First name must be between {min} and {max} characters long")
	private String firstName;
	
	@Column(nullable = false)
	@Size(min = 2, max = 30, message = "Last name must be between {min} and {max} characters long")
	private String lastName;
	
	@Column(nullable = false, unique = true)
	@NotNull(message = "Email must be provided.")
	@Email(message = "Email is not valid.")
	private String email;
	
	
	
	
	@JsonManagedReference(value = "user-affectedUser")
	@OneToMany(mappedBy = "regularUser", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<AffectedUsers> limitingFactors = new ArrayList<>();
	
	
	
	@JsonManagedReference(value = "regularUser-likedRecipes")
	@OneToMany(mappedBy = "regularUser", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private Set<LikedRecipes> likedRecipes = new HashSet<>();

	public RegularUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RegularUser(
			@Size(min = 2, max = 30, message = "First name must be between {min} and {max} characters long") String firstName,
			@Size(min = 2, max = 30, message = "Last name must be between {min} and {max} characters long") String lastName,
			@NotNull(message = "Email must be provided.") @Email(message = "Email is not valid.") String email,
			List<AffectedUsers> limitingFactors, Set<LikedRecipes> likedRecipes) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.limitingFactors = limitingFactors;
		this.likedRecipes = likedRecipes;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<AffectedUsers> getLimitingFactor() {
		return limitingFactors;
	}

	public void setLimitingFactor(List<AffectedUsers> limitingFactors) {
		this.limitingFactors = limitingFactors;
	}

	public Set<LikedRecipes> getLikedRecipes() {
		return likedRecipes;
	}

	public void setLikedRecipes(Set<LikedRecipes> likedRecipes) {
		this.likedRecipes = likedRecipes;
	}
	
	
	
	

	


}
