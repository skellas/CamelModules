package com.ixxus.example.camel.routes;

import org.apache.camel.CamelContext;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * All rights reserved.
 * Copyright (c) NIKE, Inc. 2016
 * <p>
 *
 * @author skellas, Ixxus Ltd.
 */
@Component
public class CollectorToLogRoute extends RouteBuilder {
    public static final String ROUTE_URI = "{{collectorToLogRoute.uri}}";
    protected static final String ROUTE_DESCRIPTION = "This route will just dump the exchange body to log.";

    @Override
    public void configure() throws Exception {
        from(ROUTE_URI).routeId("CollectorToLog")
                .description(ROUTE_DESCRIPTION)
                .log(LoggingLevel.INFO, "Received: " + body().toString());
    }
}
