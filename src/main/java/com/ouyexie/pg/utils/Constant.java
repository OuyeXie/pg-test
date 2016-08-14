package com.ouyexie.pg.utils;

import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Constant {

    public static void main(String[] args) {
        String a = "深圳市前海锦泓资本管理有限公司";
        String b = "做市专用证券账户";
        String c = a.replace(b, "");
        System.out.println(c);
        String d = "管理有限公司";
        String e = a.replace(d, "");
        System.out.println(e);
        for (int i = 0; i < 0; i++) {
            System.out.println(i);
        }
        Date now = new Date();
        System.out.println(now.toString());
        System.out.println(now.getTime());
    }

    public interface ErrCode {
        String SERVICE_UNAVAILABLE = "4001";

        String BAD_ARGUMENT_CHOICE = "5001";
        String INTERNAL_ERROR = "5002";

    }

    public interface ErrMsg {
        String SERVICE_UNAVAILABLE = "service is unavailable";

        String BAD_ARGUMENT_CHOICE = "bad choice of argument";
        String INTERNAL_ERROR = "internal calculation error (dimension mismatch)";
    }

    public interface InfoMsg {
    }

    public interface Field {
        /*
         * PPOSTGRESQL
         */
        String POSTGRESQL_ID = "id";
        String POSTGRESQL_CREATED_AT = "created_at";
        String POSTGRESQL_UPDATED_AT = "updated_at";
        String POSTGRESQL_COMPCODE = "compcode";
        String POSTGRESQL_COMPNAME = "compname";
        String POSTGRESQL_ADDRESS = "address";
        String POSTGRESQL_VERSION = "version";
        String POSTGRESQL_CLUSTER = "graph";
        String POSTGRESQL_SHAREHOLDERS = "shareholders";
        String POSTGRESQL_LNG = "lng";
        String POSTGRESQL_LAT = "lat";
        String POSTGRESQL_CODE = "code";
        String POSTGRESQL_NAME = "name";
        String POSTGRESQL_KEY_NO = "key_no";
        String POSTGRESQL_FOUND = "found";
        String POSTGRESQL_RESULT = "result";
        String POSTGRESQL_PRODUCT_NAME = "product_name";
        String POSTGRESQL_MANAGER = "manager";
        String POSTGRESQL_INVESTMENT_CONSULTANT = "investment_consultant";
        String POSTGRESQL_DISPLAYED_NAME = "displayed_name";
        String POSTGRESQL_DISPLAYED_DESCRIPTION = "displayed_description";
        String POSTGRESQL_DISPLAYED_FIELD = "displayed_field";
        String POSTGRESQL_DISPLAYED_KEYMAN = "displayed_keyman";


        /*
         * MYSQL
         */
        String MYSQL_SETYPE = "SETYPE";
        String MYSQL_PLAOBJNAME = "PLAOBJNAME";
        String MYSQL_PLAOBJTYPE = "PLAOBJTYPE";
        String MYSQL_PLAOBJSECODETYPE = "PLAOBJSECODETYPE";
        String MYSQL_COMPNAME = "COMPNAME";
        String MYSQL_COMPSNAME = "COMPSNAME";
        String MYSQL_COMPCODE = "COMPCODE";
        String MYSQL_SECODE = "SECODE";
        String MYSQL_SENAME = "SENAME";
        String MYSQL_PLAOBJSEID = "PLAOBJSEID";
        String MYSQL_OFFICEADDR = "OFFICEADDR";
        String MYSQL_REGADDR = "REGADDR";
        String MYSQL_SHHOLDERCODE = "SHHOLDERCODE";
        String MYSQL_SHHOLDERNAME = "SHHOLDERNAME";
        String MYSQL_SHHOLDERTYPE = "SHHOLDERTYPE";
        String MYSQL_RANK = "RANK";
        String MYSQL_HOLDERRTO = "HOLDERRTO";
        String MYSQL_HOLDERAMT = "HOLDERAMT";
        String MYSQL_PSCODE = "PSCODE";
        String MYSQL_PSCNAME = "PSCNAME";
        String MYSQL_PERSONALCODE = "PERSONALCODE";
        String MYSQL_CNAME = "CNAME";
        String MYSQL_RPAYAMT = "RPAYAMT";
        String MYSQL_TOTQTYAFT = "TOTQTYAFT";
        String MYSQL_ISSPRICE = "ISSPRICE";
        String MYSQL_PUBLISHDATE = "PUBLISHDATE";
        String MYSQL_ACTPLAQTY = "ACTPLAQTY";
        String MYSQL_ID = "ID";
        String MYSQL_ISSUEID = "ISSUEID";
        String MYSQL_PLANTOTRAISEAMT = "PLANTOTRAISEAMT";
        String MYSQL_ACTTOTRAISEAMT = "ACTTOTRAISEAMT";
        String MYSQL_SYMBOL = "SYMBOL";
        String MYSQL_FAMNAME = "FAMNAME";
        String MYSQL_KEEPERNAME = "KEEPERNAME";
        String MYSQL_PARTYNAME = "PARTYNAME";
        String MYSQL_ACLOADJUSTPRC = "ACLOADJUSTPRC";
        String MYSQL_TCLOSE = "TCLOSE";
        String MYSQL_SWLEVEL2NAME = "SWLEVEL2NAME";
        String MYSQL_SESNAME = "SESNAME";
        String MYSQL_LEGREP = "LEGREP";
        String MYSQL_TDATE = "TDATE";
        String MYSQL_TRADEPRICE = "TRADEPRICE";
        String MYSQL_TRADEVOL = "TRADEVOL";
        String MYSQL_BERACCCODE = "BERACCCODE";
        String MYSQL_BERACCNAME = "BERACCNAME";
        String MYSQL_SERACCCODE = "SERACCCODE";
        String MYSQL_SERACCNAME = "SERACCNAME";
        String MYSQL_BERBROCODE = "BERBROCODE";
        String MYSQL_BERBRONAME = "BERBRONAME";
        String MYSQL_SERBROCODE = "SERBROCODE";
        String MYSQL_SERBRONAME = "SERBRONAME";


        /*
         * NEO4J
         */
        String NEO4J_ID = "id";
        String NEO4J_ISSUEID = "issueid";
        String NEO4J_CODE = "code";
        String NEO4J_NAME = "name";
        String NEO4J_ALIAS = "alias";
        String NEO4J_KEYMAN = "keyman";
        String NEO4J_FIELD = "field";
        String NEO4J_SNAME = "sname";
        String NEO4J_SYMBOL = "symbol";
        String NEO4J_PLAOBJSEIDS = "plaobjseids";
        String NEO4J_RPAYAMT = "rpayamt";
        String NEO4J_AMOUNT = "amount";
        String NEO4J_VALUE = "value";
        String NEO4J_PROFIT = "profit";
        String NEO4J_COUNT = "count";
        String NEO4J_AMOUNT_365 = "amount365";
        String NEO4J_VALUE_365 = "value365";
        String NEO4J_PROFIT_365 = "profit365";
        String NEO4J_COUNT_365 = "count365";
        String NEO4J_AMOUNT_90 = "amount90";
        String NEO4J_VALUE_90 = "value90";
        String NEO4J_PROFIT_90 = "profit90";
        String NEO4J_COUNT_90 = "count90";
        String NEO4J_SIMPLE = "simple";
        String NEO4J_LEVENSHTEIN = "levenshtein";
        String NEO4J_PLAOBJSEID = "plaobjseid";
        String NEO4J_PLAOBJNAME = "plaobjname";
        String NEO4J_TOTQTYAFT = "totqtyaft";
        String NEO4J_ISSPRICE = "issprice";
        String NEO4J_PUBLISHDATE = "publishdate";
        String NEO4J_PUBLISHINT = "publishint";
        String NEO4J_ACTPLAQTY = "actplaqty";
        String NEO4J_PLANTOTRAISEAMT = "plantotraiseamt";
        String NEO4J_ACTTOTRAISEAMT = "acttotraiseamt";
        String NEO4J_DESCRIPTION = "description";
        String NEO4J_VALID = "valid";
        String NEO4J_PLAOBJTYPE = "plaobjtype";
        String NEO4J_PLAOBJSECODETYPE = "plaobjsecodetype";
        String NEO4J_NUMBER = "number";
        String NEO4J_SUFFIX = "suffix";
        String NEO4J_CONTENT = "content";
        String NEO4J_BANNER = "banner";
        String NEO4J_CURRPRICE = "currprice";
        String NEO4J_GROUPS = "groups";
        String NEO4J_GROUPCOUNT = "groupcount";
        String NEO4J_CREATEDAT = "createdat";
        String NEO4J_UPDATEDAT = "updatedat";
        String NEO4J_SHHOLDERTYPE = "shholdertype";
        String NEO4J_TDATE = "tdate";
        String NEO4J_SECODE = "secode";
        String NEO4J_TRADEPRICE = "tradeprice";
        String NEO4J_TRADEVOL = "tradevol";
        String NEO4J_BERACCCODE = "beracccode";
        String NEO4J_BERACCNAME = "beraccname";
        String NEO4J_SERACCCODE = "seracccode";
        String NEO4J_SERACCNAME = "seraccname";
        String NEO4J_BERBROCODE = "berbrocode";
        String NEO4J_BERBRONAME = "serbroname";
        String NEO4J_SERBROCODE = "berbrocode";
        String NEO4J_SERBRONAME = "serbroname";
        String NEO4J_ROLE = "role";
        String NEO4J_PTINFO = "ptinfo";
        String NEO4J_DISPLAYED_NAME = "displayedname";
        String NEO4J_DISPLAYED_DESCRIPTION = "displayeddescription";
        String NEO4J_DISPLAYED_FIELD = "displayedfield";
        String NEO4J_DISPLAYED_KEYMAN = "displayedkeyman";
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
        String PARAM_POSTGRESQL_TABLE_INVESTORS = "param.postgresql.table.investors";
        String PARAM_POSTGRESQL_TABLE_INVESTORS_RELATION = "param.postgresql.table.investors_relation";

        String PARAM_POSTGRESQL_HOST_TEST = "param.postgresql.host.test";
        String PARAM_POSTGRESQL_PORT_TEST = "param.postgresql.port.test";
        /*
         * MYSQL
         */
        String PARAM_MYSQL_HOST = "param.mysql.host";
        String PARAM_MYSQL_PORT = "param.mysql.port";
        String PARAM_MYSQL_USER = "param.mysql.user";
        String PARAM_MYSQL_PASSWORD = "param.mysql.password";
        String PARAM_MYSQL_DATABASE = "param.mysql.database";
        String PARAM_MYSQL_TABLE_TQ_OA_STCODE = "param.mysql.table.tq_oa_stcode";
        String PARAM_MYSQL_TABLE_TQ_THSK_PROEQPMT = "param.mysql.table.tq_thsk_proeqpmt";
        String PARAM_MYSQL_TABLE_TQ_SK_THIRDISSUE = "param.mysql.table.tq_sk_thirdissue";
        String PARAM_MYSQL_TABLE_TQ_COMP_INFO = "param.mysql.table.tq_comp_info";
        String PARAM_MYSQL_TABLE_TQ_COMP_MANAGER = "param.mysql.table.tq_comp_manager";
        String PARAM_MYSQL_TABLE_TQ_COMP_SKHOLDERCHG = "param.mysql.table.tq_comp_skholderchg";
        String PARAM_MYSQL_TABLE_TQ_SK_OTSHOLDER = "param.mysql.table.tq_sk_otsholder";
        String PARAM_MYSQL_TABLE_TQ_SK_SHAREHOLDER = "param.mysql.table.tq_sk_shareholder";
        String PARAM_MYSQL_TABLE_TQ_THSK_SHAREHOLDER = "param.mysql.table.tq_thsk_shareholder";
        String PARAM_MYSQL_TABLE_TQ_FAMP_BASICINFO = "param.mysql.table.tq_famp_basicinfo";
        String PARAM_MYSQL_TABLE_TQ_FAMP_PARTY = "param.mysql.table.tq_famp_party";
        String PARAM_MYSQL_TABLE_TQ_THSK_ADJUSTQT = "param.mysql.table.tq_thsk_adjustqt";
        String PARAM_MYSQL_TABLE_TQ_THSK_PTINFO = "param.mysql.table.tq_thsk_ptinfo";
        String PARAM_MYSQL_TABLE_TQ_THSK_BASICINFO = "param.mysql.table.tq_thsk_basicinfo";

        /*
         * NEO4J
         */
        String PARAM_NEO4J_HOST = "param.neo4j.host";
        String PARAM_NEO4J_PORT = "param.neo4j.port";
        String PARAM_NEO4J_USER = "param.neo4j.user";
        String PARAM_NEO4J_PASSWORD = "param.neo4j.password";

        String PARAM_NEO4J_HOST_TEST = "param.neo4j.host.test";
        String PARAM_NEO4J_PORT_TEST = "param.neo4j.port.test";
        String PARAM_NEO4J_PASSWORD_TEST = "param.neo4j.password.test";

        /*
         * QICHACHA
         */
        String PARAM_QICHACHA_HOST = "param.qichacha.host";
        String PARAM_QICHACHA_APIKEY = "param.qichacha.apikey";

        /*
         * REDIS
         */
        String PARAM_REDIS_HOST = "param.redis.host";
        String PARAM_REDIS_PORT = "param.redis.port";

        String PARAM_REDIS_HOST_TEST = "param.redis.host.test";
        String PARAM_REDIS_PORT_TEST = "param.redis.port.test";
        String PARAM_REDIS_PASSWORD_TEST = "param.redis.password.test";
    }

    public interface Setting {

        /*
         * INPUT
         */
        int INPUT_REMOVE_NOISE_LEVEL = 1;
        boolean INPUT_IDF = false;

        /*
         * K_MEANS
         */
        boolean KMEANS_EVALUATION_ON = true;

        /*
         * SOM
         */
        int SOM_MAX_ITERATION = 10000;
        boolean SOM_AUTO_SIZE = true;
        int SOM_TARGETTED_DENSITY = 10;
        boolean SOM_EVALUATION_ON = true;

        /*
         * AP
         */
        int AP_MAX_ITERATION = 1000;
        int AP_MAX_COVERAGE_ITERATION = 10;
        double AP_DAMPING_FACTOR = 0.5d; // [0.5, 1)
        boolean AP_EVALUATION_ON = true;

        /*
         * EVALUATION
         */
        boolean EVALUATION_HARD_MODE = false;

        /*
         * GRAPH
         */
        boolean GRAPH_WRITE_INFO = true;
        int GRAPH_RPAYAMT_MAX_LENGTH = 1;
        // do not change this unless you know what you are doing
        // 1 watch out for data in queue and sequence
        // 2 watch out how many connections it is allowed for mysql/pg
        int GRAPH_UPDATER_THREAD_NUMBER = 15;
        int GRAPH_UPDATER_QUEUE_LENGTH = 10000;

        int GRAPH_UPDATER_QUEUE_WAIT_SECONDS = 1;
        long GRAPH_UPDATER_LOG_INTERVAL_MILLISECONDS = 30 * 1000;
        long GRAPH_FIELD_MAX_LENGTH = 2;

        long GRAPH_RPAYAMT_INTERVAL_365_DAY = 365;
        long GRAPH_RPAYAMT_INTERVAL_90_DAY = 90;
        //long[] GRAPH_RPAYAMT_INTERVALS_DAY = new long[]{Long.MAX_VALUE /*from beginning*/, 365 /*one year*/};

        double GRAPH_POSITION_BANNER_THRESHOLD = 10000.0d;
        double GRAPH_INVESTMENT_BANNER_THRESHOLD = 10000.0d;
        double GRAPH_INCREASE_BANNER_THRESHOLD = 0.005d;
        double GRAPH_PROFIT_BANNER_THRESHOLD = 1000.0d;
        int GRAPH_SHAREHOLDER_BANNER_THRESHOLD = 5;
        int GRAPH_SIMPLE_SHAREHOLDER_BANNER_THRESHOLD = 5;

        int GRAPH_INFO_INCREMENTAL_INTERVAL_MILLISECONDES = 5 * 60 * 60 * 1000; // 5 hours, only take care of today's updated node, assume that all expected nodes were updated in the last 5 hours

        /*
         * QICHACHA
         */
        boolean QICHACHA_FORCE_UPDATE = false;

        /*
         * REDIS
         */
        int REDIS_MAX_TOTAL = GRAPH_UPDATER_THREAD_NUMBER;
        int REDIS_MAX_IDLE = GRAPH_UPDATER_THREAD_NUMBER;
        int REDIS_MAX_WAIT_MILLIS = 10000;
        int REDIS_TIMEOUT = 10000;

        /*
         * MYSQL
         */
        int DB_MAX_POOL_SIZE = GRAPH_UPDATER_THREAD_NUMBER;
        int DB_FETCH_SIZE = 1000;
    }

    public interface Default {
        String INVESTOR_OUTPUT = "investors";
        String INVESTOR_SORTFIELD = "rpayamt365";
        int INVESTOR_TOPN = -1;
    }

    public interface Const {

        String HTTP_SERVICE_NAME = "http_service_pg_test";

        String LOGGING_TEMPLATE = "%s";
        String ERRCODE = "ErrCode";
        String ERRMSG = "ErrMsg";
        int _20160101 = 20160101;
        int _20160401 = 20160401;

        String UNKNOWN_NAME = "UnknownName";
        String UNKNOWN_CODE = "UnknownCode";

        String _910 = "910";
        String _SSC = "证券股份有限公司";
        String _SC = "证券有限公司";
        String _SLC = "证券有限责任公司";
        String _AMP = "资产管理计划"; //Asset Management Plan

        /*
         * LABEL
         */
        String ENTITY = "Entity";
        String INVESTOR = "Investor";
        String ORGANIZATION = "Organization";
        String SECURITY = "Security";
        String UNKNOWN = "Unknown";
        String OTHER = "Other";
        String MANAGEMENT_PLAN = "ManagementPlan"; //910
        String SECURITY_COMPANY = "SecurityCompany";
        String GROUP = "Group";
        String MANAGER = "Manager";
        String STOCK = "Stock";
        String SHAREHOLDER = "Shareholder";

        /*
         * RELATIONSHIP
         */
        String INVEST = "Invest";
        String OWN = "Own";
        String MANAGE = "Manage";
        String SELL = "Sell";
        String BUY = "Buy";

        /*
         * DATE
         */
        DateTimeFormatter NUMBER_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

        /*
         * PATH
         */
        String QICHACHA_ECIRELATION = "ECIRelation";
        String QICHACHA_SEARCHCOMPANYRELATION = "SearchCompanyRelation";
        String QICHACHA_ECISIMPLE = "ECISimple";
        String QICHACHA_SEARCH = "Search";
    }
}
