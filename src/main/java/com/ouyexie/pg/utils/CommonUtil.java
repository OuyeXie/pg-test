package com.ouyexie.pg.utils;

import com.ouyexie.pg.log4j.MyLogger;
import com.ouyexie.pg.log4j.MyLoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CommonUtil {

    private static final MyLogger LOG = MyLoggerFactory.getLogger(CommonUtil.class);

    private static Map<Integer, String> plaobjsecodetypeMap = new HashMap<Integer, String>() {{
        put(1, "机构内码");
        put(2, "证券内码");
        put(3, "未知");
    }};

    private static Map<Integer, String> plaobjtypeMap = new HashMap<Integer, String>() {{
        put(1, "战略投资者");
        put(2, "网下机构投资者");
        put(3, "控股股东");
        put(4, "非控股股东");
        put(5, "实际控制人");
        put(6, "关联方");
        put(7, "公司员工");
    }};

    public static Map<Integer, String> getPlaobjsecodetypeMap() {
        return plaobjsecodetypeMap;
    }

    public static Map<Integer, String> getPlaobjtypeMap() {
        return plaobjtypeMap;
    }

    public static boolean isNumeric(String input) {
        try {
            // noinspection ResultOfMethodCallIgnored
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidYear(String input) {
        try {
            int year = Integer.parseInt(input);
            if (year > 0 && year < DateUtil.getCurrentYear()) {
                return true;
            }
        } catch (NumberFormatException ignored) {
        }
        return false;
    }

    public static float roundFloat(final float number, final int decimalPlaces) {
        float precision = 1.0F;
        // noinspection StatementWithEmptyBody
        for (int i = 0; i < decimalPlaces; i++, precision *= 10) {
        }
        return ((int) ((number * precision) + 0.5) / precision);
    }

//    public static void updateListedMap(Object key, Object value, Map<Object, List<Object>> map) {
//        if (map.containsKey(key)){
//            List<Object> list = map.get(key);
//            list.add(value);
//        }else{
//            List<Object> list = new LinkedList<>();
//            list.add(value);
//            map.put(key, list);
//        }

    public static <K, V> void updateListedMap(K key, V value, Map<K, List<V>> map) {
        if (map.containsKey(key)) {
            List<V> list = map.get(key);
            list.add(value);
        } else {
            List<V> list = new LinkedList<>();
            list.add(value);
            map.put(key, list);
        }
    }

    /***
     * encode by Base64
     */
    public static String encodeBase64(byte[] input) {
        try {
            Class clazz = Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
            Method mainMethod = clazz.getMethod("encode", byte[].class);
            mainMethod.setAccessible(true);
            Object retObj = mainMethod.invoke(null, new Object[]{input});
            return (String) retObj;
        } catch (Exception e) {
            LOG.error(e);
            return null;
        }
    }

    /***
     * decode by Base64
     */
    public static byte[] decodeBase64(String input) {
        try {
            Class clazz = Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
            Method mainMethod = clazz.getMethod("decode", String.class);
            mainMethod.setAccessible(true);
            Object retObj = mainMethod.invoke(null, input);
            return (byte[]) retObj;
        } catch (Exception e) {
            LOG.error(e);
            return null;
        }
    }

    public static void main(String[] args) {
        String a = "asdf";
        String b = "fdsa";
        String c = encodeBase64(a.getBytes());
        System.out.println(c);
        byte[] d = decodeBase64(c);
        String e = new String(d);
        System.out.println(e);
        System.out.println(null + b);
        System.out.println(b + null);
        System.out.println(b);
        String f = "ODA1MDAyNTnnj6DmtbfmrKflipvphY3nvZHoh6rliqjljJbogqHku73mnInpmZDlhazlj7g=";
        byte[] g = decodeBase64(f);
        String h = new String(g);
        System.out.println(h);
    }
}
