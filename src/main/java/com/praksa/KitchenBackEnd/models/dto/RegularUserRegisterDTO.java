package com.praksa.KitchenBackEnd.models.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.praksa.KitchenBackEnd.models.entities.EUserRole;

public class RegularUserRegisterDTO extends UserRegisterDTO {
	
	private Long id;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private List<RecipeRegisterDTO> myCookbook = new ArrayList<>();
	private Set<String> myLimitigFactors = new HashSet<>();
	
	private Set<Long> favRecipesId = new HashSet<>();
	private Set<Long> myLimFactorsId = new HashSet<>();

	public RegularUserRegisterDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public RegularUserRegisterDTO(String username, String password, EUserRole role) {
		super(username, password, role);
		// TODO Auto-generated constructor stub
	}



	public RegularUserRegisterDTO(Long id, String firstName, String lastName, String email,
			List<RecipeRegisterDTO> myCookbook, Set<String> myLimitigFactors, Set<Long> favRecipesId,
			Set<Long> myLimFactorsId) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.myCookbook = myCookbook;
		this.myLimitigFactors = myLimitigFactors;
		this.favRecipesId = favRecipesId;
		this.myLimFactorsId = myLimFactorsId;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
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



	public List<RecipeRegisterDTO> getMyCookbook() {
		return myCookbook;
	}



	public void setMyCookbook(List<RecipeRegisterDTO> myCookbook) {
		this.myCookbook = myCookbook;
	}



	public Set<String> getMyLimitigFactors() {
		return myLimitigFactors;
	}



	public void setMyLimitigFactors(Set<String> myLimitigFactors) {
		this.myLimitigFactors = myLimitigFactors;
	}



	public Set<Long> getFavRecipesId() {
		return favRecipesId;
	}



	public void setFavRecipesId(Set<Long> favRecipesId) {
		this.favRecipesId = favRecipesId;
	}



	public Set<Long> getMyLimFactorsId() {
		return myLimFactorsId;
	}



	public void setMyLimFactorsId(Set<Long> myLimFactorsId) {
		this.myLimFactorsId = myLimFactorsId;
	}



	
}
