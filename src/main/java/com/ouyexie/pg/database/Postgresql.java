package com.ouyexie.pg.database;

import com.ouyexie.pg.exception.BusinessException;
import com.ouyexie.pg.log4j.MyLogger;
import com.ouyexie.pg.log4j.MyLoggerFactory;
import com.ouyexie.pg.utils.Constant;
import com.ouyexie.pg.utils.GlobalConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;

/**
 * @author Ouye Xie
 */
public class Postgresql {
    private static final MyLogger LOG = MyLoggerFactory.getLogger(Postgresql.class);

    private static Postgresql m_instance;

    private Connection m_conn;
    private Connection m_conn_transaction;

    private String m_table_investors;
    private String m_table_investors_relation;

    private Postgresql(Map<String, String> configMap) throws BusinessException {
        String host = GlobalConfig.DEBUGMODE ? configMap.get(Constant.Config.PARAM_POSTGRESQL_HOST_TEST) : configMap.get(Constant.Config.PARAM_POSTGRESQL_HOST);
        String port = GlobalConfig.DEBUGMODE ? configMap.get(Constant.Config.PARAM_POSTGRESQL_PORT_TEST) : configMap.get(Constant.Config.PARAM_POSTGRESQL_PORT);
        String database = configMap.get(Constant.Config.PARAM_POSTGRESQL_DATABASE);
        String user = configMap.get(Constant.Config.PARAM_POSTGRESQL_USER);
        String password = configMap.get(Constant.Config.PARAM_POSTGRESQL_PASSWORD);

        String url = String.format("jdbc:postgresql://%s:%s/%s", host, port, database);

        try {
            Class.forName("org.postgresql.Driver");
            m_conn = DriverManager.getConnection(url, user, password);
            // if autocommit is off, it means transaction is triggered
            m_conn.setAutoCommit(true);
            m_conn_transaction = DriverManager.getConnection(url, user, password);
            // if autocommit is off, it means transaction is triggered
            m_conn_transaction.setAutoCommit(false);
        } catch (Exception e) {
            LOG.error(e);
        }

        m_table_investors = configMap.get(Constant.Config.PARAM_POSTGRESQL_TABLE_INVESTORS);
        m_table_investors_relation = configMap.get(Constant.Config.PARAM_POSTGRESQL_TABLE_INVESTORS_RELATION);
    }

    public static Postgresql getInstance() {
        if (m_instance == null) {
            throw new RuntimeException();
        }
        return Postgresql.m_instance;
    }

    public static Postgresql getInstance(Map<String, String> configMap) throws BusinessException {
        if (m_instance == null) {
            synchronized (Postgresql.class) {
                if (m_instance == null) {
                    m_instance = new Postgresql(configMap);
                }
            }
        }
        return Postgresql.m_instance;
    }

    public Connection getConnection() {
        return m_conn;
    }

    public Connection getConnectionTransaction() {
        return m_conn_transaction;
    }

    public String getTableTnvestors() {
        return m_table_investors;
    }

    public String getTableTnvestorsRelation() {
        return m_table_investors_relation;
    }

    public void close() {
        try {
            m_conn.close();
            m_conn_transaction.close();
            m_instance = null;
        } catch (Exception e) {
            LOG.error(e);
        }
    }
}
