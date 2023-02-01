package com.sage.sagetoolbox.controller;

import com.sage.sagetoolbox.model.Output;
import com.sage.sagetoolbox.service.ComplementBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ComplementBuilderController {
    @GetMapping("/complement")
    public ResponseEntity<Output> getComplement(
            @RequestParam(value = "inputString", defaultValue = "00000000") String inputString,
            @RequestParam(value = "radix", defaultValue = "2") int radix,
            @RequestParam(value = "length", defaultValue = "0") int length,
            @RequestParam(value = "getMinusOneComplement", defaultValue = "false") boolean getMinusOneComplement,
            @RequestParam(value = "interpretAsBinary", defaultValue = "true") boolean interpretAsBinary) {

        return ResponseEntity.ok().body(
                ComplementBuilder.formatOutput(inputString, radix, length, getMinusOneComplement, interpretAsBinary)
        );
    }
}
