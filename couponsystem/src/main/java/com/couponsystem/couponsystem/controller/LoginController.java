package com.couponsystem.couponsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.couponsystem.couponsystem.beans.ClientType;
import com.couponsystem.couponsystem.service.AdminService;
import com.couponsystem.couponsystem.service.CompanyService;
import com.couponsystem.couponsystem.service.CustomerService;

@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CompanyService companyService;

	public ResponseEntity<?> login(@RequestParam String userName, @RequestParam String password,
			@RequestParam ClientType clientType) {
		switch (clientType) {
		case ADMIN:
			return adminService.login(userName, password, clientType);

		case COMPANY:
			return companyService.login(userName, password, clientType);

		case CUSTOMER:
			return customerService.login(userName, password, clientType);
		}
		return ResponseEntity.badRequest().body("Something went wrong");
	}
	
	public ResponseEntity<?> logout(@RequestParam String token,@RequestParam ClientType clientType) {
		switch (clientType) {
		case ADMIN:
			return adminService.logout(token);

		case COMPANY:
			return companyService.logout(token);

		case CUSTOMER:
			return customerService.logout(token);
		}
		return ResponseEntity.badRequest().body("Something went wrong");
	}

	
	
	
}
