package com.couponsystem.couponsystem.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.couponsystem.couponsystem.beans.ClientType;
import com.couponsystem.couponsystem.beans.Coupon;
import com.couponsystem.couponsystem.beans.CouponType;
import com.couponsystem.couponsystem.beans.Customer;
import com.couponsystem.couponsystem.beans.Income;
import com.couponsystem.couponsystem.beans.IncomeType;
import com.couponsystem.couponsystem.dao.CouponRepository;
import com.couponsystem.couponsystem.dao.CustomerRepository;


@Service
public class CustomerService implements CouponClient {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CouponRepository couponRepository;
	
	
	@Autowired
	RestTemplate restTemplate;

	private Map<String, Long> tokens = new Hashtable<>();

	@Override
	public ResponseEntity<?> login(String name, String password, ClientType clientType) {
		Customer customer = customerRepository.findCustomerByCustomerName(name);
		if (customer != null) {
			if (customer.getPassword().equalsIgnoreCase(password)) {
				String token = UUID.randomUUID().toString();
				tokens.put(token, customer.getId());
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

	public ResponseEntity<Object> purchaseCoupon(String token, long couponId) {
		if (tokens.containsKey(token)) {
			Customer customer = customerRepository.findCustomerById(tokens.get(token));
			if (customer != null) {
				Coupon coupon = couponRepository.findCouponById(couponId);
				if (coupon != null) {
					if (coupon.getAmount() > 0) {
						coupon.setAmount(coupon.getAmount() - 1);
						long id = couponRepository.save(coupon).getCouponId();
						if (customer.getCouponsCollection() != null) {
							customer.getCouponsCollection().put(id, coupon);
							Income income=new Income();
							income.setAmount(coupon.getPrice());
							income.setDate(LocalDateTime.now());
							income.setName(customer.getCustomerName());
							income.setDescription(IncomeType.CUSTOMER_PURCHASE);
							ResponseEntity<Income> response =restTemplate.exchange("http://localhost:5000/income/storeIncome",HttpMethod.POST,null,new ParameterizedTypeReference<Income>() {} );
							HttpStatus status = response.getStatusCode();
							if (status==HttpStatus.OK) {
								Income incomeResponse= response.getBody();
								if (customer.getIncomeCollection()!=null) {
									customer.getIncomeCollection().put(incomeResponse.getId(), incomeResponse);
									return ResponseEntity.ok(customerRepository.save(customer));
								} else {
									customer.setIncomeCollection(new Hashtable<>());
									customer.getIncomeCollection().put(incomeResponse.getId(), incomeResponse);
									return ResponseEntity.ok(customerRepository.save(customer));
								}
							}
						}
					}
				}
				return ResponseEntity.badRequest().body("Coupon cant be null");
			}
			return ResponseEntity.badRequest().body("Cant find Customer");
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please login");
	}

	public ResponseEntity<Object> getAllHistory(String token) {
		if (tokens.containsKey(token)) {
			Customer customer = customerRepository.findCustomerById(tokens.get(token));
			if (customer != null) {
				if(customer.getCouponsCollection()!=null && customer.getCouponsCollection().size()>0) {
					ResponseEntity.ok(customer.getCouponsCollection());
				}
			}
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please login");
	}

	public ResponseEntity<Object> getHistoryByType(String token, CouponType couponType) {
		List<Coupon> couponsList=new ArrayList<>();
		if (tokens.containsKey(token)) {
			Customer customer = customerRepository.findCustomerById(tokens.get(token));
			if (customer != null) {
				if(customer.getCouponsCollection()!=null && customer.getCouponsCollection().size()>0) {
					for (Map.Entry<Long, Coupon> entry : customer.getCouponsCollection().entrySet()) {
						if (entry.getValue().getType().equals(couponType)) {
							couponsList.add(entry.getValue());
						}
					}
					return ResponseEntity.ok(couponsList);
				}
				return ResponseEntity.badRequest().body("No coupons available");
			}
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please login");
	}

	public ResponseEntity<Object> getHistoryByPrice(String token, double price) {
		List<Coupon> couponsList=new ArrayList<>();
		if (tokens.containsKey(token)) {
			Customer customer = customerRepository.findCustomerById(tokens.get(token));
			if (customer != null) {
				if(customer.getCouponsCollection()!=null && customer.getCouponsCollection().size()>0) {
					for (Map.Entry<Long, Coupon> entry : customer.getCouponsCollection().entrySet()) {
						if (entry.getValue().getPrice()==price) {
							couponsList.add(entry.getValue());
						}
					}
					return ResponseEntity.ok(couponsList);
				}
				return ResponseEntity.badRequest().body("No coupons available");
			}
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please login");
	}
	
	public ResponseEntity<?> viewIncome(@RequestParam String token) {
		if (tokens.containsKey(token)) {
			Optional<Customer> customer = customerRepository.findById(tokens.get(token));
			if (customer.isPresent()) {
				ResponseEntity<String> responseString = restTemplate.getForEntity(
						"http://localhost:5000/income/viewIncomeByCustomer?customerId={customerId}", String.class,
						customer.get().getId());
				HttpStatus status = responseString.getStatusCode();
				if (status == HttpStatus.OK) {
					ResponseEntity.ok(responseString);
				}
			}
			return ResponseEntity.badRequest().body("Cant find Customer");
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("please login!");
	}
	
}
