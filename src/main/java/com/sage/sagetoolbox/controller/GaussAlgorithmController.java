package com.sage.sagetoolbox.controller;

import com.sage.sagetoolbox.model.GaussAlgorithmOutput;
import com.sage.sagetoolbox.service.GaussAlgorithm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GaussAlgorithmController {
    @GetMapping("/gauss")
    public ResponseEntity<GaussAlgorithmOutput> getGaussAlgorithmOutput(
            @RequestParam(value = "m") int m,
            @RequestParam(value = "n") int n,
            @RequestParam(value = "matrixElements") String matrixElements) throws Exception {

        return ResponseEntity.ok().body(
                GaussAlgorithm.matrixToLineLevelForm(m, n, List.of(matrixElements.split(",")))
        );
    }
}
