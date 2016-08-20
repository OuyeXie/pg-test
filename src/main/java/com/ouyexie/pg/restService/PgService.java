package com.ouyexie.pg.restService;

import com.ouyexie.pg.exception.BusinessException;
import com.ouyexie.pg.log4j.MyLogger;
import com.ouyexie.pg.log4j.MyLoggerFactory;
import com.ouyexie.pg.logic.Logic;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 * Created by ouyexie on 8/14/16.
 */
@Path("/")
public class PgService {
    public static final String PONG = "Pong!";
    private static final MyLogger LOG = MyLoggerFactory.getLogger(PgService.class);

    /**
     * Method handling HTTP GET requests. The returned object will be sent to
     * the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Path("ping")
    @Produces(MediaType.TEXT_PLAIN)
    public String ping() {
        return PONG;
    }


    /**
     * Method handling HTTP GET requests. The returned object will be sent to
     * the client as "application/json" media type.
     *
     * @return String that will be returned as a application/json response.
     */
    @GET
    @Path("{table}")
    @Produces(MediaType.APPLICATION_JSON)
    public String table(@Context UriInfo uriInfo) {
        MultivaluedMap<String, String> pathParams = uriInfo.getPathParameters();
        String table = pathParams.getFirst("table");
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        LOG.debug(String.format("select query, pathParams: [%s], queryParams: [%s]", pathParams.toString(), queryParams.toString()));
        String info = null;
        try {
            info = Logic.select(table, queryParams);
        } catch (BusinessException e) {
            LOG.error(e.getDetailedMessage());
            info = e.getMessage();
        }

        return info;
    }

    /**
     * Method handling HTTP OPTIONS requests. The returned object will be sent to
     * the client as "application/json" media type.
     *
     * @return String that will be returned as a application/json response.
     */
    @OPTIONS
    @Path("{table}")
    @Produces(MediaType.APPLICATION_JSON)
    public String metadataTable(@PathParam("table") String table) {
        String info = Logic.getMetadata(table);
        return info;
    }

    /**
     * Method handling HTTP OPTIONS requests. The returned object will be sent to
     * the client as "application/json" media type.
     *
     * @return String that will be returned as a application/json response.
     */
    @OPTIONS
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public String metadataDatabase() {
        String info = Logic.getMetadata();
        return info;
    }
}
