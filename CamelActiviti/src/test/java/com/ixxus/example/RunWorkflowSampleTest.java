package com.ixxus.example;

import com.ixxus.example.camel.routes.ActivitiWorkflowHandlerRoutes;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
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

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * All rights reserved.
 * Copyright (c) Ixxus Ltd. 2016
 * <p/>
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

    @Test
    public void testCreationOfWorkflowThroughCamel() throws Exception {
        Object o = runWorkflowProducer.requestBody(new String("Hello World"));
        assertNotNull(o);
        Map<String, Object> variables = runtimeService.getVariables(o.toString());
        assertNotNull(variables);
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(o.toString()).list();
        assertNotNull(taskList);
        TimeUnit.SECONDS.sleep(10);
    }



}
