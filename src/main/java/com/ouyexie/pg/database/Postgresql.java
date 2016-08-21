package com.ouyexie.pg.database;

import com.ouyexie.pg.exception.BusinessException;
import com.ouyexie.pg.log4j.MyLogger;
import com.ouyexie.pg.log4j.MyLoggerFactory;
import com.ouyexie.pg.utils.Constant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;

/**
 * @author Ouye Xie
 */
public class Postgresql {
    private static final MyLogger LOG = MyLoggerFactory.getLogger(Postgresql.class);

    private static Postgresql m_instance;

    private String url;
    private String user;
    private String password;

    private Postgresql(Map<String, String> configMap) throws BusinessException {
        String host = configMap.get(Constant.Config.PARAM_POSTGRESQL_HOST);
        String port = configMap.get(Constant.Config.PARAM_POSTGRESQL_PORT);
        String database = configMap.get(Constant.Config.PARAM_POSTGRESQL_DATABASE);
        String user = configMap.get(Constant.Config.PARAM_POSTGRESQL_USER);
        String password = configMap.get(Constant.Config.PARAM_POSTGRESQL_PASSWORD);

        String url = String.format("jdbc:postgresql://%s:%s/%s", host, port, database);

        this.url = url;
        this.user = user;
        this.password = password;
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
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);
            // if autocommit is off, it means transaction is triggered
            conn.setAutoCommit(true);
        } catch (Exception e) {
            LOG.error(e);
        }
        return conn;
    }

    public Connection getConnectionTransaction() {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);
            // if autocommit is off, it means transaction is triggered
            conn.setAutoCommit(false);
        } catch (Exception e) {
            LOG.error(e);
        }
        return conn;
    }
}
