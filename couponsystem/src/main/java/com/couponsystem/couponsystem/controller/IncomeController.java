package com.couponsystem.couponsystem.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.couponsystem.couponsystem.beans.Income;
import com.couponsystem.couponsystem.service.IncomeService;



@RestController
@RequestMapping("/income")
public class IncomeController {

	@Autowired
	private IncomeService incomeService;

	@PostMapping("/storeIncome")
	public ResponseEntity<?> storeIncome(@RequestBody Income income) {
		return incomeService.storeIncome(income);
	}

	@GetMapping("/viewAllIncome")
	public ResponseEntity<?> viewAllIncome() {
		return incomeService.viewAllIncome();
	}

	@GetMapping("/viewIncomeByCustomer")
	public ResponseEntity<?> viewIncomeByCustomer(@RequestParam long customerId) {
		return incomeService.viewIncomeByCustomer(customerId);
	}

	@GetMapping("/viewIncomeByCompany")
	public ResponseEntity<?> viewIncomeByCompany(@RequestParam long companyId) {
		return incomeService.viewIncomeByCompany(companyId);
	}

}