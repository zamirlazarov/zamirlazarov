package com.couponsystem.couponsystem.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.couponsystem.couponsystem.beans.ClientType;
import com.couponsystem.couponsystem.beans.Company;
import com.couponsystem.couponsystem.beans.CouponType;

public interface CompanyRepository extends JpaRepository<Company, Long> {

	
	public Company findCompanyById(long id);
	public Company findCompanyBycompName(String compName);
	
}
