package com.couponsystem.couponsystem.dao;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.couponsystem.couponsystem.beans.Coupon;
import com.couponsystem.couponsystem.beans.CouponType;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

	
	
	public Coupon findCouponById(long id);
	public Coupon findCouponBytitle(String title);
	public Coupon findCouponBycouponType(CouponType couponType);
	public Coupon findCouponByprice(double price);
	public Coupon findCouponByendDate(LocalDateTime endDate);
	
}
