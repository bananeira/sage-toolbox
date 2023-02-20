package com.sage.sagetoolbox.service;

import java.util.ArrayList;
import java.util.List;

public class ExtendedEulerTotientFunction {
    public static List<Integer> findEulersTotient(List<Integer> factorList) {
        List<Integer> totientComponents = new ArrayList<>();
        int lastFactor = 0;

        for (Integer factor : factorList) {
            if (totientComponents.contains(factor - 1) && factor == lastFactor) {
                totientComponents.add(factor);
            } else {
              totientComponents.add(factor);
              totientComponents.add(1);
            }
            // if the local totient (n - 1) of the factor is already contained in the totientComponents list, then
            // we will just add n. for the case of the transition from the factor 2 to 3, we need a clarification
            // on whether 2 is the local totient (n - 1) of 3 or the factor 2. we do that by comparing whether
            // the current and recent factors in the factorList have already transitioned to a new and thus are inequal
            // or not.
            lastFactor = factor;
        }

        return totientComponents;
    }
}
