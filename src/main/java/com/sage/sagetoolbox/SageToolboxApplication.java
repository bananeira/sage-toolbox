package com.sage.sagetoolbox;

import com.sage.sagetoolbox.service.GaussMatrices;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SageToolboxApplication {
	public static void main(String[] args) {
		SpringApplication.run(SageToolboxApplication.class, args);

		GaussMatrices.setMatrix(4,3, List.of(1,2,3,4,5,6,7,8,9));
		System.out.println(Arrays.deepToString(GaussMatrices.getMatrix()));
		GaussMatrices.mult(1, new int[]{5,2});
		System.out.println(Arrays.deepToString(GaussMatrices.getMatrix()));
		GaussMatrices.swap(1, 2);
		System.out.println(Arrays.deepToString(GaussMatrices.getMatrix()));
		GaussMatrices.add(new int[]{5,3}, 0, 1);
		System.out.println(Arrays.deepToString(GaussMatrices.getMatrix()));
	}
}