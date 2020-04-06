package com.couponsystem.couponsystem.service;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.couponsystem.couponsystem.beans.ClientType;
import com.couponsystem.couponsystem.beans.Company;
import com.couponsystem.couponsystem.beans.Customer;
import com.couponsystem.couponsystem.dao.CompanyRepository;
import com.couponsystem.couponsystem.dao.CouponRepository;
import com.couponsystem.couponsystem.dao.CustomerRepository;


@Service
public class AdminService implements CouponClient {

	@Autowired
	private CompanyRepository companyrepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	
	
	@Autowired
	private RestTemplate restTemplate;

	private Map<String, Long> tokens = new Hashtable<>();

	@Override
	public ResponseEntity<?> login(String name, String password, ClientType clientType) {
		if (name.equalsIgnoreCase("admin")) {
			if (password.equalsIgnoreCase("1234")) {
				String token = UUID.randomUUID().toString();
				tokens.put(token, 1L);
				return ResponseEntity.ok("Logged in as " + clientType.toString() + " your token is: " + token);
			}
		}
		return ResponseEntity.badRequest().body("User name or password incorrect");
	}

	@Override
	public ResponseEntity<?> logout(String token) {
		if (tokens.containsKey(token)) {
			tokens.remove(token);
			return ResponseEntity.ok("Logged out successfully");
		}
		return ResponseEntity.badRequest().body("Token Doesnt exist");
	}

	@GetMapping("/getAllCompanies")
	public ResponseEntity<Object> getAllCompanies(String token) {
		if (tokens.containsKey(token)) {
			return ResponseEntity.ok(companyrepository.findAll());
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please login");
	}

	@GetMapping("/getCompany")
	public ResponseEntity<Object> getCompany(String token, long companyId) {
		if (tokens.containsKey(token)) {
			return ResponseEntity.ok(companyrepository.findCompanyById(companyId));
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please login");
	}

	public ResponseEntity<Object> addCompany(String token, Company company) {
		if (tokens.containsKey(token)) {
			return ResponseEntity.ok(companyrepository.save(company));
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please login");
	}

	public ResponseEntity<Object> deleteCompany(String token, long companyId) {
		if (tokens.containsKey(token)) {
			Optional<Company> cOptional = companyrepository.findById(companyId);
			if (cOptional.isPresent()) {
				companyrepository.deleteById(companyId);
				return ResponseEntity.ok("Company with id " + companyId + " have been deleted");
			}
			return ResponseEntity.badRequest().body("No company with id " + companyId + " found");
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please login");
	}

	public ResponseEntity<Object> updateCompany(String token, Company company) {
		if (tokens.containsKey(token)) {
			Optional<Company> cOptional = companyrepository.findById(company.getId());
			if (cOptional.isPresent()) {
				company.setCouponsCollection(cOptional.get().getCouponsCollection());
				return ResponseEntity.ok(companyrepository.save(company));
			}
			return ResponseEntity.badRequest().body("No company with id " + company.getId() + " found");
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please login");
	}

	public ResponseEntity<Object> getAllCustomers(String token) {
		if (tokens.containsKey(token)) {
			return ResponseEntity.ok(customerRepository.findAll());
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please login");
	}

	public ResponseEntity<Object> getCustomer(String token, long customerId) {
		if (tokens.containsKey(token)) {
			return ResponseEntity.ok(customerRepository.findCustomerById(customerId));
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please login");
	}

	public ResponseEntity<Object> addCustomer(String token, Customer customer) {
		if (tokens.containsKey(token)) {
			return ResponseEntity.ok(customerRepository.save(customer));
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please login");
	}

	public ResponseEntity<Object> deleteCustomer(String token, long customerId) {
		if (tokens.containsKey(token)) {
			Optional<Customer> cOptional = customerRepository.findById(customerId);
			if (cOptional.isPresent()) {
				customerRepository.deleteById(customerId);
				return ResponseEntity.ok("Customer with id " + customerId + " have been deleted");
			}
			return ResponseEntity.badRequest().body("No customer with id " + customerId + " found");
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please login");
	}

	public ResponseEntity<Object> updateCustomer(String token, Customer customer) {
		if (tokens.containsKey(token)) {
			Optional<Customer> cOptional = customerRepository.findById(customer.getId());
			if (cOptional.isPresent()) {
				customer.setCouponsCollection(cOptional.get().getCouponsCollection());
				return ResponseEntity.ok(customerRepository.save(customer));
			}
			return ResponseEntity.badRequest().body("No customer with id " + customer.getId() + " found");
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please login");
	}

	public ResponseEntity<?> viewAllIncome(String token) {
		if (tokens.containsKey(token)) {
			ResponseEntity<String> responseString = restTemplate
					.getForEntity("http://localhost:5000/income/viewAllIncome", String.class);
			HttpStatus status = responseString.getStatusCode();
			if (status == HttpStatus.OK) {
				ResponseEntity.ok(responseString);
			}

		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please login");
	}

	public ResponseEntity<?> viewIncomeByCustomer(String token, long customerId) {
		if (tokens.containsKey(token)) {
			ResponseEntity<String> responseString = restTemplate.getForEntity(
					"http://localhost:5000/income/viewIncomeByCustomer?customerId={customerId}", String.class,
					customerId);
			HttpStatus status = responseString.getStatusCode();
			if (status == HttpStatus.OK) {
				ResponseEntity.ok(responseString);
			}
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please login");
	}

	public ResponseEntity<?> viewIncomeByCompany(String token, long companyId) {
		if (tokens.containsKey(token)) {
			ResponseEntity<String> responseString = restTemplate.getForEntity(
					"http://localhost:5000/income/viewIncomeByCompany?companyId={companyId}", String.class, companyId);
			HttpStatus status = responseString.getStatusCode();
			if (status == HttpStatus.OK) {
				ResponseEntity.ok(responseString);
			}
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please login");
	}
	
}
