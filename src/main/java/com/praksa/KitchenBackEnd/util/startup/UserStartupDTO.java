package com.praksa.KitchenBackEnd.util.startup;

import com.opencsv.bean.CsvBindByName;

public class UserStartupDTO {

	@CsvBindByName(column = "First Name")
	private String firstName;
	@CsvBindByName(column = "Last Name")
	private String lastName;
	@CsvBindByName(column = "Username")
	private String username;
	@CsvBindByName(column = "Email")
	private String email;
	@CsvBindByName(column = "Password")
	private String password;
	@CsvBindByName(column = "Role")
	private String role;
	@CsvBindByName(column = "About Me")
	private String aboutMe;
	
	public UserStartupDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserStartupDTO(String firstName, String lastName, String username, String email, String password,
			String role, String aboutMe) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	@Override
	public String toString() {
		return "UserStartupDTO [firstName=" + firstName + ", lastName=" + lastName + ", username=" + username
				+ ", email=" + email + ", password=" + password + ", role=" + role + ", aboutMe=" + aboutMe + "]";
	}
	
	
}
