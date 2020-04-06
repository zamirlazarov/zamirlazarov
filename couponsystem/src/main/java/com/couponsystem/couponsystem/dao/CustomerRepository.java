package com.couponsystem.couponsystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.couponsystem.couponsystem.beans.ClientType;
import com.couponsystem.couponsystem.beans.CouponType;
import com.couponsystem.couponsystem.beans.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

public Customer findCustomerById(long id);
	
	public Customer findCustomerByCustomerName(String customerName);
}
