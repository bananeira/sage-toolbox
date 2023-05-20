package com.sage.sagetoolbox.model;

import com.sage.sagetoolbox.service.Fraction;

import java.util.ArrayList;
import java.util.List;

public class GaussAlgorithmData {
    public int firstColumnDifferentFromZero;
    public int ignoreRowsAboveNeq = 0;
    public Fraction currentFirstInColumn;
    public final List<String> visualizedOperations = new ArrayList<>();
    public final List<Integer> operationsOnPass = new ArrayList<>();
    public final List<Fraction[][]> matrixHistory = new ArrayList<>();
    public int pass = 0;
}
