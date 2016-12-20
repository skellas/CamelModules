package com.ixxus.example.camel.routes;

import com.ixxus.example.model.DemoObject;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * All rights reserved.
 * Copyright (c) NIKE, Inc. 2016
 * <p>
 *
 * @author skellas, Ixxus Ltd.
 */
@Component
public class ObjectAggregatorRoute extends RouteBuilder {
    public static final String AGGREGATE_EXCHANGE_PROPERTY = "ObjectID";
    public static final String ROUTE_URI = "seda:ObjectAggregatorRoute";
    protected static final String ROUTE_DESCRIPTION = "This route will accept all exchanges and group them based on a particular exchange"
            + " property before sending them off to an awaiting queue.";

    @Value("${global.waitTime}")
    private long waitTime;

    @Override
    public void configure() throws Exception {
        from(ROUTE_URI).routeId("ObjectAggregatorRoute")
                .description(ROUTE_DESCRIPTION)
                .aggregate(exchangeProperty(AGGREGATE_EXCHANGE_PROPERTY), new AggregateObjectsById())
                .completionPredicate(header("aggregated").isEqualTo(7))
                .completionTimeout(waitTime)
                .to(CollectorToLogRoute.ROUTE_URI);
    }

    private class AggregateObjectsById implements AggregationStrategy {

        @Override
        public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
            if (Objects.isNull(oldExchange))
                return newExchange;
            DemoObject obj = oldExchange.getIn().getBody(DemoObject.class);
            if (Objects.isNull(obj))
                return newExchange;
            // add the new object id to the existing object's aggregated ids
            obj.addAggregatedValue(newExchange.getIn().getBody(DemoObject.class).getValue());

            oldExchange.getIn().setBody(obj);
            return oldExchange;

        }
    }
}
