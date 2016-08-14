package com.ouyexie.pg.data;

/**
 * @author Ouye Xie
 */
public class IncreaseBanner extends Banner {
    public IncreaseBanner(double value, String name) {
        super(value, name);
        setNiceValue(Math.round(value * 10000.0d) / 100);
        setSuffix("%");
    }

    /*
     * Convenience method for creating an appropriately typed banner
     */
    public static IncreaseBanner create(double value, String name) {
        return new IncreaseBanner(value, name);
    }

    public String getContent() {
        return String.format("持有的%s，持有后涨幅达%s%s", name, niceValue, suffix);
    }
}
