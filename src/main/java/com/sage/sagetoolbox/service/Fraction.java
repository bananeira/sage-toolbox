package com.sage.sagetoolbox.service;

import static java.lang.Math.abs;

public class Fraction {
    private int numerator;

    private int denominator;

    public Fraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public Fraction() {
        this.numerator = 0;
        this.denominator = 1;
    }


    public int getNum() {
        return numerator;
    }

    public void setNum(int numerator) {
        this.numerator = numerator;
    }

    public int getDen() {
        return denominator;
    }

    public void setDen(int denominator) {
        if (denominator != 0) {
            if (denominator < 0) {
                this.denominator = abs(denominator);
                this.numerator = this.numerator * -1;
            } else {
                this.denominator = denominator;
            }
        }
    }
}
