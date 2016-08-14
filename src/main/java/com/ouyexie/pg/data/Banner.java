package com.ouyexie.pg.data;

/**
 * @author Ouye Xie
 */
public abstract class Banner {
    protected double value;
    protected String name;
    protected long niceValue;
    protected String suffix;

    public Banner() {
    }

    public Banner(double value, String name) {
        this.value = value;
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getNiceValue() {
        return niceValue;
    }

    public void setNiceValue(long niceValue) {
        this.niceValue = niceValue;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public abstract String getContent();
}
