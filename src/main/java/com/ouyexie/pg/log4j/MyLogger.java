package com.ouyexie.pg.log4j;

import com.ouyexie.pg.utils.StringUtil;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author Ouye Xie
 */
public class MyLogger {

    private static final String FQCN = MyLogger.class.getName();
    private Logger logger;

    protected MyLogger(String name) {
        logger = Logger.getLogger(name);
    }

    public void debug(Object message) {
        logger.log(FQCN, Level.DEBUG, StringUtil.formatLogging(message), null);
    }

    public void debug(Object message, Throwable t) {
        logger.log(FQCN, Level.DEBUG, StringUtil.formatLogging(message), t);
    }

    public void info(Object message) {
        logger.log(FQCN, Level.INFO, StringUtil.formatLogging(message), null);
    }

    public void info(Object message, Throwable t) {
        logger.log(FQCN, Level.INFO, StringUtil.formatLogging(message), t);
    }

    public void warn(Object message) {
        logger.log(FQCN, Level.WARN, StringUtil.formatLogging(message), null);
    }

    public void warn(Object message, Throwable t) {
        logger.log(FQCN, Level.WARN, StringUtil.formatLogging(message), t);
    }

    public void error(Object message) {
        logger.log(FQCN, Level.ERROR, StringUtil.formatLogging(message), null);
    }

    public void error(Object message, Throwable t) {
        logger.log(FQCN, Level.ERROR, StringUtil.formatLogging(message), t);
    }
}
