package com.ouyexie.pg.database;

import com.ouyexie.pg.exception.BusinessException;
import com.ouyexie.pg.log4j.MyLogger;
import com.ouyexie.pg.log4j.MyLoggerFactory;
import com.ouyexie.pg.utils.Constant;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ouye Xie
 */
public class Mysql {
    private static final MyLogger LOG = MyLoggerFactory.getLogger(Mysql.class);

    private static Mysql m_instance;

    private HikariDataSource m_ds;

    private String m_table_tq_oa_stcode;
    private String m_table_tq_thsk_proeqpmt;
    private String m_table_tq_sk_thirdissue;
    private String m_table_tq_comp_info;
    private String m_table_tq_comp_manager;
    private String m_table_tq_comp_skholderchg;
    private String m_table_tq_sk_otsholder;
    private String m_table_tq_sk_shareholder;
    private String m_table_tq_thsk_shareholder;
    private String m_table_tq_famp_basicinfo;
    private String m_table_tq_famp_party;
    private String m_table_tq_thsk_adjustqt;
    private String m_table_tq_thsk_ptinfo;
    private String m_table_tq_thsk_basicinfo;


    private Map<String, String> m_codeToExchange = new HashMap<>();

    private Mysql(Map<String, String> configMap) throws BusinessException {
        String host = configMap.get(Constant.Config.PARAM_MYSQL_HOST);
        String port = configMap.get(Constant.Config.PARAM_MYSQL_PORT);
        String database = configMap.get(Constant.Config.PARAM_MYSQL_DATABASE);
        String user = configMap.get(Constant.Config.PARAM_MYSQL_USER);
        String password = configMap.get(Constant.Config.PARAM_MYSQL_PASSWORD);
        String url = String.format("jdbc:mysql://%s:%s/%s", host, port, database);

        try {
            HikariConfig config = new HikariConfig();
//            config.setJdbcUrl(url);
            config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
            config.addDataSourceProperty("serverName", host);
            config.addDataSourceProperty("port", port);
            config.addDataSourceProperty("databaseName", database);
            config.addDataSourceProperty("user", user);
            config.addDataSourceProperty("password", password);
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            config.setMaximumPoolSize(Constant.Setting.DB_MAX_POOL_SIZE);

            m_ds = new HikariDataSource(config);
            m_ds.setAutoCommit(true);
        } catch (Exception e) {
            LOG.error(e);
        }

        m_table_tq_oa_stcode = configMap.get(Constant.Config.PARAM_MYSQL_TABLE_TQ_OA_STCODE);
        m_table_tq_comp_info = configMap.get(Constant.Config.PARAM_MYSQL_TABLE_TQ_COMP_INFO);
        m_table_tq_thsk_proeqpmt = configMap.get(Constant.Config.PARAM_MYSQL_TABLE_TQ_THSK_PROEQPMT);
        m_table_tq_sk_thirdissue = configMap.get(Constant.Config.PARAM_MYSQL_TABLE_TQ_SK_THIRDISSUE);

        m_table_tq_comp_manager = configMap.get(Constant.Config.PARAM_MYSQL_TABLE_TQ_COMP_MANAGER);
        m_table_tq_comp_skholderchg = configMap.get(Constant.Config.PARAM_MYSQL_TABLE_TQ_COMP_SKHOLDERCHG);
        m_table_tq_sk_otsholder = configMap.get(Constant.Config.PARAM_MYSQL_TABLE_TQ_SK_OTSHOLDER);
        m_table_tq_sk_shareholder = configMap.get(Constant.Config.PARAM_MYSQL_TABLE_TQ_SK_SHAREHOLDER);
        m_table_tq_thsk_shareholder = configMap.get(Constant.Config.PARAM_MYSQL_TABLE_TQ_THSK_SHAREHOLDER);

        m_table_tq_famp_basicinfo = configMap.get(Constant.Config.PARAM_MYSQL_TABLE_TQ_FAMP_BASICINFO);
        m_table_tq_famp_party = configMap.get(Constant.Config.PARAM_MYSQL_TABLE_TQ_FAMP_PARTY);

        m_table_tq_thsk_adjustqt = configMap.get(Constant.Config.PARAM_MYSQL_TABLE_TQ_THSK_ADJUSTQT);

        m_table_tq_thsk_ptinfo = configMap.get(Constant.Config.PARAM_MYSQL_TABLE_TQ_THSK_PTINFO);
        m_table_tq_thsk_basicinfo = configMap.get(Constant.Config.PARAM_MYSQL_TABLE_TQ_THSK_BASICINFO);
    }

    public static Mysql getInstance() {
        if (m_instance == null) {
            throw new RuntimeException();
        }
        return Mysql.m_instance;
    }

    public static Mysql getInstance(Map<String, String> configMap) throws BusinessException {
        if (m_instance == null) {
            synchronized (Postgresql.class) {
                if (m_instance == null) {
                    m_instance = new Mysql(configMap);
                }
            }
        }
        return Mysql.m_instance;
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = m_ds.getConnection();
        } catch (Exception e) {
            LOG.error(e);
        }
        return conn;
    }

    public String getTableTqThskProeqpmt() {
        return m_table_tq_thsk_proeqpmt;
    }

    public String getTableTqSkThridissue() {
        return m_table_tq_sk_thirdissue;
    }

    public String getTableTqCompInfo() {
        return m_table_tq_comp_info;
    }

    public String getTableTqOaStcode() {
        return m_table_tq_oa_stcode;
    }

    public String getTableTqCompManager() {
        return m_table_tq_comp_manager;
    }

    public String getTableTqCompSkholderchg() {
        return m_table_tq_comp_skholderchg;
    }

    public String getTableTqSkOtsholder() {
        return m_table_tq_sk_otsholder;
    }

    public String getTableTqSkShareholder() {
        return m_table_tq_sk_shareholder;
    }

    public String getTableTqThskShareholder() {
        return m_table_tq_thsk_shareholder;
    }

    public String getTableTqFampBasicinfo() {
        return m_table_tq_famp_basicinfo;
    }

    public String getTableTqFampParty() {
        return m_table_tq_famp_party;
    }

    public String getTableTqThskAdjustqt() {
        return m_table_tq_thsk_adjustqt;
    }

    public String getTableTqThskPtinfo() {
        return m_table_tq_thsk_ptinfo;
    }

    public String getTableTqThskBasicinfo() {
        return m_table_tq_thsk_basicinfo;
    }

    public void close() {
        try {
            m_ds.close();
            m_instance = null;
        } catch (Exception e) {
            LOG.error(e);
        }
    }
}
