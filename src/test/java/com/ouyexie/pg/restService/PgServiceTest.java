package com.ouyexie.pg.restService;

import org.junit.Test;

import javax.ws.rs.core.Application;

import static org.junit.Assert.assertEquals;

/**
 * Created by ouyexie on 8/20/16.
 */
public class PgServiceTest extends Application {

    @Test
    public void pingTest() {
        String pong = new PgService().ping();
        assertEquals("Pong!", pong);
    }
}
