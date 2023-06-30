package com.sage.sagetoolbox.controller;

import com.sage.sagetoolbox.service.RandomMatrixGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RandomGaussMatrixController {
    @GetMapping("/randomGaussMatrix")
    public ResponseEntity<List<String>> getRandomMatrix(
            @RequestParam(value = "max") int max,
            @RequestParam(value = "useFractions") boolean useFractions,
            @RequestParam(value = "m") int m,
            @RequestParam(value = "n") int n,
            @RequestParam(value = "numOfFreeVars") int numOfFreeVars,
            @RequestParam(value = "unsolvable") boolean unsolvable
            ) throws Exception {

        return ResponseEntity.ok().body(
                RandomMatrixGenerator.generateRandomMatrix(max, useFractions, m, n, numOfFreeVars, unsolvable)
        );
    }
}
