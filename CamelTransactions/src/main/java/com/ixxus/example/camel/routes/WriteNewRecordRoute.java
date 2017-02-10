package com.ixxus.example.camel.routes;

import com.ixxus.example.camel.beans.WriteNewRecord;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

/**
 * All rights reserved.
 * Copyright (c) Ixxus Ltd. 2017
 * <p/>
 *
 * @author skellas, Ixxus Ltd.
 */
@Component
public class WriteNewRecordRoute extends SpringRouteBuilder {
    public static final String ROUTE_URI = "seda:WriteNewRecord";
    public static final String ROUTE_ID = "WriteNewRecord";
    public static final String ROUTE_DESCRIPTION = "Send in the sort of action taken via the body. "
            + "A new Record will be spawned and written to the RecordRepository.";

    private static final String LAST_STEP_IN_ROUTE = "{{writeNewRecordRoute.lastStep.uri}}";
    @Override
    public void configure() throws Exception {
        from(ROUTE_URI).routeId(ROUTE_ID).description(ROUTE_DESCRIPTION)
                .bean(WriteNewRecord.class)
                .to(LAST_STEP_IN_ROUTE);

    }
}
