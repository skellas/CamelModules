package com.ixxus.example.camel;

import com.ixxus.example.CamelMassAggregatorApplication;
import org.apache.camel.CamelContext;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;

/**
 * All rights reserved.
 * Copyright (c) NIKE, Inc. 2016
 * <p>
 *
 * @author skellas, Ixxus Ltd.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CamelMassAggregatorApplication.class)
@EnableAutoConfiguration
public class ObjectAggregatorTest {

    @Autowired
    CamelContext camelContext;

    @Produce(uri = "seda:SpawnObjects")
    private ProducerTemplate spawnObjectsRouteProducer;

    @Test
    public void sendMessageToRouteTest() throws Exception {
        // Change the values in the /resources/application.properties
        spawnObjectsRouteProducer.sendBody(null);
        // Wait a long time, because this could take a while
        TimeUnit.SECONDS.sleep(30);
        // Check the console, yo
    }
}