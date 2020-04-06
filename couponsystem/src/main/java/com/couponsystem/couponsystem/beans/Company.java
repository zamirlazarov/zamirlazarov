package com.couponsystem.couponsystem.beans;

import java.util.Hashtable;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "company")
public class Company {

	private long id;

	private String CompanyName;

	private String Password;

	private String Email;

	private Map<Long, Coupon> couponsCollection = new Hashtable<>();
	
	private Map<Long, Income> incomeCollection=new Hashtable<>();

	public Company(Long id, String companyName, String password, String email) {
		super();
		setId(id);
		CompanyName = companyName;
		Password = password;
		Email = email;
	}

	public Company() {

	}



	public Company(long id, String companyName, String password, String email, Map<Long, Coupon> couponsCollection,
			Map<Long, Income> incomeCollection) {
		super();
		this.id = id;
		CompanyName = companyName;
		Password = password;
		Email = email;
		this.couponsCollection = couponsCollection;
		this.incomeCollection = incomeCollection;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column
	public String getCompanyName() {
		return CompanyName;
	}

	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}

	@Column
	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	@Column
	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", CompanyName=" + CompanyName + ", Password=" + Password + ", Email=" + Email
				+ "]";
	}

	public Map<Long, Coupon> getCouponsCollection() {
		return couponsCollection;
	}

	public void setCouponsCollection(Map<Long, Coupon> couponsCollection) {
		this.couponsCollection = couponsCollection;
	}
	@ManyToMany
	public Map<Long, Income> getIncomeCollection() {
		return incomeCollection;
	}
	@ManyToMany
	public void setIncomeCollection(Map<Long, Income> incomeCollection) {
		this.incomeCollection = incomeCollection;
	}



}
