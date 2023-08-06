package com.praksa.KitchenBackEnd.models.dto;

import java.util.HashSet;
import java.util.Set;

import com.praksa.KitchenBackEnd.models.entities.EUserRole;

public class RegularUserRegisterDTO extends UserRegisterDTO {
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private Set<Long> myCookbook = new HashSet<>();
	
	private Set<String> myLimitigFactors = new HashSet<>();

	public RegularUserRegisterDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RegularUserRegisterDTO(String username, String password, EUserRole role) {
		super(username, password, role);
		// TODO Auto-generated constructor stub
	}

	public RegularUserRegisterDTO(String firstName, String lastName, String email, Set<Long> myCookbook,
			Set<String> myLimitigFactors) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.myCookbook = myCookbook;
		this.myLimitigFactors = myLimitigFactors;
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

	public Set<Long> getMyCookbook() {
		return myCookbook;
	}

	public void setMyCookbook(Set<Long> myCookbook) {
		this.myCookbook = myCookbook;
	}

	public Set<String> getMyLimitigFactors() {
		return myLimitigFactors;
	}

	public void setMyLimitigFactors(Set<String> myLimitigFactors) {
		this.myLimitigFactors = myLimitigFactors;
	}

	
	
	
}
