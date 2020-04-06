package com.couponsystem.couponsystem.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.couponsystem.couponsystem.beans.ClientType;
import com.couponsystem.couponsystem.beans.Company;
import com.couponsystem.couponsystem.beans.Coupon;
import com.couponsystem.couponsystem.beans.CouponType;
import com.couponsystem.couponsystem.beans.Income;
import com.couponsystem.couponsystem.beans.IncomeType;
import com.couponsystem.couponsystem.dao.CompanyRepository;
import com.couponsystem.couponsystem.dao.CouponRepository;



@Service
public class CompanyService implements CouponClient {

	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private CouponRepository couponRepository;
	
	@Autowired
	RestTemplate restTemplate;

	private Map<String, Long> tokens = new Hashtable<>();

	@Override
	public ResponseEntity<?> login(String name, String password, ClientType clientType) {
		Company company = companyRepository.findCompanyBycompName(name);
		if (company != null) {
			if (company.getPassword().equalsIgnoreCase(password)) {
				String token = UUID.randomUUID().toString();
				tokens.put(token, company.getId());
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

	public ResponseEntity<Object> createCoupon(String token, @RequestBody Coupon coupon) {
		if (tokens.containsKey(token)) {
			Company company = companyRepository.findCompanyById(tokens.get(token));
			if (company != null) {
				if (coupon != null) {
					long id = couponRepository.save(coupon).getCouponId();
					company.getCouponsCollection().put(id, coupon);
					Income income = new Income();
					income.setAmount(100);
					income.setDate(LocalDateTime.now());
					income.setName(company.getCompanyName());
					income.setDescription(IncomeType.COMPANY_NEW_COUPON);
					ResponseEntity<Income> response = restTemplate.exchange("http://localhost:5000/income/storeIncome",
							HttpMethod.POST, null, new ParameterizedTypeReference<Income>() {
							});
					HttpStatus status = response.getStatusCode();
					if (status == HttpStatus.OK) {
						Income incomeResponse = response.getBody();
						if (company.getIncomeCollection() != null) {
							company.getIncomeCollection().put(incomeResponse.getId(), incomeResponse);
							return ResponseEntity.ok(companyRepository.save(company));
						} else {
							company.setIncomeCollection(new Hashtable<>());
							company.getIncomeCollection().put(incomeResponse.getId(), incomeResponse);
							return ResponseEntity.ok(companyRepository.save(company));
						}
					}
				}
				return ResponseEntity.badRequest().body("Coupon cant be null");
			}
			return ResponseEntity.badRequest().body("Cant find Company");
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("please login!");
	}

	public ResponseEntity<Object> deleteCoupon(String token, long couponId) {
		if (tokens.containsKey(token)) {
			Company company = companyRepository.findCompanyById(tokens.get(token));
			Optional<Coupon> coupon = couponRepository.findById(couponId);
			if (company != null) {
				if (coupon.isPresent()) {
					if (company.getCouponsCollection() != null && company.getCouponsCollection().size() > 0) {
						company.getCouponsCollection().remove(company.getId(), couponRepository.findCouponById(couponId));
						couponRepository.deleteById(couponId);
						return ResponseEntity.ok("Coupon Delete sucess");
					}
					return ResponseEntity.badRequest().body("Company couponCollection is empty");
				}
				return ResponseEntity.badRequest().body("Cant find Coupon");
			}
			return ResponseEntity.badRequest().body("Cant find Company");
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("please login!");
	}

	public ResponseEntity<Object> updateCoupon(String token, @RequestBody Coupon coupon) {
		if (tokens.containsKey(token)) {
			Company company = companyRepository.findCompanyById(tokens.get(token));
			if (company != null) {
				Optional<Coupon> dbCoupon = couponRepository.findById(coupon.getCouponId());
				if (dbCoupon.isPresent()) {
					couponRepository.save(coupon);
					company.getCouponsCollection().put(coupon.getCouponId(), coupon);
					Income income = new Income();
					income.setAmount(10);
					income.setDate(LocalDateTime.now());
					income.setName(company.getCompanyName());
					income.setDescription(IncomeType.COMPANY_UPDATE_COUPON);
					ResponseEntity<Income> response = restTemplate.exchange("http://localhost:5000/income/storeIncome",
							HttpMethod.POST, null, new ParameterizedTypeReference<Income>() {
							});
					HttpStatus status = response.getStatusCode();
					if (status == HttpStatus.OK) {
						Income incomeResponse = response.getBody();
						if (company.getIncomeCollection() != null) {
							company.getIncomeCollection().put(incomeResponse.getId(), incomeResponse);
							return ResponseEntity.ok(companyRepository.save(company));
						} else {
							company.setIncomeCollection(new Hashtable<>());
							company.getIncomeCollection().put(incomeResponse.getId(), incomeResponse);
							return ResponseEntity.ok(companyRepository.save(company));
						}
					}
				}
				return ResponseEntity.badRequest().body("Cant find Coupon");
			}
			return ResponseEntity.badRequest().body("Cant find Company");
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("please login!");
	}

	public ResponseEntity<Object> getCompany(String token) {
		if (tokens.containsKey(token)) {
			Company company = companyRepository.findCompanyById(tokens.get(token));
			if (company != null) {
				return ResponseEntity.ok(company);
			} else {
				return ResponseEntity.badRequest().body("Cant find Company");
			}
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("please login!");
		}
	}

	public ResponseEntity<Object> getAllCoupon(String token) {
		if (tokens.containsKey(token)) {
			Company company = companyRepository.findCompanyById(tokens.get(token));
			if (company != null) {
				return ResponseEntity.ok(company.getCouponsCollection());
			} else {
				return ResponseEntity.badRequest().body("Cant find Company");
			}
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("please login!");
		}
	}

	// TODO fix method
	public ResponseEntity<Object> getCouponByType(String token, CouponType couponType) {
		if (tokens.containsKey(token)) {
			Company company = companyRepository.findCompanyById(tokens.get(token));
			if (company != null) {
				return ResponseEntity.ok(couponRepository.findCouponBycouponType(couponType));
			} else {
				return ResponseEntity.badRequest().body("Cant find Company");
			}
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("please login!");
		}
	}

	// TODO fix method
	public ResponseEntity<Object> getCouponByPrice(String token, double price) {
		Company company = companyRepository.findCompanyById(tokens.get(token));
		if (tokens.containsKey(token)) {
			if (company != null) {
				return ResponseEntity.ok(couponRepository.findCouponByprice(price));
			} else {
				return ResponseEntity.badRequest().body("Cant find Company");
			}
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("please login!");
		}
	}

	// TODO fix method
	public ResponseEntity<Object> getCouponByDate(String token, LocalDateTime localdatetime) {
		if (tokens.containsKey(token)) {
			Company company = companyRepository.findCompanyById(tokens.get(token));
			if (company != null) {
				return ResponseEntity.ok(couponRepository.findCouponByendDate(localdatetime));
			} else {
				return ResponseEntity.badRequest().body("Cant find Company");
			}
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("please login!");
		}
	}

	public ResponseEntity<?> viewIncome(@RequestParam String token) {
		if (tokens.containsKey(token)) {
			Optional<Company> company = companyRepository.findById(tokens.get(token));
			if (company.isPresent()) {
				ResponseEntity<String> responseString = restTemplate.getForEntity(
						"http://localhost:5000/income/viewIncomeByCompany?companyId={companyId}", String.class,
						company.get().getId());
				HttpStatus status = responseString.getStatusCode();
				if (status == HttpStatus.OK) {
					ResponseEntity.ok(responseString);
				}
			}
			return ResponseEntity.badRequest().body("Cant find Company");
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("please login!");
	}
}
