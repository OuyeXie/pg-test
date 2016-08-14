package com.ouyexie.pg.database;

import com.ouyexie.pg.exception.BusinessException;
import com.ouyexie.pg.log4j.MyLogger;
import com.ouyexie.pg.log4j.MyLoggerFactory;
import com.ouyexie.pg.utils.Constant;
import com.ouyexie.pg.utils.GlobalConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.Map;

/**
 * @author Ouye Xie
 */
public class Redis {
    private static final MyLogger LOG = MyLoggerFactory.getLogger(Redis.class);

    private static Redis m_instance;

    private JedisPool m_jedisPool;

    private Redis(Map<String, String> configMap) throws BusinessException {
        String host = GlobalConfig.DEBUGMODE ? configMap.get(Constant.Config.PARAM_REDIS_HOST_TEST) : configMap.get(Constant.Config.PARAM_REDIS_HOST);
        String portString = GlobalConfig.DEBUGMODE ? configMap.get(Constant.Config.PARAM_REDIS_PORT_TEST) : configMap.get(Constant.Config.PARAM_REDIS_PORT);
        int port = Integer.parseInt(portString);
        String password = GlobalConfig.DEBUGMODE ? configMap.get(Constant.Config.PARAM_REDIS_PASSWORD_TEST) : null;
        JedisPoolConfig pooConfig = new JedisPoolConfig();
        pooConfig.setMaxTotal(Constant.Setting.REDIS_MAX_TOTAL);
        pooConfig.setMaxIdle(Constant.Setting.REDIS_MAX_IDLE);
        pooConfig.setMaxWaitMillis(Constant.Setting.REDIS_MAX_WAIT_MILLIS);
        if (password != null) {
            m_jedisPool = new JedisPool(pooConfig, host, port, Constant.Setting.REDIS_TIMEOUT, password);
        } else {
            m_jedisPool = new JedisPool(pooConfig, host, port);
        }
        LOG.info(String.format("jedisPool config, MaxTotal: [%s], MaxIdle: [%s], MaxWaitMillis: [%s]", pooConfig.getMaxTotal(), pooConfig.getMaxIdle(), pooConfig.getMaxWaitMillis()));
        LOG.info(getPoolInfo());
    }

    public static Redis getInstance() {
        if (m_instance == null) {
            throw new RuntimeException();
        }
        return Redis.m_instance;
    }

    public static Redis getInstance(Map<String, String> configMap) throws BusinessException {
        if (m_instance == null) {
            synchronized (Redis.class) {
                if (m_instance == null) {
                    m_instance = new Redis(configMap);
                }
            }
        }
        return Redis.m_instance;
    }

    public String get(String key) {
        LOG.debug(getPoolInfo());
        try (Jedis jedis = m_jedisPool.getResource()){
            return jedis.get(key);
        } catch (JedisConnectionException e) {
            LOG.error("Could not get a resource from the pool! " + getPoolInfo(), e);
        }
        return null;
    }

    public String getPoolInfo(){
        return String.format("jedisPool info, NumActive: [%s], NumIdle: [%s], NumWaiters: [%s], MaxBorrowWaitTimeMillis: [%s], MeanBorrowWaitTimeMillis: [%s]",m_jedisPool.getNumActive(), m_jedisPool.getNumIdle(), m_jedisPool.getNumWaiters(), m_jedisPool.getMaxBorrowWaitTimeMillis(), m_jedisPool.getMeanBorrowWaitTimeMillis());
    }

    public void close() {
        try {
            m_jedisPool.destroy();
            m_instance = null;
        } catch (Exception e) {
            LOG.error(e);
        }
    }
}
