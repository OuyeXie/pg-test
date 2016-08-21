package com.ouyexie.pg.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ouyexie.pg.data.Column;
import com.ouyexie.pg.data.Metadata;
import com.ouyexie.pg.database.Postgresql;
import com.ouyexie.pg.exception.BusinessException;
import com.ouyexie.pg.exception.ParamException;
import com.ouyexie.pg.log4j.MyLogger;
import com.ouyexie.pg.log4j.MyLoggerFactory;
import com.ouyexie.pg.utils.Constant;

import javax.ws.rs.core.MultivaluedMap;
import java.sql.*;
import java.util.*;

/**
 * Created by ouyexie on 8/27/16.
 */
@SuppressWarnings("unchecked")
public class Logic {
    private static final MyLogger LOG = MyLoggerFactory.getLogger(Logic.class);
    private static Postgresql postgresql = Postgresql.getInstance();
    private static ObjectMapper mapper = new ObjectMapper();

    public static String getMetadata() {
        return getMetadata(null);
    }

    /**
     * get metadata from table or database
     *
     * @param table
     * @return
     */
    public static String getMetadata(String table) {
        String info = null;

        Connection conn = null;
        try {
            conn = postgresql.getConnection();
            // Get the MetaData
            DatabaseMetaData metaData = conn.getMetaData();
            if (table == null || table.isEmpty()) {
                // Get table information
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
                String primaryKey = null;
                ResultSet primaryKeys = metaData.getPrimaryKeys("", "", table);
                if (primaryKeys.next()) {
                    primaryKey = primaryKeys.getString(4);
                }

                List<Column> columns = new LinkedList<>();
                ResultSet rs = metaData.getColumns("", "", table, null);
                while (rs.next()) {
                    /*
                     example:
                     "references": {
                      --  "schema": "public",
                        "column": "id",
                      --  "table": "competition"
                      },
                      --"default": null,
                      --"precision": 32,
                      "updatable": true,
                      --"schema": "public",
                      --"name": "competition",
                      --"type": "integer",
                      --"maxLen": null,
                      "enum": [],
                      --"nullable": false,
                      --"position": 2

                     *  <OL>
                     *  <LI><B>TABLE_CAT</B> String {@code =>} table catalog (may be <code>null</code>)
                     *  <LI><B>TABLE_SCHEM</B> String {@code =>} table schema (may be <code>null</code>)
                     *  <LI><B>TABLE_NAME</B> String {@code =>} table name
                     *  <LI><B>COLUMN_NAME</B> String {@code =>} column name
                     *  <LI><B>DATA_TYPE</B> int {@code =>} SQL type from java.sql.Types
                     *  <LI><B>TYPE_NAME</B> String {@code =>} Data source dependent type name,
                     *  for a UDT the type name is fully qualified
                     *  <LI><B>COLUMN_SIZE</B> int {@code =>} column size.
                     *  <LI><B>BUFFER_LENGTH</B> is not used.
                     *  <LI><B>DECIMAL_DIGITS</B> int {@code =>} the number of fractional digits. Null is returned for data types where
                     * DECIMAL_DIGITS is not applicable.
                     *  <LI><B>NUM_PREC_RADIX</B> int {@code =>} Radix (typically either 10 or 2)
                     *  <LI><B>NULLABLE</B> int {@code =>} is NULL allowed.
                     *      <UL>
                     *      <LI> columnNoNulls - might not allow <code>NULL</code> values
                     *      <LI> columnNullable - definitely allows <code>NULL</code> values
                     *      <LI> columnNullableUnknown - nullability unknown
                     *      </UL>
                     *  <LI><B>REMARKS</B> String {@code =>} comment describing column (may be <code>null</code>)
                     *  <LI><B>COLUMN_DEF</B> String {@code =>} default value for the column, which should be interpreted as a string when the value is enclosed in single quotes (may be <code>null</code>)
                     *  <LI><B>SQL_DATA_TYPE</B> int {@code =>} unused
                     *  <LI><B>SQL_DATETIME_SUB</B> int {@code =>} unused
                     *  <LI><B>CHAR_OCTET_LENGTH</B> int {@code =>} for char types the
                     *       maximum number of bytes in the column
                     *  <LI><B>ORDINAL_POSITION</B> int {@code =>} index of column in table
                     *      (starting at 1)
                     *  <LI><B>IS_NULLABLE</B> String  {@code =>} ISO rules are used to determine the nullability for a column.
                     *       <UL>
                     *       <LI> YES           --- if the column can include NULLs
                     *       <LI> NO            --- if the column cannot include NULLs
                     *       <LI> empty string  --- if the nullability for the
                     * column is unknown
                     *       </UL>
                     *  <LI><B>SCOPE_CATALOG</B> String {@code =>} catalog of table that is the scope
                     *      of a reference attribute (<code>null</code> if DATA_TYPE isn't REF)
                     *  <LI><B>SCOPE_SCHEMA</B> String {@code =>} schema of table that is the scope
                     *      of a reference attribute (<code>null</code> if the DATA_TYPE isn't REF)
                     *  <LI><B>SCOPE_TABLE</B> String {@code =>} table name that this the scope
                     *      of a reference attribute (<code>null</code> if the DATA_TYPE isn't REF)
                     *  <LI><B>SOURCE_DATA_TYPE</B> short {@code =>} source type of a distinct type or user-generated
                     *      Ref type, SQL type from java.sql.Types (<code>null</code> if DATA_TYPE
                     *      isn't DISTINCT or user-generated REF)
                     *   <LI><B>IS_AUTOINCREMENT</B> String  {@code =>} Indicates whether this column is auto incremented
                     *       <UL>
                     *       <LI> YES           --- if the column is auto incremented
                     *       <LI> NO            --- if the column is not auto incremented
                     *       <LI> empty string  --- if it cannot be determined whether the column is auto incremented
                     *       </UL>
                     *   <LI><B>IS_GENERATEDCOLUMN</B> String  {@code =>} Indicates whether this is a generated column
                     *       <UL>
                     *       <LI> YES           --- if this a generated column
                     *       <LI> NO            --- if this not a generated column
                     *       <LI> empty string  --- if it cannot be determined whether this is a generated column
                     *       </UL>
                     *  </OL>
                     *
                     */
                    //TODO: all attributes
                    String schema = rs.getString("TABLE_SCHEM");
                    String name = rs.getString("COLUMN_NAME");
                    String type = rs.getString("TYPE_NAME");
                    int precision = rs.getInt("DECIMAL_DIGITS");
                    String _default = rs.getString("COLUMN_DEF");
                    String maxLen = rs.getString("CHAR_OCTET_LENGTH");
                    int position = rs.getInt("ORDINAL_POSITION");
                    int nullable = rs.getInt("NULLABLE"); //FIXME: ?boolean is not better?
                    String ref_schema = rs.getString("SCOPE_SCHEMA");
                    String ref_table = rs.getString("SCOPE_TABLE");
                    Map<String, String> references = null;
                    if (ref_schema != null && ref_table != null) {
                        references = new HashMap() {{
                            put("schema", ref_schema);
                            put("table", ref_table);
                        }};
                    }
                    Column column = new Column(references, _default, precision, false, schema, name, type, maxLen, new ArrayList<String>(0), nullable, position);
                    columns.add(column);
                }
                Metadata metadata = new Metadata(primaryKey, columns);
                String metadataInfo = null;
                try {
                    ObjectMapper m_mapper = new ObjectMapper();
                    metadataInfo = m_mapper.writeValueAsString(metadata);
                } catch (JsonProcessingException e) {
                    LOG.error(e);
                }
                info = metadataInfo;
            }
        } catch (SQLException e) {
            LOG.error(e);
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOG.error(e);
                }
            }
        }
        return info;
    }

    /**
     * select result from table
     *
     * @param table
     * @param queryParams
     * @return
     * @throws BusinessException
     */
    public static List<Map<String, String>> select(String table, MultivaluedMap<String, String> queryParams) throws BusinessException {
        String select = null;
        String orderBy = null;
        String limit = null;
        String offset = null;
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
                    case Constant.Const.LIMIT:
                        limit = queryParamValueZero;
                        break;
                    case Constant.Const.OFFSET:
                        offset = queryParamValueZero;
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
                where += String.format("%s %s %s", column, operator, value);
            }
        }

        String query = null;
        query = String.format("SELECT %s FROM %s", (select == null || select.isEmpty()) ? "*" : select, table);

        if (!where.isEmpty()) {
            query += String.format(" %s %s", Constant.Const.WHERE, where);
        }
        if (orderBy != null && !orderBy.isEmpty()) {
            query += String.format(" %s %s", Constant.Const.ORDER_BY, orderBy);
        }
        if (offset != null && !offset.isEmpty()) {
            query += String.format(" %s %s", Constant.Const.OFFSET, offset);
        }
        if (limit != null && !limit.isEmpty()) {
            query += String.format(" %s %s", Constant.Const.LIMIT, limit);
        }
        query += ";";

        List<Map<String, String>> results = new LinkedList<>();
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
                // output in a certain order
                Map<String, String> record = new LinkedHashMap<>();
                for (int i = 1; i <= columnNum; i++) {
                    String name = rm.getColumnName(i);
                    String value = rs.getString(i);
                    record.put(name, value);
                }
                results.add(record);
            }
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOG.error(e);
                }
            }
        }
        return results;
    }

}
