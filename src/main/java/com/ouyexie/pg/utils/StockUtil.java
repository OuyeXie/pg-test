package com.ouyexie.pg.utils;

import com.ouyexie.pg.database.Mysql;
import com.ouyexie.pg.database.Redis;
import com.ouyexie.pg.log4j.MyLogger;
import com.ouyexie.pg.log4j.MyLoggerFactory;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by ouyexie on 7/5/16.
 */
public class StockUtil {
    private static final MyLogger LOG = MyLoggerFactory.getLogger(StockUtil.class);

    private static Map<String, Double> compcodeToQuote = new Hashtable<>();
    private static Map<String, Map<String, String>> compcodeToInfo = new Hashtable<>();
    private static Map<String, List> compcodeToShareholder = new Hashtable<>();
    private static Map<String, String> compnameToCompcode = new Hashtable<>();
    private static Map<String, String> compcodeToCompname = new Hashtable<>();

    public static String getSymbolByCompcode(String compcode) {
        if (compcode == null || compcode.isEmpty()) {
            return null;
        }
        String symbol = null;

        Connection conn = null;
        try {
            conn = Mysql.getInstance().getConnection();
            Statement st = conn.createStatement();
            if (conn == null) {
                LOG.error("cannot fetch a connection from CP");
                return null;
            }
            // change size to 0 if you want to stop using cursor
            st.setFetchSize(0);
            ResultSet rs;
            String mysqlQuery = String.format("SELECT SYMBOL FROM TQ_OA_STCODE WHERE COMPCODE=%s;", compcode);
            LOG.debug("mysqlQuery: " + mysqlQuery);
            rs = st.executeQuery(mysqlQuery);
            if (rs.next()) {
                symbol = rs.getString(Constant.Field.MYSQL_SYMBOL);
            }
        } catch (SQLException e) {
            LOG.error(e);
            return null;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOG.error(e);
                }
            }
        }
        return symbol;
    }

    // TODO: adjust price and total quantity
    public static double getQuoteBySymbol(String symbol) {
        if (symbol == null || symbol.isEmpty()) {
            return 0.0d;
        }
        double currprice = 0.0d;

        String key = String.format("stock:quotes:%s", symbol);

        LOG.debug(String.format("redisKey: [%s]", key));
        String quoteString = null;

        quoteString = Redis.getInstance().get(key);

        if (quoteString == null) {
            LOG.debug(String.format("record cannot be found in redis, key: [%s]", key));
            return 0.0d;
        }
        LOG.debug(String.format("quote: [%s]", quoteString));
        JSONObject quote = null;
        try {
            quote = new JSONObject(quoteString);
        } catch (JSONException e) {
            LOG.error(e);
            return 0.0d;
        }
        if (quote.has("lastPrice")) {
            currprice = quote.getDouble("lastPrice");
        } else if (quote.has("lastClose")) {
            currprice = quote.getDouble("lastClose");
        } else {
            LOG.debug(String.format("neither lastPrice nor lastClose field can be found in redis, key: [%s]", key));
        }

        return currprice;
    }

    public static double getClosePriceBySymbol(String symbol) {
        if (symbol == null || symbol.isEmpty()) {
            return 0.0d;
        }
        double closePrice = 0.0d;

        Connection conn = null;
        try {
            conn = Mysql.getInstance().getConnection();
            Statement st = conn.createStatement();
            if (conn == null) {
                LOG.error("cannot fetch a connection from CP");
                return 0.0d;
            }
            // change size to 0 if you want to stop using cursor
            st.setFetchSize(0);
            ResultSet rs;

            String mysqlQuery = String.format("SELECT TCLOSE FROM TQ_THSK_ADJUSTQT WHERE SYMBOL='%s' ORDER BY ID DESC;", symbol);

            LOG.debug("mysqlQuery: " + mysqlQuery);
            rs = st.executeQuery(mysqlQuery);
            if (rs.next()) {
                closePrice = rs.getDouble(Constant.Field.MYSQL_TCLOSE);
            }
        } catch (SQLException e) {
            LOG.error(e);
            return 0.0d;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOG.error(e);
                }
            }
        }
        return closePrice;
    }

    public static double getQuoteByCompcode(String compcode) {
        if (compcode == null || compcode.isEmpty()) {
            return 0.0d;
        }
        if (compcodeToQuote.containsKey(compcode)) {
            return compcodeToQuote.get(compcode);
        }
        String symbol = getSymbolByCompcode(compcode);
        double quote = getQuoteBySymbol(symbol);
        if (quote == 0.0d) {
            quote = getClosePriceBySymbol(symbol);
        }
        compcodeToQuote.put(compcode, quote);
        return quote;
    }

    public static Map<String, String> getInfoByCompcode(String compcode) {
        if (compcode == null || compcode.isEmpty()) {
            return null;
        }
        if (compcodeToInfo.containsKey(compcode)) {
            return compcodeToInfo.get(compcode);
        }

        String symbol = null;
        String sw = null;
        String sname = null;
        String legrep = null;

        Connection conn = null;
        try {
            conn = Mysql.getInstance().getConnection();
            Statement st = conn.createStatement();
            if (conn == null) {
                LOG.error("cannot fetch a connection from CP");
                return null;
            }
            // change size to 0 if you want to stop using cursor
            st.setFetchSize(0);
            ResultSet rs;
            String mysqlQuery = String.format("SELECT SYMBOL, SESNAME, SWLEVEL2NAME FROM TQ_SK_BASICINFO WHERE COMPCODE='%s';", compcode);
            LOG.debug("mysqlQuery: " + mysqlQuery);
            rs = st.executeQuery(mysqlQuery);
            if (rs.next()) {
                symbol = rs.getString(Constant.Field.MYSQL_SYMBOL);
                sw = rs.getString(Constant.Field.MYSQL_SWLEVEL2NAME);
                sname = rs.getString(Constant.Field.MYSQL_SESNAME);
            }

            mysqlQuery = String.format("SELECT COMPSNAME, LEGREP FROM TQ_COMP_INFO WHERE COMPCODE='%s';", compcode);
            LOG.debug("mysqlQuery: " + mysqlQuery);
            rs = st.executeQuery(mysqlQuery);
            if (rs.next()) {
                if (sname == null || sname.isEmpty()) {
                    sname = rs.getString(Constant.Field.MYSQL_COMPSNAME);
                }
                legrep = rs.getString(Constant.Field.MYSQL_LEGREP);
            }
        } catch (SQLException e) {
            LOG.error(e);
            return null;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOG.error(e);
                }
            }
        }
        Map<String, String> map = new HashMap();
        if (symbol != null) {
            map.put(Constant.Field.MYSQL_SYMBOL, symbol);
        }
        if (sw != null) {
            map.put(Constant.Field.MYSQL_SWLEVEL2NAME, sw);
        }
        if (sname != null) {
            map.put(Constant.Field.MYSQL_SESNAME, sname);
        }
        if (legrep != null) {
            map.put(Constant.Field.MYSQL_LEGREP, legrep);
        }

        compcodeToInfo.put(compcode, map);

        return map;
    }

    public static List getShareholderByCompcode(String compcode) {
        if (compcode == null || compcode.isEmpty()) {
            return null;
        }

        if (compcodeToShareholder.containsKey(compcode)) {
            return compcodeToShareholder.get(compcode);
        }

        Connection conn = null;
        try {
            conn = Mysql.getInstance().getConnection();
            Statement st = conn.createStatement();
            if (conn == null) {
                LOG.error("cannot fetch a connection from CP");
                return null;
            }
            // change size to 0 if you want to stop using cursor
            st.setFetchSize(0);
            ResultSet rs;
            String mysqlQuery = String.format("SELECT SHHOLDERCODE, SHHOLDERNAME, RANK, HOLDERRTO, HOLDERAMT FROM TQ_SK_SHAREHOLDER WHERE COMPCODE=%s ORDER BY PUBLISHDATE DESC, RANK ASC LIMIT 10;", compcode);
            LOG.debug("mysqlQuery: " + mysqlQuery);
            rs = st.executeQuery(mysqlQuery);
            List list = CollectionUtil.resultSetToArrayList(rs);
            if (list == null) {
                return null;
            }
            compcodeToShareholder.put(compcode, list);
            return list;
        } catch (SQLException e) {
            LOG.error(e);
            return null;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOG.error(e);
                }
            }
        }
    }

    public synchronized static void prepareCompcodeCompname() {
        if (compnameToCompcode.isEmpty() || compcodeToCompname.isEmpty()) {
            Connection conn = null;
            try {
                conn = Mysql.getInstance().getConnection();
                Statement st = conn.createStatement();
                if (conn == null) {
                    LOG.error("cannot fetch a connection from CP");
                }
                // change size to 0 if you want to stop using cursor
                st.setFetchSize(Constant.Setting.DB_FETCH_SIZE);
                ResultSet rs;
                String mysqlQuery = "SELECT COMPCODE, COMPNAME FROM TQ_COMP_INFO ORDER BY ID DESC;";
                LOG.info("mysqlQuery: " + mysqlQuery);
                rs = st.executeQuery(mysqlQuery);
                while (rs.next()) {
                    String currCompcode = rs.getString(Constant.Field.MYSQL_COMPCODE);
                    String currCompname = rs.getString(Constant.Field.MYSQL_COMPNAME);
                    currCompname = StringUtil.unify(currCompname);
                    if (currCompcode != null && !currCompcode.isEmpty() && currCompname != null && !currCompname.isEmpty()) {
                        if (compnameToCompcode.containsKey(currCompname)){
                            LOG.warn(String.format("in to_comp_info, a name [%s] has multiple codes [%s, %s]", currCompname, compnameToCompcode.get(currCompname), currCompcode));
                        } else {
                            compnameToCompcode.put(currCompname, currCompcode);
                        }
                        if (compcodeToCompname.containsKey(currCompcode)){
                            LOG.warn(String.format("in to_comp_info, a code [%s] has multiple names [%s, %s]", currCompcode, compcodeToCompname.get(currCompcode), currCompname));
                        } else {
                            compcodeToCompname.put(currCompcode, currCompname);
                        }
                    }
                }
                LOG.info("number of name to code pairs is: " + compnameToCompcode.size());
                LOG.info("number of code to name pairs is: " + compcodeToCompname.size());
            } catch (SQLException e) {
                LOG.error(e);
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        LOG.error(e);
                    }
                }
            }
        }
    }

    public static String getCompcodeByCompname(String compname) {
        compname = StringUtil.unify(compname);
        if (compnameToCompcode.isEmpty()) {
            prepareCompcodeCompname();
        }
        if (compname == null || compname.isEmpty()) {
            return null;
        }
        if (compnameToCompcode.containsKey(compname)) {
            return compnameToCompcode.get(compname);
        }
        return null;
    }

    public static String getCompnameByCompcode(String compcode) {
        if (compcodeToCompname.isEmpty()) {
            prepareCompcodeCompname();
        }
        if (compcode == null || compcode.isEmpty()) {
            return null;
        }
        if (compcodeToCompname.containsKey(compcode)) {
            return compcodeToCompname.get(compcode);
        }
        return null;
    }

    public static boolean compcodeExists(String compcode) {
        if (compcodeToCompname.isEmpty()) {
            prepareCompcodeCompname();
        }
        if (compcode == null || compcode.isEmpty()) {
            return false;
        }
        if (compcodeToCompname.containsKey(compcode)) {
            return true;
        }
        return false;
    }

    public static boolean compnameExists(String compname) {
        if (compcodeToCompname.isEmpty()) {
            prepareCompcodeCompname();
        }
        if (compname == null || compname.isEmpty()) {
            return false;
        }
        if (compnameToCompcode.containsKey(compname)) {
            return true;
        }
        return false;
    }

    public static String getCompcode(String compname) {
        if (compname == null || compname.isEmpty()) {
            return null;
        }
        String compcode = getCompcodeByCompname(compname);
        if (compcode == null ) {
            compcode = compname;
        }
        return compcode;
    }
}
