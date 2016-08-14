package com.ouyexie.pg.utils;

import com.ouyexie.pg.log4j.MyLogger;
import com.ouyexie.pg.log4j.MyLoggerFactory;

public class MathUtil {
    private static final MyLogger LOG = MyLoggerFactory.getLogger(MathUtil.class);

    public static double sigmoid(double input) {
        return (1.0d / (1.0d + Math.exp(-input)));
    }

    public static double devide(double n, double d) {
        return d == 0.0d ? 0.0d : n / d;
    }
}
