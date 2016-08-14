package com.ouyexie.pg.data;


import java.io.Serializable;
import java.util.Objects;

/**
 * @author Ouye Xie
 */
public class Trade implements Serializable {

    private String id;
    private String tdate;
    private String secode;
    private String symbol;
    private String compcode;
    private String compname;
    private double tradeprice;
    private double tradevol;
    private String beracccode;
    private String beraccname;
    private String seracccode;
    private String seraccname;
    private String berbrocode;
    private String berbroname;
    private String serbrocode;
    private String serbroname;


    public Trade(String id, String tdate, String secode, String symbol, String compcode, String compname, double tradeprice, double tradevol, String beracccode, String beraccname, String seracccode, String seraccname, String berbrocode, String berbroname, String serbrocode, String serbroname) {
        this.id = id;
        this.tdate = tdate;
        this.secode = secode;
        this.symbol = symbol;
        this.compcode = compcode;
        this.compname = compname;
        this.tradeprice = tradeprice;
        this.tradevol = tradevol;
        this.beracccode = beracccode;
        this.beraccname = beraccname;
        this.seracccode = seracccode;
        this.seraccname = seraccname;
        this.berbrocode = berbrocode;
        this.berbroname = berbroname;
        this.serbrocode = serbrocode;
        this.serbroname = serbroname;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Trade)) {
            return false;
        }
        Trade p = (Trade) o;
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

    public String getTdate() {
        return tdate;
    }

    public void setTdate(String tdate) {
        this.tdate = tdate;
    }

    public String getSecode() {
        return secode;
    }

    public void setSecode(String secode) {
        this.secode = secode;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCompcode() {
        return compcode;
    }

    public void setCompcode(String compcode) {
        this.compcode = compcode;
    }

    public String getCompname() {
        return compname;
    }

    public void setCompname(String compname) {
        this.compname = compname;
    }

    public double getTradeprice() {
        return tradeprice;
    }

    public void setTradeprice(double tradeprice) {
        this.tradeprice = tradeprice;
    }

    public double getTradevol() {
        return tradevol;
    }

    public void setTradevol(double tradevol) {
        this.tradevol = tradevol;
    }

    public String getBeracccode() {
        return beracccode;
    }

    public void setBeracccode(String beracccode) {
        this.beracccode = beracccode;
    }

    public String getBeraccname() {
        return beraccname;
    }

    public void setBeraccname(String beraccname) {
        this.beraccname = beraccname;
    }

    public String getSeracccode() {
        return seracccode;
    }

    public void setSeracccode(String seracccode) {
        this.seracccode = seracccode;
    }

    public String getSeraccname() {
        return seraccname;
    }

    public void setSeraccname(String seraccname) {
        this.seraccname = seraccname;
    }

    public String getBerbrocode() {
        return berbrocode;
    }

    public void setBerbrocode(String berbrocode) {
        this.berbrocode = berbrocode;
    }

    public String getBerbroname() {
        return berbroname;
    }

    public void setBerbroname(String berbroname) {
        this.berbroname = berbroname;
    }

    public String getSerbrocode() {
        return serbrocode;
    }

    public void setSerbrocode(String serbrocode) {
        this.serbrocode = serbrocode;
    }

    public String getSerbroname() {
        return serbroname;
    }

    public void setSerbroname(String serbroname) {
        this.serbroname = serbroname;
    }
}
