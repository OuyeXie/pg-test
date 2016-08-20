package com.ouyexie.pg.restService;

import com.ouyexie.pg.database.Postgresql;
import com.ouyexie.pg.log4j.MyLogger;
import com.ouyexie.pg.log4j.MyLoggerFactory;
import com.ouyexie.pg.utils.ConfigUtil;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by ouyexie on 8/14/16.
 */
@ApplicationPath("/")
public class HttpRestService extends Application {
    private final Set<Class<?>> classes;
    private static final MyLogger LOG = MyLoggerFactory.getLogger(HttpRestService.class);
    private static Map<String, String> configMap = ConfigUtil.loadConfig();

    public HttpRestService() {

        LOG.info("http rest server starts");
        /*
         * initialize pg
         */
        //TODO: add hook to close pg safely before exiting the programme
        Postgresql postgresql = null;
        try {
            postgresql = Postgresql.getInstance(configMap);
            LOG.info("Initialized postgresql");
        } catch (Exception e) {
            LOG.error(e);
            System.exit(-1);
        }

        HashSet<Class<?>> c = new HashSet<Class<?>>();
        c.add(PgService.class);
        classes = Collections.unmodifiableSet(c);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }
}