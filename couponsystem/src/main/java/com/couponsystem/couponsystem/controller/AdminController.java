package com.couponsystem.couponsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.couponsystem.couponsystem.beans.Company;
import com.couponsystem.couponsystem.beans.Customer;
import com.couponsystem.couponsystem.service.AdminService;


@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	
	@GetMapping("/getAllCompanies")
	public ResponseEntity<Object> getAllCompanies(@RequestParam String token) {
		return adminService.getAllCompanies(token);
	}
	
	@GetMapping("/getCompany")
	public ResponseEntity<Object> getCompany(@RequestParam String token,@RequestParam long companyId) {
		return adminService.getCompany(token, companyId);
	}
	
	@PostMapping("/addCompany")
	public ResponseEntity<Object> addCompany(@RequestParam String token,@RequestBody Company company) {
		return adminService.addCompany(token, company);
	}
	
	@DeleteMapping("/deleteCompany")
	public ResponseEntity<Object> deleteCompany(@RequestParam String token,@RequestParam long companyId) {
		return adminService.deleteCompany(token, companyId);
	}
	
	@PutMapping("/updateCompany")
	public ResponseEntity<Object> updateCompany(@RequestParam String token,@RequestBody Company company) {
		return adminService.updateCompany(token, company);
	}
	
	@GetMapping("/getAllCustomers")
	public ResponseEntity<Object> getAllCustomers(@RequestParam String token) {
		return adminService.getAllCustomers(token);
	}
	
	@GetMapping("/getCustomer")
	public ResponseEntity<Object> getCustomer(@RequestParam String token,@RequestParam long customerId) {
		return adminService.getCustomer(token, customerId);
	}
	
	@PostMapping("/addCustomer")
	public ResponseEntity<Object> addCustomer(@RequestParam String token,@RequestBody Customer customer) {
		return adminService.addCustomer(token, customer);
	}
	
	@DeleteMapping("/deleteCustomer")
	public ResponseEntity<Object> deleteCustomer(@RequestParam String token,@RequestParam long customerId) {
		return adminService.deleteCustomer(token, customerId);
	}
	
	@PutMapping("/updateCustomer")
	public ResponseEntity<Object> updateCustomer(@RequestParam String token,@RequestBody Customer customer) {
		return adminService.updateCustomer(token, customer);
	}
	
	@GetMapping("/viewAllIncome")
	public ResponseEntity<?> viewAllIncome(@RequestParam String token) {
		return adminService.viewAllIncome(token);
	}

	@GetMapping("/viewIncomeByCustomer")
	public ResponseEntity<?> viewIncomeByCustomer(@RequestParam String token,@RequestParam long customerId) {
		return adminService.viewIncomeByCustomer(token,customerId);
	}

	@GetMapping("/viewIncomeByCompany")
	public ResponseEntity<?> viewIncomeByCompany(@RequestParam String token,@RequestParam long companyId) {
		return adminService.viewIncomeByCompany(token,companyId);
	}
}
	
