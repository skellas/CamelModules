package com.ixxus.example.camel.routes;

import com.ixxus.example.camel.beans.AddNewObjectToExchange;
import com.ixxus.example.model.DemoObject;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * All rights reserved.
 * Copyright (c) Ixxus Ltd. 2017
 * <p>
 *
 * @author skellas, Ixxus Ltd.
 */
@Component
public class SpawnObjectsRoute extends RouteBuilder {
    public static final String ROUTE_URI = "seda:SpawnObjects";
    protected static final String ROUTE_DESCRIPTION = "This route will spawn the amount of objects declared in the applications properties"
            + " under the property \"global.howManyObjects\". It will then send them off to the aggregator route for ingestion.";

    @Value("${global.howManyObjects}")
    private int howManyObjects;

    @Override
    public void configure() throws Exception {
        from(ROUTE_URI).routeId("SpawnObjects")
                .description(ROUTE_DESCRIPTION)
                .loop(howManyObjects)
                    .to("bean:AddNewObjectToExchange")
                    .setProperty(ObjectAggregatorRoute.AGGREGATE_EXCHANGE_PROPERTY, simple("${body.id}"))
                    //.log(LoggingLevel.INFO, body().toString())
                    .to(ObjectAggregatorRoute.ROUTE_URI)
                .end();
    }

    @Bean(name = "AddNewObjectToExchange")
    public AddNewObjectToExchange addNewObjectToExchange() {
        return new AddNewObjectToExchange();
    }
}
