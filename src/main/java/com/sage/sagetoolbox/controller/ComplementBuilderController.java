package com.sage.sagetoolbox.controller;

import com.sage.sagetoolbox.model.Output;
import com.sage.sagetoolbox.service.ComplementBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ComplementBuilderController {
    private final ComplementBuilder complementBuilderService;

    public ComplementBuilderController(ComplementBuilder complementBuilderService) {
        this.complementBuilderService = complementBuilderService;
    }

    @GetMapping("/complement")
    public ResponseEntity<Output> getComplement(
            @RequestParam(value = "inputString", defaultValue = "00000000") String inputString,
            @RequestParam(value = "basis", defaultValue = "2") int basis,
            @RequestParam(value = "size", defaultValue = "8") int size) {

        return ResponseEntity.ok().body(
                complementBuilderService.buildBComplement(inputString, basis, size)
        );
    }
}
