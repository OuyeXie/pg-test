package com.ouyexie.pg.restService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ouyexie.pg.exception.BusinessException;
import com.ouyexie.pg.exception.HeaderException;
import com.ouyexie.pg.log4j.MyLogger;
import com.ouyexie.pg.log4j.MyLoggerFactory;
import com.ouyexie.pg.logic.Logic;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by ouyexie on 8/14/16.
 */
@Path("/")
public class PgService {
    public static final String PONG = "Pong!";
    private static final MyLogger LOG = MyLoggerFactory.getLogger(PgService.class);
    private static ObjectMapper mapper = new ObjectMapper();

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
    public String table(@Context UriInfo uriInfo, @Context HttpHeaders httpHeader, @Context HttpServletResponse response) {
        String info = null;
        boolean rangeOn = false;
        int offset = 0;
        int limit = Integer.MAX_VALUE;
        int len = 0;
        int lenActul = 0;
        try {
            try {
                MultivaluedMap<String, String> headers = httpHeader.getRequestHeaders();
                Iterator<String> iterator = headers.keySet().iterator();
                while (iterator.hasNext()) {
                    String headName = iterator.next();
                    if (headName.equals("Range")) {
                        String range = headers.get(headName).get(0);
                        String[] ranges = range.split("-");
                        if (ranges.length > 0) {
                            offset = Integer.parseInt(ranges[0]);
                        }
                        if (ranges.length > 1) {
                            limit = Integer.parseInt(ranges[1]) + 1;
                        }
                        len = limit - offset;
                        rangeOn = true;
                    }
                }
            } catch (Exception e) {
                LOG.error(e);
                throw new HeaderException();
            }
            MultivaluedMap<String, String> pathParams = uriInfo.getPathParameters();
            String table = pathParams.getFirst("table");
            MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
            LOG.debug(String.format("select query, pathParams: [%s], queryParams: [%s]", pathParams.toString(), queryParams.toString()));

            List<Map<String, String>> results = Logic.select(table, queryParams);
            lenActul = results.size();
            if (rangeOn) {
                if (offset >= lenActul) {
                    results.clear();
                } else {
                    if (lenActul > len) {
                        results = results.subList(offset, limit);
                        lenActul = len;
                    } else {
                        limit = lenActul + offset - 1;
                    }
                }
            }
            info = mapper.writeValueAsString(results);
        } catch (JsonProcessingException e) {
            LOG.error(e);
        } catch (BusinessException e) {
            LOG.error(e.getDetailedMessage());
            info = e.getMessage();
        }
        if (rangeOn) {
            response.setHeader("Content-Range", String.format("%d-%d/%d", offset, limit - 1, lenActul));
        }
        System.out.println(response.getHeader("Content-Range"));
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
