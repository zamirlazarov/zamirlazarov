package com.couponsystem.couponsystem.service;

import org.springframework.http.ResponseEntity;

import com.couponsystem.couponsystem.beans.ClientType;

public interface CouponClient {


	ResponseEntity<?> login(String name, String password, ClientType clientType);
	
	ResponseEntity<?> logout(String token);
	
}
