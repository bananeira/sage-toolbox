package com.sage.sagetoolbox;

import com.sage.sagetoolbox.service.GaussAlgorithm;
import com.sage.sagetoolbox.service.GaussMatrix;
import com.sage.sagetoolbox.service.SolveEquationSystem;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class SageToolboxApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(SageToolboxApplication.class, args);
		GaussAlgorithm.matrixToLineLevelForm(4,7, List.of("1","2","3","-1","-1","2","1","0","1","3","2","0","0","1","0","0","0","0","1","-3","-2"));
		//GaussAlgorithm.matrixToLineLevelForm(4,7, List.of(1,2,3,-1,-1,2,1,0,1,3,2,0,0,1,0,0,0,0,1,-3,-2));
		SolveEquationSystem.solveSystem(GaussMatrix.getMatrix());
	}
}