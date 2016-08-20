package com.ouyexie.pg.utils;

import com.ouyexie.pg.log4j.MyLogger;
import com.ouyexie.pg.log4j.MyLoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class StringUtil {
    private static final MyLogger LOG = MyLoggerFactory.getLogger(StringUtil.class);

    public static String formatLogging(Object... list) {
        return String.format(Constant.Const.LOGGING_TEMPLATE, list);
    }

}
