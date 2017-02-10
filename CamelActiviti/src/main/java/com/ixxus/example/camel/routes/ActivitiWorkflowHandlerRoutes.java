package com.ixxus.example.camel.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * All rights reserved.
 * Copyright (c) Ixxus Ltd. 2017
 * <p/>
 *
 * @author skellas, Ixxus Ltd.
 */
@Component
public class ActivitiWorkflowHandlerRoutes extends RouteBuilder {
    // Start the workflow
    public static final String WORKFLOW_START_URI = "direct:WorkflowStart";
    private static final String ACTIVITI_START_URI = "activiti:ActivitiWorkflowSample";

    // Step 1 of the workflow
    private static final String WORKFLOW_STEP_1_URI = "activiti:ActivitiWorkflowSample:step1";

    // Step 2 of the workflow
    private static final String WORKFLOW_STEP_2_URI = "activiti:ActivitiWorkflowSample:step2";
    private static final String ACTIVITI_RECEIVER_TASK = "activiti:ActivitiWorkflowSample:step2Completed";

    // Step 3 of the workflow
    private static final String WORKFLOW_STEP_3_URI = "activiti:ActivitiWorkflowSample:step3";

    @Override
    public void configure() throws Exception {


        from(WORKFLOW_START_URI)
                .to(ACTIVITI_START_URI)
                .transform().simple("${property.PROCESS_ID_PROPERTY}");

        from(WORKFLOW_STEP_1_URI)
                .log(LoggingLevel.INFO, "Process ID:${property.PROCESS_ID_PROPERTY} - STEP 1");

        from(WORKFLOW_STEP_2_URI)
                .log(LoggingLevel.INFO, "Process ID:${property.PROCESS_ID_PROPERTY} - STEP 2 START")
                .process(exchange -> {
                    Objects.nonNull(exchange);
                    TimeUnit.SECONDS.sleep(5);
                })
                .log(LoggingLevel.INFO, "Process ID:${property.PROCESS_ID_PROPERTY} - STEP 2 COMPLETE")
                .to(ACTIVITI_RECEIVER_TASK);

        from(WORKFLOW_STEP_3_URI)
                .log(LoggingLevel.INFO, "Process ID:${property.PROCESS_ID_PROPERTY} - STEP 3");

    }
}
