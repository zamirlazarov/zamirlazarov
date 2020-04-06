package com.couponsystem.couponsystem.beans;

import java.util.Hashtable;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "custmer")
public class Customer {

	private long id;
	private String CustomerName;
	private String Password;

	private Map<Long, Coupon> couponsCollection = new Hashtable<>();
	private Map<Long, Income> incomeCollection = new Hashtable<>();

	public Customer() {

	}

	public Customer(long id, String customerName, String password, Map<Long, Coupon> couponsCollection) {

		setId(id);
		setCustomerName(customerName);
		setPassword(password);
		setCouponsCollection(couponsCollection);

	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCustomerName() {
		return CustomerName;
	}

	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	@ManyToMany
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

	public void setIncomeCollection(Map<Long, Income> incomeCollection) {
		this.incomeCollection = incomeCollection;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", CustomerName=" + CustomerName + ", Password=" + Password
				+ ", couponsCollection=" + couponsCollection + "]";
	}

}
