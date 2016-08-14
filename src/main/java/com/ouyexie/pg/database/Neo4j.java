package com.ouyexie.pg.database;

import com.ouyexie.pg.exception.BusinessException;
import com.ouyexie.pg.log4j.MyLogger;
import com.ouyexie.pg.log4j.MyLoggerFactory;
import com.ouyexie.pg.utils.*;
import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.exceptions.Neo4jException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ouye Xie
 */
public class Neo4j {
    private static final MyLogger LOG = MyLoggerFactory.getLogger(Neo4j.class);

    private static Neo4j m_instance;

    Driver driver;

    private Map<String, String> m_codeToExchange = new HashMap<>();

    private Neo4j(Map<String, String> configMap) throws BusinessException {
        String host = GlobalConfig.DEBUGMODE ? configMap.get(Constant.Config.PARAM_NEO4J_HOST_TEST) : configMap.get(Constant.Config.PARAM_NEO4J_HOST);
        String port = GlobalConfig.DEBUGMODE ? configMap.get(Constant.Config.PARAM_NEO4J_PORT_TEST) : configMap.get(Constant.Config.PARAM_NEO4J_PORT);
        String user = configMap.get(Constant.Config.PARAM_NEO4J_USER);
        String password = GlobalConfig.DEBUGMODE ? configMap.get(Constant.Config.PARAM_NEO4J_PASSWORD_TEST) : configMap.get(Constant.Config.PARAM_NEO4J_PASSWORD);

        driver = GraphDatabase.driver(String.format("bolt://%s", host), AuthTokens.basic(user, password));
    }

    public static Neo4j getInstance() {
        if (m_instance == null) {
            throw new RuntimeException();
        }
        return Neo4j.m_instance;
    }

    public static Neo4j getInstance(Map<String, String> configMap) throws BusinessException {
        if (m_instance == null) {
            synchronized (Postgresql.class) {
                if (m_instance == null) {
                    m_instance = new Neo4j(configMap);
                }
            }
        }
        return Neo4j.m_instance;
    }

