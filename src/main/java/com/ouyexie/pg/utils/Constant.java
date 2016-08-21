package com.ouyexie.pg.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Constant {

    public static void main(String[] args) {
    }

    public interface ErrCode {
        String SERVICE_UNAVAILABLE = "4001";
        String BAD_PARAM = "5001";
        String NOT_IMPLEMENTED = "5002";
        String BAD_HEADER = "5003";
    }

    public interface ErrMsg {
        String SERVICE_UNAVAILABLE = "service is unavailable";
        String BAD_PARAM = "param is bad";
        String NOT_IMPLEMENTED = "not implemented";
        String BAD_HEADER = "header is bad";
    }

    public interface InfoMsg {
    }

    public interface Field {
        /*
         * POSTGRESQL
         */
    }

    public interface Config {

        /*
         * POSTGRESQL
         */
        String PARAM_POSTGRESQL_HOST = "param.postgresql.host";
        String PARAM_POSTGRESQL_PORT = "param.postgresql.port";
        String PARAM_POSTGRESQL_USER = "param.postgresql.user";
        String PARAM_POSTGRESQL_PASSWORD = "param.postgresql.password";
        String PARAM_POSTGRESQL_DATABASE = "param.postgresql.database";
    }

    public interface Setting {
        int DB_FETCH_SIZE = 1000;
    }

    public interface Default {
    }

    public interface Const {

        String HTTP_SERVICE_NAME = "http_service_pg_test";

        String LOGGING_TEMPLATE = "%s";
        String ERRCODE = "ErrCode";
        String ERRMSG = "ErrMsg";

        String ORDER_BY = "ORDER BY";
        String WHERE = "WHERE";

        String ORDER = "order";
        String SELECT = "select";
        String LIMIT = "limit";
        String OFFSET = "offset";
        String TRUE = "true";
        String FALSE = "false";
        String NULL = "null";
        String EQ = "=";
        String NEQ = "<>";
        String LT = "<";
        String GT = ">";
        String LTE = "<=";
        String GTE = ">=";
        String is = "IS";
        String isnot = "IS NOT";

        @SuppressWarnings("unchecked")
        Set<String> RESERVED = new HashSet() {{
            add(ORDER);
            add(SELECT);
            add(LIMIT);
            add(OFFSET);
        }};

        @SuppressWarnings("unchecked")
        Map<String, Object> VALUE_RESERVED = new HashMap() {{
            put(TRUE, true);
            put(FALSE, false);
            put(NULL, null);
        }};

        @SuppressWarnings("unchecked")
        Map<String, String> OPERATORS = new HashMap() {{
            put("eq", EQ);
            put("neq", NEQ);
            put("lt", LT);
            put("gt", GT);
            put("lte", LTE);
            put("gte", GTE);
            put("is", is);
            put("isnot", isnot);
        }};
    }
}
