package com.couponsystem.couponsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication

@EnableJpaRepositories
public class CouponsystemApplication {

	public static void main(String[] args) {
		//ApplicationContext ctx = SpringApplication.run(CouponsystemApplication.class, args);

		SpringApplication.run(CouponsystemApplication.class, args);
	}
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
}
