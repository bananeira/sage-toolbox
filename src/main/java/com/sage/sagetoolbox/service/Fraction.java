package com.sage.sagetoolbox.service;

import java.math.BigInteger;
import java.util.Objects;

public class Fraction {
    private BigInteger numerator;

    private BigInteger denominator;

    public Fraction(BigInteger numerator, BigInteger denominator) {
        this.numerator = numerator;
        this.denominator = denominator;

        if (denominator.compareTo(BigInteger.valueOf(0)) < 0) {
            this.denominator = this.denominator.multiply(BigInteger.valueOf(-1));
            this.numerator = this.numerator.multiply(BigInteger.valueOf(-1));
        }
    }

    public Fraction() {
        this.numerator = BigInteger.valueOf(0);
        this.denominator = BigInteger.valueOf(1);
    }


    public BigInteger getNum() {
        return numerator;
    }

    public void setNum(BigInteger numerator) {
        this.numerator = numerator;
    }

    public BigInteger getDen() {
        return denominator;
    }

    public void setDen(BigInteger denominator) {
        if (!Objects.equals(denominator, BigInteger.valueOf(0))) {
            if (denominator.compareTo(BigInteger.valueOf(0)) < 0) {
                this.denominator = this.denominator.multiply(BigInteger.valueOf(-1));
                this.numerator = this.numerator.multiply(BigInteger.valueOf(-1));
            } else {
                this.denominator = denominator;
            }
        }
    }

    public void shortenFractionsMax() {
        Fraction toShortened = new Fraction(this.numerator, this.denominator);
        BigInteger divisor = EuclideanAlgorithm.fastGCD(toShortened.getNum(), toShortened.getDen());

        if (Objects.equals(divisor, BigInteger.valueOf(1))) return;

        this.numerator = this.numerator.divide(divisor);
        this.denominator = this.denominator.divide(divisor);
    }

    @Override
    public String toString() {
        return this.numerator + "/" + this.denominator;
    }
}
