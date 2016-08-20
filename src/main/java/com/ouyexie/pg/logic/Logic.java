package com.ouyexie.pg.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ouyexie.pg.data.Column;
import com.ouyexie.pg.data.Item;
import com.ouyexie.pg.database.Postgresql;
import com.ouyexie.pg.exception.BusinessException;
import com.ouyexie.pg.exception.ParamException;
import com.ouyexie.pg.log4j.MyLogger;
import com.ouyexie.pg.log4j.MyLoggerFactory;
import com.ouyexie.pg.utils.Constant;

import javax.ws.rs.core.MultivaluedMap;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by ouyexie on 8/27/16.
 */
public class Logic {
    private static final MyLogger LOG = MyLoggerFactory.getLogger(Logic.class);
    private static Postgresql postgresql = Postgresql.getInstance();
    private static ObjectMapper mapper = new ObjectMapper();

    public static String getMetadata() {
        return getMetadata(null);
    }

    public static String getMetadata(String table) {
        String info = null;

        Connection conn = null;
        try {
            conn = postgresql.getConnection();
            // Get the MetaData
            DatabaseMetaData metaData = conn.getMetaData();

//            // Get driver information
//            System.out.println("====================================");
//            System.out.println("Driver Informaion");
//            System.out.println(metaData.getDriverName());
//            System.out.println(metaData.getDriverVersion());
//            // Get schema information
//            System.out.println("====================================");
//            System.out.println("Schemas");
//            ResultSet schemas = metaData.getSchemas();
//            while (schemas.next()) {
//                System.out.println(schemas.getString(1));
//            }
            if (table == null || table.isEmpty()) {
                // Get table information
//                System.out.println("====================================");
//                System.out.println("Tables");
                ResultSet tables = metaData.getTables("", "", "", null);
                List<String> tableList = new LinkedList<>();
                while (tables.next()) {
                    tableList.add(tables.getString(3));
                }
                String tableInfo = null;
                try {
                    ObjectMapper m_mapper = new ObjectMapper();
                    tableInfo = m_mapper.writeValueAsString(tableList);
                } catch (JsonProcessingException e) {
                    LOG.error(e);
                }
                info = String.format("{tables: %s}", tableInfo);
            } else {
                // Certain table information
//            System.out.println("====================================");
//            System.out.println(table);
                ResultSet columns = metaData.getColumns("", "", table, null);
                List<Column> columnList = new LinkedList<>();
                while (columns.next()) {
                    String name = columns.getString(4);
                    String type = columns.getString(6);
                    Column column = new Column(name, type);
                    columnList.add(column);
                }
                String columnInfo = null;
                try {
                    ObjectMapper m_mapper = new ObjectMapper();
                    columnInfo = m_mapper.writeValueAsString(columnList);
                } catch (JsonProcessingException e) {
                    LOG.error(e);
                }
                info = columnInfo;
            }
        } catch (SQLException e) {
            LOG.error(e);
            e.printStackTrace();
        } finally {
//            if (conn != null) {
//                try {
//                    conn.close();
//                } catch (SQLException e) {
//                    LOG.error(e);
//                }
//            }
        }
        return info;
    }

    public static String select(String table, MultivaluedMap<String, String> queryParams) throws BusinessException {
        String select = null;
        String orderBy = null;
        String where = "";
        for (Map.Entry<String, List<String>> queryParam : queryParams.entrySet()) {
            String queryParamKey = queryParam.getKey();
            List<String> queryParamValue = queryParam.getValue();
            String queryParamValueZero = queryParamValue.get(0);
            if (Constant.Const.RESERVED.contains(queryParamKey)) {
                switch (queryParamKey) {
                    case Constant.Const.SELECT:
                        select = queryParamValueZero;
                        break;
                    case Constant.Const.ORDER:
                        //TODO: nulllast and nullfirst
                        orderBy = queryParamValueZero.replace(".", " ");
                        break;
                    default:
                        LOG.error(String.format("bad param: [%s]", queryParams.toString()));
                        throw new ParamException();
                }
            } else {
                String column = queryParamKey;
                String[] condition = queryParamValueZero.split("\\.");
                if (condition.length != 2) {
                    LOG.error(String.format("bad param: [%s]", queryParams.toString()));
                    throw new ParamException();
                }
                String operator = Constant.Const.OPERATORS.get(condition[0]);
                if (operator == null || operator.isEmpty()) {
                    LOG.error(String.format("bad param: [%s]", queryParams.toString()));
                    throw new ParamException();
                }
                String value = condition[1];
                if (value == null || value.isEmpty()) {
                    LOG.error(String.format("bad param: [%s]", queryParams.toString()));
                    throw new ParamException();
                }
                if (!where.isEmpty()) {
                    where += " AND ";
                }
                where += String.format("%s%s%s", column, operator, value);
            }
        }

        String query = null;

        query = String.format("SELECT %s FROM %s", (select == null || select.isEmpty()) ? "*" : select, table);

        if (where != null && !where.isEmpty()) {
            query += String.format(" %s %s", Constant.Const.WHERE, where);
        }
        if (orderBy != null && !orderBy.isEmpty()) {
            query += String.format(" %s %s", Constant.Const.ORDER_BY, orderBy);
        }

        query += ";";

        List<List> results = new LinkedList<>();
        Connection conn = null;
        try {
            conn = postgresql.getConnection();
            Statement st = conn.createStatement();
            // change size to 0 if you want to stop using cursor
            st.setFetchSize(Constant.Setting.DB_FETCH_SIZE);
            LOG.debug(query);
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                ResultSetMetaData rm = rs.getMetaData();
                int columnNum = rm.getColumnCount();
                List<Item> record = new LinkedList<>();
                for (int i = 1; i <= columnNum; i++) {
                    String name = rm.getColumnName(i);
                    String type = rm.getColumnTypeName(i);
                    String value = rs.getString(i);
                    record.add(new Item(name, type, value));
                }
                results.add(record);
            }
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
//            if (conn != null) {
//                try {
//                    conn.close();
//                } catch (SQLException e) {
//                    LOG.error(e);
//                }
//            }
        }
        String resultsString = null;
        try {
            resultsString = mapper.writeValueAsString(results);
        } catch (JsonProcessingException e) {
            LOG.error(e);
        }
        return resultsString;
    }

}
