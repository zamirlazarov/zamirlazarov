package com.couponsystem.couponsystem.beans;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table

public class Income {
	private long id;
	private String name;
	private LocalDateTime date;
	private IncomeType description;
	private double amount;
	
	

	public Income(long id, String name, LocalDateTime date, IncomeType description, double amount) {
		super();
		this.id = id;
		this.name = name;
		this.date = date;
		this.description = description;
		this.amount = amount;
	}
	
	
	

	public Income() {
		super();
	}




	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	@Column
	public String getName() {
		return name;
	}

	@Column
	public LocalDateTime getDate() {
		return date;
	}

	@Enumerated(EnumType.STRING)
	public IncomeType getDescription() {
		return description;
	}

	@Column
	public double getAmount() {
		return amount;
	}




	public void setId(long id) {
		this.id = id;
	}




	public void setName(String name) {
		this.name = name;
	}




	public void setDate(LocalDateTime date) {
		this.date = date;
	}




	public void setDescription(IncomeType description) {
		this.description = description;
	}




	public void setAmount(double amount) {
		this.amount = amount;
	}

	
	
}
