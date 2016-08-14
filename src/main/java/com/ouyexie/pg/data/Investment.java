package com.ouyexie.pg.data;


import java.io.Serializable;
import java.util.Objects;

/**
 * @author Ouye Xie
 */
public class Investment implements Serializable {
    private String id;
    private String issueid;
    private Entity investor;
    private Entity investee;
    private double rpayamt;
    private double actplaqty;
    private double totqtyaft;
    private double issprice;
    private String publishdate;
    private int publishint;
    private String plaobjtype;
    private String shholdertype;
    private String plaobjsecodetype;
    private double plantotraiseamt;
    private double acttotraiseamt;

    public Investment(String id, Entity investor, Entity investee, double actplaqty, String shholdertype) {
        this.id = id;
        this.investor = investor;
        this.investee = investee;
        this.actplaqty = actplaqty;
        this.shholdertype = shholdertype;
    }

    public Investment(String id, String issueid, Entity investor, Entity investee, double rpayamt, double actplaqty, double totqtyaft, double issprice, String publishdate, String plaobjtype, String plaobjsecodetype, double plantotraiseamt, double acttotraiseamt) {
        this.id = id;
        this.issueid = issueid;
        this.investor = investor;
        this.investee = investee;
        this.rpayamt = rpayamt;
        this.actplaqty = actplaqty;
        this.totqtyaft = totqtyaft;
        this.issprice = issprice;
        this.publishdate = publishdate;
        try {
            this.publishint = Integer.parseInt(publishdate);
        } catch (NumberFormatException e) {
            this.publishint = -1;
        }
        this.plaobjtype = plaobjtype;
        this.plaobjsecodetype = plaobjsecodetype;
        this.plantotraiseamt = plantotraiseamt;
        this.acttotraiseamt = acttotraiseamt;
    }

    /*
     * Convenience method for creating an appropriately typed pair.
     */
    public static Investment create(String id, String issueid, Entity investor, Entity investee, double rpayamt, double actplaqty, double totqtyaft, double issprice, String publishdate, String plaobjtype, String plaobjsecodetype, double plantotraiseamt, double acttotraiseamt) {
        return new Investment(id, issueid, investor, investee, rpayamt, actplaqty, totqtyaft, issprice, publishdate, plaobjtype, plaobjsecodetype, plantotraiseamt, acttotraiseamt);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Investment)) {
            return false;
        }
        Investment p = (Investment) o;
        return Objects.equals(p.id, id) && Objects.equals(p.investor, investor) && Objects.equals(p.investee, investee);
    }

    @Override
    public int hashCode() {
        return 41 ^ 3 * (id == null ? 0 : id.hashCode()) + 41 ^ 2
                * (investor == null ? 0 : investor.hashCode()) + 41
                * (investee == null ? 0 : investee.hashCode());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIssueid() {
        return issueid;
    }

    public void setIssueid(String issueid) {
        this.issueid = issueid;
    }

    public Entity getInvestor() {
        return investor;
    }

    public void setInvestor(Entity investor) {
        this.investor = investor;
    }

    public Entity getInvestee() {
        return investee;
    }

    public void setInvestee(Entity investee) {
        this.investee = investee;
    }

    public double getRpayamt() {
        return rpayamt;
    }

    public void setRpayamt(double rpayamt) {
        this.rpayamt = rpayamt;
    }

    public double getActplaqty() {
        return actplaqty;
    }

    public void setActplaqty(double actplaqty) {
        this.actplaqty = actplaqty;
    }

    public double getTotqtyaft() {
        return totqtyaft;
    }

    public void setTotqtyaft(double totqtyaft) {
        this.totqtyaft = totqtyaft;
    }

    public double getIssprice() {
        return issprice;
    }

    public void setIssprice(double issprice) {
        this.issprice = issprice;
    }

    public String getPublishdate() {
        return publishdate;
    }

    public void setPublishdate(String publishdate) {
        this.publishdate = publishdate;
    }

    public int getPublishint() {
        return publishint;
    }

    public void setPublishint(int publishint) {
        this.publishint = publishint;
    }

    public String getPlaobjtype() {
        return plaobjtype;
    }

    public void setPlaobjtype(String plaobjtype) {
        this.plaobjtype = plaobjtype;
    }

    public String getPlaobjsecodetype() {
        return plaobjsecodetype;
    }

    public void setPlaobjsecodetype(String plaobjsecodetype) {
        this.plaobjsecodetype = plaobjsecodetype;
    }

    public String getShholdertype() {
        return shholdertype;
    }

    public void setShholdertype(String shholdertype) {
        this.shholdertype = shholdertype;
    }

    public double getPlantotraiseamt() {
        return plantotraiseamt;
    }

    public void setPlantotraiseamt(double plantotraiseamt) {
        this.plantotraiseamt = plantotraiseamt;
    }

    public double getActtotraiseamt() {
        return acttotraiseamt;
    }

    public void setActtotraiseamt(double acttotraiseamt) {
        this.acttotraiseamt = acttotraiseamt;
    }
}