    /*
        session.run( "CREATE (a:Person {name:'Arthur', title:'King'})" );
        StatementResult result = session.run( "MATCH (a:Person) WHERE a.name = 'Arthur' RETURN a.name AS name, a.title AS title" );
        while ( result.hasNext() )
        {
            Record record = result.next();
            System.out.println( record.get( "title" ).asString() + " " + record.get("name").asString() );
        }
     */
    public StatementResult run(String statementTemplate) {
        LOG.debug(String.format("neo4j statementTemplate: [%s]", statementTemplate));
        Session session = null;
        try {
            session = driver.session();
            StatementResult sr = session.run(statementTemplate);
            return sr;
        } catch (Neo4jException e) {
            LOG.error(e);
            e.printStackTrace();
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (Neo4jException e) {
                    LOG.error(e);
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public StatementResult run(String statementTemplate, Value parameters) {
        LOG.debug(String.format("neo4j statementTemplate: [%s],  parameters: [%s]", statementTemplate, parameters.toString()));
        Session session = null;
        try {
            session = driver.session();
            StatementResult sr = session.run(statementTemplate, parameters);
            return sr;
        } catch (Neo4jException e) {
            LOG.error(e);
            e.printStackTrace();
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (Neo4jException e) {
                    LOG.error(e);
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public void close() {
        try {
            driver.close();
            m_instance = null;
        } catch (Exception e) {
            LOG.error(e);
        }
    }

    public void mergeEntityNode(String name) {
        if (name == null || name.isEmpty()) {
            LOG.error("name can never be null or empty");
            return;
        }
        String code = StockUtil.getCompcode(name);
        String id = CommonUtil.encodeBase64((code).getBytes());
        String ts = DateUtil.formatComparableDate(new Date());
        String query = String.format("MERGE (a:%s {%s:'%s',%s:'%s'}) ON CREATE SET a.%s='%s', a.%s='%s', a.%s='%s' ON MATCH SET a.%s='%s'", Constant.Const.ENTITY, Constant.Field.NEO4J_ID, id, Constant.Field.NEO4J_CODE, code, Constant.Field.NEO4J_NAME, name, Constant.Field.NEO4J_CREATEDAT, ts, Constant.Field.NEO4J_UPDATEDAT, ts, Constant.Field.NEO4J_UPDATEDAT, ts);
        run(query);
    }

    /*
     * identified by code
     */
    public void mergeEntityNode(String code, String name) {
        if (code == null || code.isEmpty()) {
            LOG.error("code can never be null or empty");
            return;
        }
        String realName = StockUtil.getCompnameByCompcode(code);
        if (realName != null && !realName.isEmpty()) {
            name = realName;
        }
        String id = CommonUtil.encodeBase64((code).getBytes());
        String ts = DateUtil.formatComparableDate(new Date());
        String query = String.format("MERGE (a:%s {%s:'%s',%s:'%s'}) ON CREATE SET a.%s='%s', a.%s='%s', a.%s='%s' ON MATCH SET a.%s='%s'", Constant.Const.ENTITY, Constant.Field.NEO4J_ID, id, Constant.Field.NEO4J_CODE, code, Constant.Field.NEO4J_NAME, name, Constant.Field.NEO4J_CREATEDAT, ts, Constant.Field.NEO4J_UPDATEDAT, ts, Constant.Field.NEO4J_UPDATEDAT, ts);
        run(query);
    }

    public void mergeEntityNode(String name, String[] label) {
        if (name == null || name.isEmpty()) {
            LOG.error("name can never be null or empty");
            return;
        }
        String code = StockUtil.getCompcode(name);
        String id = CommonUtil.encodeBase64((code).getBytes());
        String labelString = "";
        for (String l : label) {
            labelString += ":" + l;
        }
        String ts = DateUtil.formatComparableDate(new Date());
        String query = String.format("MERGE (a:%s {%s:'%s',%s:'%s'}) ON CREATE SET a.%s='%s', a.%s='%s', a.%s='%s', a%s ON MATCH SET a.%s='%s', a%s", Constant.Const.ENTITY, Constant.Field.NEO4J_ID, id, Constant.Field.NEO4J_CODE, code, Constant.Field.NEO4J_NAME, name, Constant.Field.NEO4J_CREATEDAT, ts, Constant.Field.NEO4J_UPDATEDAT, ts, labelString, Constant.Field.NEO4J_UPDATEDAT, ts, labelString);
        run(query);
    }

    public void setEntityNodeByIdWithProperties(String id, String properties) {
        if (id == null || id.isEmpty()) {
            LOG.error("id can never be null or empty");
            return;
        }

        String ts = DateUtil.formatComparableDate(new Date());

        String query = String.format("MATCH (a:%s {%s:'%s'}) SET a.%s='%s', %s", Constant.Const.ENTITY, Constant.Field.NEO4J_ID, id, Constant.Field.NEO4J_UPDATEDAT, ts, properties);
        run(query);
    }

    public void setEntityNodeByCodeWithProperties(String code, String properties) {
        if (code == null || code.isEmpty()) {
            LOG.error("name can never be null or empty");
            return;
        }

        String ts = DateUtil.formatComparableDate(new Date());

        String query = String.format("MATCH (a:%s {%s:'%s'}) SET a.%s='%s', %s", Constant.Const.ENTITY, Constant.Field.NEO4J_CODE, code, Constant.Field.NEO4J_UPDATEDAT, ts, properties);
        run(query);
    }

    /*
     * identified by code and name
     */
    public void mergeEntityNode(String code, String name, String[] label, Map<String, String> properties) {
        if (name == null || name.isEmpty()) {
            LOG.error("name can never be null or empty");
            return;
        }
        String id = CommonUtil.encodeBase64((code).getBytes());
        String labelString = "";
        for (String l : label) {
            labelString += ":" + l;
        }
        String propertyString = "";
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            propertyString += String.format(", a.%s='%s'", entry.getKey(), entry.getValue());
        }
        String ts = DateUtil.formatComparableDate(new Date());
        String query = String.format("MERGE (a:%s {%s:'%s',%s:'%s',%s:'%s'}) ON CREATE SET a%s%s, a.%s='%s', a.%s='%s' ON MATCH SET a%s%s, a.%s='%s'", Constant.Const.ENTITY, Constant.Field.NEO4J_ID, id, Constant.Field.NEO4J_CODE, code, Constant.Field.NEO4J_NAME, name, labelString, propertyString, Constant.Field.NEO4J_CREATEDAT, ts, Constant.Field.NEO4J_UPDATEDAT, ts, labelString, propertyString, Constant.Field.NEO4J_UPDATEDAT, ts);
        run(query);
    }

    public void mergeRelationshipByName(String valueA, String valueB, String relation, boolean matchIncluded) {
        String ts = DateUtil.formatComparableDate(new Date());
        String query = matchIncluded ?
                String.format("MATCH (s:%s) WHERE s.%s='%s' WITH s MATCH (c:%s) WHERE c.%s='%s' MERGE (s)-[r%s]->(c) ON CREATE SET r.%s='%s', r.%s='%s' ON MATCH SET r.%s='%s'", Constant.Const.ENTITY, Constant.Field.NEO4J_NAME, valueA, Constant.Const.ENTITY, Constant.Field.NEO4J_NAME, valueB, relation, Constant.Field.NEO4J_CREATEDAT, ts, Constant.Field.NEO4J_UPDATEDAT, ts, Constant.Field.NEO4J_UPDATEDAT, ts) :
                String.format("MATCH (s:%s) WHERE s.%s='%s' WITH s MATCH (c:%s) WHERE c.%s='%s' MERGE (s)-[r%s]->(c) ON CREATE SET r.%s='%s', r.%s='%s'", Constant.Const.ENTITY, Constant.Field.NEO4J_NAME, valueA, Constant.Const.ENTITY, Constant.Field.NEO4J_NAME, valueB, relation, Constant.Field.NEO4J_CREATEDAT, ts, Constant.Field.NEO4J_UPDATEDAT, ts);
        run(query);
    }

    public void mergeRelationshipByCode(String valueA, String valueB, String relation, String properties, boolean matchIncluded) {
        if (valueA == null || valueB == null) {
            return;
        }
        String ts = DateUtil.formatComparableDate(new Date());
        String query = matchIncluded ?
                String.format("MATCH (s:%s {%s:'%s'}) WITH s MATCH (c:%s {%s:'%s'}) MERGE (s)-[r%s]->(c) ON CREATE SET r.%s='%s', r.%s='%s', %s ON MATCH SET r.%s='%s', %s", Constant.Const.ENTITY, Constant.Field.NEO4J_CODE, valueA, Constant.Const.ENTITY, Constant.Field.NEO4J_CODE, valueB, relation, Constant.Field.NEO4J_CREATEDAT, ts, Constant.Field.NEO4J_UPDATEDAT, ts, properties, Constant.Field.NEO4J_UPDATEDAT, ts, properties) :
                String.format("MATCH (s:%s {%s:'%s'}) WITH s MATCH (c:%s {%s:'%s'}) MERGE (s)-[r%s]->(c) ON CREATE SET r.%s='%s', r.%s='%s', %s", Constant.Const.ENTITY, Constant.Field.NEO4J_CODE, valueA, Constant.Const.ENTITY, Constant.Field.NEO4J_CODE, valueB, relation, Constant.Field.NEO4J_CREATEDAT, ts, Constant.Field.NEO4J_UPDATEDAT, ts, properties);
        run(query);
    }
}
