package com.ouyexie.pg.data;

/**
 * @author Ouye Xie
 */
public class SimpleShareholderBanner extends Banner {
    private int rank;

    public SimpleShareholderBanner(double value, String name, int rank) {
        super(value, name);
        setNiceValue(Math.round(value * 10000.0d) / 100);
        setSuffix("%");
        this.rank = rank;
    }

    /*
     * Convenience method for creating an appropriately typed banner
     */
    public static SimpleShareholderBanner create(double value, String name, int rank) {
        return new SimpleShareholderBanner(value, name, rank);
    }

    public int getRank() {
        return rank;
    }

    public String getContent() {
        return String.format("占股比例%s%s，第%s大股东", ((double) niceValue) / 100.0d, suffix, rank);
    }
}
