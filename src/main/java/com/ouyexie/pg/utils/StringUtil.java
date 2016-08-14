package com.ouyexie.pg.utils;

import com.ouyexie.pg.data.Pair;
import com.ouyexie.pg.log4j.MyLogger;
import com.ouyexie.pg.log4j.MyLoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class StringUtil {
    public static final String NEWLINE = System.getProperty("line.separator");
    private static final MyLogger LOG = MyLoggerFactory.getLogger(StringUtil.class);
    private static List<String> stopWordSet = new LinkedList<>();

    public static String formatLogging(Object... list) {
        return String.format(Constant.Const.LOGGING_TEMPLATE, list);
    }

    public static String stop(String input) {
        String stopped = input;
        for (String stopword : stopWordSet) {
            stopped = stopped.replace(stopword, "");
//            LOG.debug(stopword);
//            LOG.debug(stopped);
        }
        return stopped;
    }

    public static String clean(String input) {
        if (input == null) {
            return null;
        }
        String cleaned = input.replaceAll("[\\(\\)\\（\\）]", "");
        return cleaned;
    }

    public static String unify(String input) {
        if (input == null) {
            return null;
        }
        String r = input.replace("（", "(");
        r = r.replace("）", ")");
        return r;
    }

    public static byte[] hostToAddress(String host) {
        System.out.println(host);
        String[] parts = host.split("\\.");
        System.out.println(parts.length);
        byte[] address = new byte[4];
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            System.out.println(part);
            address[i] = (byte) (int) (Integer.parseInt(part));
        }
        return address;
    }

    public static <T> String arrayToString(T[] input) {
        String r = "";
        int i = 0;
        int len = input.length;
        for (T s : input) {
            r += s;
            if (i < len - 1) {
                r += ", ";
                i++;
            }
        }
        return r;
    }

    public static <T> String arrayToString(T[] input, String quoteFront, String quoteBack) {
        String r = "";
        int i = 0;
        int len = input.length;
        for (T s : input) {
            r += (quoteFront + s + quoteBack);
            if (i < len - 1) {
                r += ", ";
                i++;
            }
        }
        return r;
    }

    public static <T> String listToString(List<T> input) {
        String r = "";
        int i = 0;
        int len = input.size();
        for (T s : input) {
            r += s;
            if (i < len - 1) {
                r += ", ";
                i++;
            }
        }
        return r;
    }

    public static <T> String setToString(Set<T> input, String quoteFront, String quoteBack) {
        String r = "";
        int i = 0;
        int len = input.size();
        for (T s : input) {
            r += (quoteFront + s + quoteBack);
            if (i < len - 1) {
                r += ", ";
                i++;
            }
        }
        return r;
    }

    public static <T> String getClusterInfo(T cluster) {
        String info = null;
        if (cluster instanceof String) {
            info = (String) cluster;
        } else if (cluster instanceof Integer) {
            info = String.valueOf(((Integer) cluster));
        } else if (cluster instanceof Pair) {
            Pair coor = (Pair) cluster;
            info = String.format("(%s, %s)", coor.first, coor.second);
        } else {
            info = cluster.toString();
        }
        return info;
    }

    public static Pair<Number, String> nice(double num) {
        if (Double.isNaN(num)) {
            return new Pair(num, "");
        }
        double abs = Math.abs(num);
        long prefix;
        String suffix = "";
        if (abs < 10000) {
            prefix = Math.round(num * 100.0d) / 100;
        } else if (abs < 100000000) {
            prefix = Math.round(num / 100.0d) / 100;
            suffix = "万";
        } else {
            prefix = Math.round(num / 1000000.0d) / 100;
            suffix = "亿";
        }
        return new Pair(prefix, suffix);
    }
}
