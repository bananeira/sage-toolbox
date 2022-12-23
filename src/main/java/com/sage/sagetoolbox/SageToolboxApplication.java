package com.sage.sagetoolbox;

import com.sage.sagetoolbox.tool.ComplementBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SageToolboxApplication {

	public static void main(String[] args) {
		SpringApplication.run(SageToolboxApplication.class, args);
	}

	@GetMapping("/complement")
	public String getComplement(
			@RequestParam(value = "inputString", defaultValue = "00000000") String inputString,
			@RequestParam(value = "basis", defaultValue = "2") int basis,
			@RequestParam(value = "size", defaultValue = "8") int size) {
		return ComplementBuilder.buildBComplement(inputString, basis, size);
	}

	@GetMapping("/b-1-complement")
	public String getComplement(
			@RequestParam(value = "inputString", defaultValue = "00000000") String inputString,
			@RequestParam(value = "basis", defaultValue = "2") int basis) {
		return ComplementBuilder.stringToBMinusOneComplement(inputString, basis);
	}
}
