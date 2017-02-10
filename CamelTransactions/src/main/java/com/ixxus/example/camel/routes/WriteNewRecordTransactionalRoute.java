package com.ixxus.example.camel.routes;

import com.ixxus.example.camel.beans.WriteNewRecord;
import org.apache.camel.builder.ErrorHandlerBuilder;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * All rights reserved.
 * Copyright (c) Ixxus Ltd. 2017
 * <p/>
 *
 * @author skellas, Ixxus Ltd.
 */
@Component
public class WriteNewRecordTransactionalRoute extends SpringRouteBuilder {
    public static final String ROUTE_URI = "seda:WriteNewRecordTransactional";
    public static final String ROUTE_ID = "WriteNewRecordTransactional";
    public static final String ROUTE_DESCRIPTION = "Send in the sort of action taken via the body. "
            + "A new Record will be spawned and written to the RecordRepository. If that write fails, rollback.";

    private static final String LAST_STEP_IN_ROUTE = "{{writeNewRecordRoute.lastStep.uri}}";

    @Autowired
    @Qualifier("transactionErrorHandler")
    private ErrorHandlerBuilder defaultErrorHandler;

    @Override
    public void configure() throws Exception {
        errorHandler(transactionErrorHandler().maximumRedeliveries(1));

        from(ROUTE_URI).routeId(ROUTE_ID).description(ROUTE_DESCRIPTION)
                .transacted()
                .bean(WriteNewRecord.class)
                .to(LAST_STEP_IN_ROUTE);

    }
}
