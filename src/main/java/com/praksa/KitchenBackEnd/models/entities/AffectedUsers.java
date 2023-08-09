package com.praksa.KitchenBackEnd.models.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class AffectedUsers {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@JsonBackReference(value = "user-affectedUser")
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "regular_user_id")
	private RegularUser regularUser;
	
	@JsonBackReference(value = "affectedUser-limitingFactor")
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "limiting_factor_id")
	private LimitingFactor limitingFactor;

	public AffectedUsers() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AffectedUsers(Long id, RegularUser regularUser, LimitingFactor limitingFactor) {
		super();
		this.id = id;
		this.regularUser = regularUser;
		this.limitingFactor = limitingFactor;
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

	public LimitingFactor getLimitingFactor() {
		return limitingFactor;
	}

	public void setLimitingFactor(LimitingFactor limitingFactor) {
		this.limitingFactor = limitingFactor;
	}
	
	
	
	
	
}
