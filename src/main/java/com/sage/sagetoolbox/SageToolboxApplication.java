package com.sage.sagetoolbox;

import com.sage.sagetoolbox.service.ComplementBuilder;
import com.sage.sagetoolbox.service.NumberRadixConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class SageToolboxApplication {
	public static void main(String[] args) {
		SpringApplication.run(SageToolboxApplication.class, args);
		System.out.println(ComplementBuilder.formatOutput("7fe30a", 16, 0, false, true));
	}
}