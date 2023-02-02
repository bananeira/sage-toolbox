package com.sage.sagetoolbox;

import com.sage.sagetoolbox.service.NumberRadixConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class SageToolboxApplication {
	public static void main(String[] args) {
		SpringApplication.run(SageToolboxApplication.class, args);
		System.out.println(NumberRadixConverter.convertToRadix(List.of(0, 0, 0, 0, 0, 0, 0, 0), 2, 16));

	}
}