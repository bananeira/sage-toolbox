package com.sage.sagetoolbox.service;

import java.math.BigInteger;
import java.util.Objects;

public class Fraction {
    private BigInteger numerator;

    private BigInteger denominator;

    public Fraction(BigInteger numerator, BigInteger denominator) {
        this.numerator = numerator;
        this.denominator = denominator;

        if (denominator.compareTo(BigInteger.ZERO) < 0) {
            this.numerator = this.numerator.multiply(BigInteger.valueOf(-1));
            this.denominator = this.denominator.multiply(BigInteger.valueOf(-1));
        }

        shortenFractionsMax();
    }

    public Fraction(Fraction fraction) {
        this.numerator = fraction.getNum();
        this.denominator = fraction.getDen();

        if (denominator.compareTo(BigInteger.ZERO) < 0) {
            this.denominator = this.denominator.multiply(BigInteger.valueOf(-1));
            this.numerator = this.numerator.multiply(BigInteger.valueOf(-1));
        }

        shortenFractionsMax();
    }

    public Fraction() {
        this.numerator = BigInteger.ZERO;
        this.denominator = BigInteger.ONE;
    }

    public BigInteger getNum() {
        return numerator;
    }

    public void setNum(BigInteger numerator) {
        this.numerator = numerator;

        shortenFractionsMax();
    }

    public BigInteger getDen() {
        return denominator;
    }

    public void setDen(BigInteger denominator) {
        if (!Objects.equals(denominator, BigInteger.ZERO)) {
            if (denominator.compareTo(BigInteger.ZERO) < 0) {
                this.denominator = this.denominator.multiply(BigInteger.valueOf(-1));
                this.numerator = this.numerator.multiply(BigInteger.valueOf(-1));
            } else {
                this.denominator = denominator;
            }
        }

        shortenFractionsMax();
    }

    public void shortenFractionsMax() {
        BigInteger divisor = EuclideanAlgorithm.fastGCD(this.numerator, this.denominator);

        if (Objects.equals(divisor, BigInteger.ONE) || Objects.equals(divisor, BigInteger.ZERO)) return;

        this.numerator = this.numerator.divide(divisor);
        this.denominator = this.denominator.divide(divisor);
    }

    public void add(Fraction fraction) {
        fraction = new Fraction(fraction.getNum(), fraction.getDen());
        fraction.setNum(fraction.numerator.multiply(this.denominator));

        this.denominator = this.denominator.multiply(fraction.getDen());
        this.numerator = this.numerator.multiply(fraction.getDen());

        this.numerator = this.numerator.add(fraction.getNum());
        shortenFractionsMax();
    }

    public void subtract(Fraction fraction) {
        fraction = new Fraction(fraction.getNum(), fraction.getDen());
        fraction.setNum(fraction.numerator.multiply(this.denominator));

        this.denominator = this.denominator.multiply(fraction.getDen());
        this.numerator = this.numerator.multiply(fraction.getDen());

        this.numerator = this.numerator.subtract(fraction.getNum());
        shortenFractionsMax();
    }

    public void multiply(BigInteger factor) {
        this.numerator = this.numerator.multiply(factor);
        shortenFractionsMax();
    }

    public void invert() {
        BigInteger temp = this.numerator;
        this.numerator = this.denominator;
        if (temp.compareTo(BigInteger.ZERO) != 0) {
            this.denominator = temp;
        }
    }

    public void multiply(Fraction fraction) {
        fraction = new Fraction(fraction.getNum(), fraction.getDen());
        this.numerator = this.numerator.multiply(fraction.getNum());
        this.denominator = this.denominator.multiply(fraction.getDen());
        shortenFractionsMax();
    }

    public void divide(Fraction fraction) {
        fraction = new Fraction(fraction.getNum(), fraction.getDen());
        this.numerator = this.numerator.multiply(fraction.getDen());
        this.denominator = this.denominator.multiply(fraction.getNum());
        shortenFractionsMax();
    }

    @Override
    public String toString() {
        return Objects.equals(this.denominator, BigInteger.ONE) ? String.valueOf(this.numerator) : this.numerator + "/" + this.denominator;
    }
}
