package com.sage.sagetoolbox.controller;

import com.sage.sagetoolbox.model.RSAWithKeyOutput;
import com.sage.sagetoolbox.model.RSAWithPrimesOutput;
import com.sage.sagetoolbox.service.RSAProcedure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RSAController {
    @GetMapping("/rsaWithPrimes")
    public ResponseEntity<RSAWithPrimesOutput> getRSAPrimes(
            @RequestParam(value = "primeP", defaultValue = "0") int primeP,
            @RequestParam(value = "primeQ", defaultValue = "0") int primeQ,
            @RequestParam(value = "encryptKey", defaultValue = "0") int encryptKey) {
        return ResponseEntity.ok().body(
                RSAProcedure.rsaWithPrimes(primeP, primeQ, encryptKey)
        );
    }

    @GetMapping("/rsaWithKey")
    public ResponseEntity<RSAWithKeyOutput> getRSAKeys(
            @RequestParam(value = "encryptKey", defaultValue = "0") int encryptKey,
            @RequestParam(value = "N", defaultValue = "0") int N) {
        return ResponseEntity.ok().body(
                RSAProcedure.rsaWithKey(encryptKey, N)
        );
    }
}
