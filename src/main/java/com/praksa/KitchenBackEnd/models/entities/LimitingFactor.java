package com.praksa.KitchenBackEnd.models.entities;

import java.util.ArrayList;
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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.opencsv.bean.CsvBindByName;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LimitingFactor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Version
	@JsonIgnore
	private Integer version;
	@Column(nullable = false, unique = true)
	@NotBlank(message = "Limiting/prohibiting factors in ingredients must have a name.")
	@CsvBindByName(column = "Limiting Factors")
	private String name;

	
	@JsonManagedReference(value = "limitingIngredient-limitingFactor")
	@OneToMany(mappedBy = "limitingFactor", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<LimitingIngredient> ingredients = new ArrayList<>(); 
	
	@JsonIgnore
	@JsonManagedReference(value = "affectedUser-limitingFactor")
	@OneToMany(mappedBy = "limitingFactor", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<AffectedUsers> limitingFactors = new ArrayList<>();

	public LimitingFactor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LimitingFactor(Long id, Integer version,
			@NotBlank(message = "Limiting/prohibiting factor in ingredients must have a name.") String name,
			List<LimitingIngredient> ingredients, List<AffectedUsers> limitingFactors) {
		super();
		this.id = id;
		this.version = version;
		this.name = name;
		this.ingredients = ingredients;
		this.limitingFactors = limitingFactors;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<LimitingIngredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<LimitingIngredient> ingredients) {
		this.ingredients = ingredients;
	}

	public List<AffectedUsers> getLimitingFactors() {
		return limitingFactors;
	}

	public void setLimitingFactors(List<AffectedUsers> limitingFactors) {
		this.limitingFactors = limitingFactors;
	}






	
	
	
	
}
