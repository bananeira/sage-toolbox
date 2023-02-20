package com.sage.sagetoolbox.model;

import java.util.List;

public class RSAWithKeyOutput {
    public int eulerTotient;
    public List<Integer> totientComponents;
    public List<Integer> primeFactors;
    public List<List<Integer>> divisorFormatList;
    public List<List<Integer>> extendedGCDList;
    public int exception = 0;
}
