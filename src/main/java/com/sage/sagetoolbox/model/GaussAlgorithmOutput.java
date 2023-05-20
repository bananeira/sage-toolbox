package com.sage.sagetoolbox.model;

import com.sage.sagetoolbox.service.Fraction;

import java.util.List;

public class GaussAlgorithmOutput {
    public List<Fraction[][]> matrixHistory;
    public List<String> matrixOperations;
    public List<Integer> operationsOnPass;
    public List<Fraction[][]> eqSysTranformationHistory;
    public boolean containsIllegalEquation;
}
