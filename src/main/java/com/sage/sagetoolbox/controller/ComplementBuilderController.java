package com.sage.sagetoolbox.controller;

import com.sage.sagetoolbox.service.ComplementBuilder;
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
    public String getComplement(
            @RequestParam(value = "inputString", defaultValue = "00000000") String inputString,
            @RequestParam(value = "basis", defaultValue = "2") int basis,
            @RequestParam(value = "size", defaultValue = "8") int size) {
        return complementBuilderService.buildBComplement(inputString, basis, size);
    }

    @GetMapping("/b-1-complement")
    public String getComplement(
            @RequestParam(value = "inputString", defaultValue = "00000000") String inputString,
            @RequestParam(value = "basis", defaultValue = "2") int basis) {
        return complementBuilderService.stringToBMinusOneComplement(inputString, basis);
    }
}
