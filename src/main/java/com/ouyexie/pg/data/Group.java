package com.ouyexie.pg.data;


import com.ouyexie.pg.log4j.MyLogger;
import com.ouyexie.pg.log4j.MyLoggerFactory;
import com.ouyexie.pg.utils.Constant;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Ouye Xie
 */
public class Group {
    private static final MyLogger LOG = MyLoggerFactory.getLogger(Group.class);

    private double issprice;
    private double actplaqty;
    private double currprice;
    private double value;
    private String publishdate;
    private String code;
    private String name;
    private String sname;
    private String symbol;
    private String banner;
    private Set<InvestmentInGroup> investments;
    private List shareholderRs;

    public Group(String code, String name, String sname, String symbol, double currprice, InvestmentInGroup investment, List shareholderRs) {
        this.code = code;
        this.name = name;
        this.sname = sname;
        this.symbol = symbol;
        this.currprice = currprice;
        investments = new HashSet<>();
        if (investment != null) {
            investments.add(investment);
        }
        this.shareholderRs = shareholderRs;
    }

    public double getIssprice() {
        return issprice;
    }

    public double getCurrprice() {
        return currprice;
    }

    public double getValue() {
        return value;
    }

    public String getPublishdate() {
        return publishdate;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public double getActplaqty() {
        return actplaqty;
    }

    public String getSname() {
        return sname;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getBanner() {
        return banner;
    }

    public List getShareholderRs() {
        return shareholderRs;
    }

    public Set<InvestmentInGroup> getInvestments() {
        return investments;
    }

    public void insertInvestment(InvestmentInGroup investment) {
        investments.add(investment);
    }

    public JSONObject getGroup() {
        double actplaqty = 0.0d;
        double isspriceN = 0.0d;
        double isspriceD = 0.0d;
        double value = 0.0d;
        String publishdate = "19700101";
        double totalHolderRto = 0.0d;
        int rank = 11;
        for (InvestmentInGroup investmentInGroup : this.investments) {
            actplaqty += investmentInGroup.getActplaqty();
            if (investmentInGroup.getIssprice() * investmentInGroup.getActplaqty() != 0.0d) {
                isspriceN += investmentInGroup.getIssprice() * investmentInGroup.getActplaqty();
                isspriceD += investmentInGroup.getActplaqty();
                value += investmentInGroup.getCurrprice() * investmentInGroup.getActplaqty();
            }
            if (investmentInGroup.getPublishdate() != null && investmentInGroup.getPublishdate().compareTo(publishdate) > 0) {
                publishdate = investmentInGroup.getPublishdate();
            }
            if (this.shareholderRs != null) {
                Iterator<Map> rsIterator = this.shareholderRs.iterator();
                while (rsIterator.hasNext()) {
                    Map map = rsIterator.next();
                    String shholdercode = (String) map.get(Constant.Field.MYSQL_SHHOLDERCODE);
                    String shholdername = (String) map.get(Constant.Field.MYSQL_SHHOLDERNAME);
                    double holderrto = ((BigDecimal) map.get(Constant.Field.MYSQL_HOLDERRTO)).doubleValue();
                    if ((shholdercode != null && !shholdercode.isEmpty() && investmentInGroup.getStartCode() != null && !investmentInGroup.getStartCode().isEmpty() && shholdercode.equals(investmentInGroup.getStartCode()) || (shholdername != null && !shholdername.isEmpty() && investmentInGroup.getStartName() != null && !investmentInGroup.getStartName().isEmpty() && shholdername.equals(investmentInGroup.getStartName())))) {
                        totalHolderRto += holderrto;
                    }
                }
            }
        }

        if (this.shareholderRs != null) {
            Iterator<Map> rsIterator1 = this.shareholderRs.iterator();
            while (rsIterator1.hasNext()) {
                Map map = rsIterator1.next();
                int currRank = ((BigDecimal) map.get(Constant.Field.MYSQL_RANK)).intValue();
                double holderrto = ((BigDecimal) map.get(Constant.Field.MYSQL_HOLDERRTO)).doubleValue();
                if (totalHolderRto >= +holderrto) {
                    rank = currRank;
                    break;
                }
            }
        }
        SimpleShareholderBanner simpleShareholderBanner = null;
        if (rank <= Constant.Setting.GRAPH_SIMPLE_SHAREHOLDER_BANNER_THRESHOLD) {
            String name = sname != null && !sname.isEmpty() ? this.sname : this.name;
            simpleShareholderBanner = SimpleShareholderBanner.create(totalHolderRto, name, rank);
        }

        this.issprice = isspriceD != 0.0d ? isspriceN / isspriceD : 0.0d;
        this.actplaqty = actplaqty;
        this.value = value;
        this.publishdate = publishdate.equals("19700101") ? "" : publishdate;

        if (this.currprice == 0.0d) {
            this.currprice = issprice;
        }

        JSONObject object = new JSONObject();
        object.put(Constant.Field.NEO4J_CODE, this.code);
        object.put(Constant.Field.NEO4J_NAME, this.name);
        object.put(Constant.Field.NEO4J_SNAME, this.sname);
        object.put(Constant.Field.NEO4J_SYMBOL, this.symbol);
        object.put(Constant.Field.NEO4J_ISSPRICE, this.issprice);
        object.put(Constant.Field.NEO4J_ACTPLAQTY, this.actplaqty);
        object.put(Constant.Field.NEO4J_CURRPRICE, this.currprice);
        object.put(Constant.Field.NEO4J_VALUE, this.value);
        object.put(Constant.Field.NEO4J_PUBLISHDATE, this.publishdate);

        if (simpleShareholderBanner != null) {
            JSONObject banner = new JSONObject();
            banner.put(Constant.Field.NEO4J_NUMBER, simpleShareholderBanner.getNiceValue());
            banner.put(Constant.Field.NEO4J_SUFFIX, simpleShareholderBanner.getSuffix());
            banner.put(Constant.Field.NEO4J_CONTENT, simpleShareholderBanner.getContent());
            object.put(Constant.Field.NEO4J_BANNER, banner);
        }

        return object;
    }
}
