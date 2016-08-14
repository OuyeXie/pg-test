package com.ouyexie.pg.data;


import java.io.Serializable;
import java.util.Objects;

/**
 * @author Ouye Xie
 */
public class InvestmentInGroup implements Serializable {
    private String id;
    private double issprice;
    private double actplaqty;
    private double currprice;
    private String publishdate;
    private String startCode;
    private String startName;
    private String endCode;
    private String endName;

    public InvestmentInGroup(String id, double issprice, double actplaqty, double currprice, String publishdate, String startCode, String startName, String endCode, String endName) {
        this.id = id;
        this.issprice = issprice;
        this.actplaqty = actplaqty;
        this.currprice = currprice;
        this.publishdate = publishdate;
        this.startCode = startCode;
        this.startName = startName;
        this.endCode = endCode;
        this.endName = endName;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof InvestmentInGroup)) {
            return false;
        }
        InvestmentInGroup p = (InvestmentInGroup) o;
        return Objects.equals(p.id, id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getIssprice() {
        return issprice;
    }

    public void setIssprice(double issprice) {
        this.issprice = issprice;
    }

    public double getActplaqty() {
        return actplaqty;
    }

    public void setActplaqty(double actplaqty) {
        this.actplaqty = actplaqty;
    }

    // this will do the trick if no currprice can be found
    public double getCurrprice() {
        return currprice == 0.0d ? issprice : currprice;
    }

    public void setCurrprice(double currprice) {
        this.currprice = currprice;
    }

    public String getPublishdate() {
        return publishdate;
    }

    public void setPublishdate(String publishdate) {
        this.publishdate = publishdate;
    }

    public String getStartCode() {
        return startCode;
    }

    public void setStartCode(String startCode) {
        this.startCode = startCode;
    }

    public String getStartName() {
        return startName;
    }

    public void setStartName(String startName) {
        this.startName = startName;
    }

    public String getEndCode() {
        return endCode;
    }

    public void setEndCode(String endCode) {
        this.endCode = endCode;
    }

    public String getEndName() {
        return endName;
    }

    public void setEndName(String endName) {
        this.endName = endName;
    }
}
