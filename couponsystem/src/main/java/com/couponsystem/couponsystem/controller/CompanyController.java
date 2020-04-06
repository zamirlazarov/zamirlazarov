package com.couponsystem.couponsystem.controller;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.couponsystem.couponsystem.beans.Coupon;
import com.couponsystem.couponsystem.beans.CouponType;
import com.couponsystem.couponsystem.service.CompanyService;


@RestController
@RequestMapping("/company")
public class CompanyController {
	@Autowired
	private CompanyService companyService;

	@PostMapping("/createCoupon")
	public ResponseEntity<Object> createCoupon(@RequestParam String token, @RequestBody Coupon coupon) {
		return companyService.createCoupon(token, coupon);
	}

	@DeleteMapping("/deleteCoupon")
	public ResponseEntity<Object> deleteCoupon(@RequestParam String token, @RequestParam long couponId) {
		return companyService.deleteCoupon(token, couponId);
	}

	@PutMapping("/updateCoupon")
	public ResponseEntity<Object> updateCoupon(@RequestParam String token, @RequestBody Coupon coupon) {
		return companyService.updateCoupon(token, coupon);
	}

	@GetMapping("/getCompany")
	public ResponseEntity<Object> getCompany(@RequestParam String token) {
		return companyService.getCompany(token);
	}
	@GetMapping("/getAllCoupon")
	public ResponseEntity<Object> getAllCoupon(@RequestParam String token) {
		return companyService.getAllCoupon(token);
	}

	@GetMapping("/getCoupon/{couponType}")
	public ResponseEntity<Object> getCouponByType(@RequestParam String token,@PathVariable CouponType couponType) {
		return companyService.getCouponByType(token, couponType);
	}

	@GetMapping("/getCoupon/{price}")
	public ResponseEntity<Object> getCouponByPrice(@RequestParam String token,@PathVariable double price) {
		return companyService.getCouponByPrice(token, price);
	}

	@GetMapping("/getCoupon/{localdatetime}")
	public ResponseEntity<Object> getCouponByDate(@RequestParam String token,@PathVariable LocalDateTime localdatetime) {
		return companyService.getCouponByDate(token, localdatetime);
	}
	
	@GetMapping("/viewIncome")
	public ResponseEntity<?> viewIncome(@RequestParam String token) {
		return companyService.viewIncome(token);
	}
}