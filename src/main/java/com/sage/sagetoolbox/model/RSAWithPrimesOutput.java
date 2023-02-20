package com.sage.sagetoolbox.model;

import java.util.List;

public class RSAWithPrimesOutput {
    public int eulerTotient;
    public List<Integer> totientComponents;
    public List<List<Integer>> divisorFormatList;
    public List<List<Integer>> extendedGCDList;
    public int exception = 0;
}
