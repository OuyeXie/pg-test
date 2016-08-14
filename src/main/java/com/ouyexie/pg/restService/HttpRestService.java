package com.ouyexie.pg.restService;

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
//@ApplicationPath("/")
//public class HttpRestService extends ResourceConfig {
//    private static final MyLogger LOG = MyLoggerFactory
//            .getLogger(HttpRestService.class);
//
//    public HttpRestService() {
//        ConfigUtil.loadConfig();
//        Map<String, String> configMap = ConfigUtil.getConfig();
//
//        LOG.info("http rest server starts");
//
//        SLF4JBridgeHandler.removeHandlersForRootLogger();
//        SLF4JBridgeHandler.install();
//
//        this.setApplicationName(Const.HTTP_SERVICE_NAME);
//
//        registerClasses(PgService.class);
//
//        /*
//         * initialize
//         */
////        Postgresql postgresql = null;
////        try {
////            postgresql = Postgresql.getInstance(configMap);
////            LOG.info("Initialized postgresql");
////        } catch (Exception e) {
////            LOG.error(e);
////            System.exit(-1);
////        }
//    }
//}

@ApplicationPath("/")
public class HttpRestService extends Application {
    private final Set<Class<?>> classes;
    private static final MyLogger LOG = MyLoggerFactory.getLogger(HttpRestService.class);

    public HttpRestService() {
        ConfigUtil.loadConfig();
        Map<String, String> configMap = ConfigUtil.getConfig();

        LOG.info("http rest server starts");

        HashSet<Class<?>> c = new HashSet<Class<?>>();
        c.add(PgService.class);
        classes = Collections.unmodifiableSet(c);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }
}