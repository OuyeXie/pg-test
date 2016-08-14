package com.ouyexie.pg.log4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ouye Xie
 */
public class MyLoggerFactory {
    private static Map<String, MyLogger> loggerMap = new HashMap<>();

    @SuppressWarnings("rawtypes")
    public static MyLogger getLogger(Class clazz) {
        return getLogger(clazz.getName());
    }

    public static MyLogger getLogger(String name) {
        // noinspection SynchronizeOnNonFinalField
        synchronized (loggerMap) {
            if (!loggerMap.containsKey(name)) {
                loggerMap.put(name, new MyLogger(name));
            }
            return loggerMap.get(name);
        }
    }
}
