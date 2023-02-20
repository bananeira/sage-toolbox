package com.sage.sagetoolbox;

import com.sage.sagetoolbox.service.GenerateRSAValues;
import com.sage.sagetoolbox.service.PrimeFactorization;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SageToolboxApplication {
	public static void main(String[] args) {
		SpringApplication.run(SageToolboxApplication.class, args);
	}
}