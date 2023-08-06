package com.praksa.KitchenBackEnd.models.dto;

import com.praksa.KitchenBackEnd.models.entities.EUserRole;

public class CookRegisterDTO extends UserRegisterDTO {

	private String firstName;

	private String lastName;

	private String email;

	private String aboutMe;

	public CookRegisterDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CookRegisterDTO(String username, String password, EUserRole role) {
		super(username, password, role);
		// TODO Auto-generated constructor stub
	}

	public CookRegisterDTO(String firstName, String lastName, String email, String aboutMe) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.aboutMe = aboutMe;
	}

	public CookRegisterDTO(String username, String password, EUserRole role, String firstName, String lastName,
			String email, String aboutMe) {
		super(username, password, role);
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.aboutMe = aboutMe;
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

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

}
