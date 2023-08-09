package com.praksa.KitchenBackEnd.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.opencsv.bean.CsvBindByName;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, unique = true)
	@NotBlank(message = "Username must be provided")
	@Size(min = 5, max = 20, message = "Username must be between {min} and {max} characters long.")
	@CsvBindByName(column = "Username")
	private String username;
	
	@JsonIgnore
	@NotBlank(message = "Password must be provided.")
	@Size(min = 4, max = 100, message = "Password must be between {min} and {max} characters long.")
	@Column(nullable = false, length = 100)
	@CsvBindByName(column = "Password")
	private String password;
	
	
	
	@Column
	@Enumerated(EnumType.STRING)
	@CsvBindByName(column = "Role")
	private EUserRole role;

	@Version
	private Integer version;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(Long id, String username, String password, EUserRole role, Integer version) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
		this.version = version;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public EUserRole getRole() {
		return role;
	}

	public void setRole(EUserRole role) {
		this.role = role;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
	
	
	
}
