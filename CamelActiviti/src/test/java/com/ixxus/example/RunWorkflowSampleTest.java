package com.ixxus.example;

import com.ixxus.example.camel.routes.ActivitiWorkflowHandlerRoutes;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * All rights reserved.
 * Copyright (c) Ixxus Ltd. 2016
 * <p/>
 * This test will show how you can create an Activiti job by using Camel routes.
 * It will start the process, then pass off the exchange to separate camel routes
 * both syncronously and asyncronously. It will then wait on that last async route
 * to complete before finishing, using a <receiveTask/> node in the process definition.
 *
 * @author skellas, Ixxus Ltd.
 */
@RunWith(SpringRunner.class)
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
@SpringBootTest(classes = CamelActivitiApplication.class)
public class RunWorkflowSampleTest {
    @Produce(uri = ActivitiWorkflowHandlerRoutes.WORKFLOW_START_URI)
    protected ProducerTemplate runWorkflowProducer;

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    protected TaskService taskService;

    private String processId;
    private String bodyText = "Hello World";

    @Test
    public void testCreationOfWorkflowThroughCamel() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Callable<String> startWorkflowTask = () -> {
            Object o = runWorkflowProducer.requestBody(bodyText);
            assertNotNull(o);
            return o.toString();
        };
        Runnable checkWorkflowTask = () -> {
            assertNotNull("We should have gotten a process id from that Future by now.", processId);
            assertEquals("And that process should still be running.", 1, runtimeService.createProcessInstanceQuery()
                                          .processDefinitionKey(ActivitiWorkflowHandlerRoutes.WORKFLOW_PROCESS_ID)
                                          .processInstanceId(processId).count());
            Map<String, Object> variables = runtimeService.getVariables(processId);
            assertNotNull("And that process should have at least one variable attached to it.", variables);
            assertEquals("A variable that was the Body of the Camel request!", bodyText, variables.get("camelBody"));
        };

        try {
            // start the workflow
            Future<String> future = executor.submit(startWorkflowTask);
            while (!future.isDone()) {
                System.out.println("waiting in test");
                // wait a second
                TimeUnit.SECONDS.sleep(1);
            }
            processId = future.get();
            // check on the workflow
            executor.execute(checkWorkflowTask);
            // the job is actually still waiting, so we'll wait before trying to close out the app
            TimeUnit.SECONDS.sleep(5);
        } finally {
            executor.shutdown();
        }
    }



}
