package com.ouyexie.pg.utils;

import com.ouyexie.pg.data.Pair;
import com.ouyexie.pg.log4j.MyLogger;
import com.ouyexie.pg.log4j.MyLoggerFactory;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Ouye Xie
 */
public class CollectionUtil {
    private static final MyLogger LOG = MyLoggerFactory.getLogger(StringUtil.class);

    public static <T> Set<Pair<T, T>> listToPairSet(List<T> input) {
        Set<Pair<T, T>> output = new HashSet<>();
        int l = input.size();
        for (int i = 0; i < l; i++) {
            for (int j = i; j < l; j++) {
                if (i == j) {
                    continue;
                }
                Pair<T, T> pair = new Pair<>(input.get(i), input.get(j));
                output.add(pair);
            }
        }
        return output;
    }

    public static <T> Set<Pair<T, T>> setToPairSet(Set<T> input) {
        List<T> list = new ArrayList<T>(input);
        Set<Pair<T, T>> output = listToPairSet(list);
        return output;
    }

    public static Map sortByValue(Map map, final boolean reverse) {
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {

            public int compare(Object o1, Object o2) {
                if (reverse) {
                    return -((Comparable) ((Map.Entry) (o1)).getValue())
                            .compareTo(((Map.Entry) (o2)).getValue());
                }
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });

        Map result = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public static List resultSetToArrayList(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        List list = new LinkedList<>();
        while (rs.next()){
            HashMap row = new HashMap(columns);
            for(int i=1; i<=columns; ++i){
                row.put(md.getColumnName(i),rs.getObject(i));
            }
            list.add(row);
        }
        return list;
    }

    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("a", 4);
        map.put("b", 1);
        map.put("c", 3);
        map.put("d", 2);
        Map sorted = sortByValue(map, true);
        System.out.println(sorted); // {a=4, c=3, d=2, b=1}
    }
}
