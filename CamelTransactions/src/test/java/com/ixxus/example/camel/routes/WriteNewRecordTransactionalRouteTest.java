package com.ixxus.example.camel.routes;

import com.ixxus.example.CamelTransactionsApplication;
import com.ixxus.example.domain.model.Record;
import com.ixxus.example.domain.repository.RecordRepository;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * All rights reserved.
 * Copyright (c) Ixxus Ltd. 2017
 * <p/>
 *
 * @author skellas, Ixxus Ltd.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CamelTransactionsApplication.class)
public class WriteNewRecordTransactionalRouteTest {
    private static final String RECORD_ACTION = "TestAction";

    @Produce(uri = WriteNewRecordTransactionalRoute.ROUTE_URI)
    protected ProducerTemplate writeNewRecordProducer;
    @EndpointInject(uri = "{{writeNewRecordRoute.lastStep.uri}}")
    protected MockEndpoint lastStepMockEndpoint;
    @Autowired
    private CamelContext camelContext;

    @Autowired
    private RecordRepository recordRepository;

    @Before
    public void cleanRecordRepo() {
        recordRepository.deleteAll();
    }

    @Test
    public void testWritingSuccessfully() throws Exception {
        assertEquals("Should be 0 records.", 0, recordRepository.count());

        Object o = writeNewRecordProducer.requestBody(RECORD_ACTION);
        assertNotNull("An object was returned from the route.", o);
        assertTrue("That object is in fact a record.", o instanceof Record);
        assertEquals("Should be 1 record now.", 1, recordRepository.count());

        Record record = (Record) o;
        assertEquals("That record action should be the one we just wrote.", RECORD_ACTION, record.getAction());
        Record recordFromRepo = recordRepository.findAll().get(0);
        assertEquals("In fact, it should be the body of our returned exchange.", recordFromRepo, record);
    }


    @Test
    public void testFailOnSecondWrite() throws Exception {
        assertEquals("Should be 0 records.", 0, recordRepository.count());

        Object o = writeNewRecordProducer.requestBody(RECORD_ACTION);
        assertNotNull("An object was returned from the route.", o);
        assertTrue("That object is in fact a record.", o instanceof Record);
        assertEquals("Should be 1 record now.", 1, recordRepository.count());

        // write a second time, but this time, throw an exception to cause rollback
        lastStepMockEndpoint.whenExchangeReceived(2, exchange -> {
            throw new RuntimeException("GODZILLA ATTACK!!!");
        });

        Object e = writeNewRecordProducer.requestBody(RECORD_ACTION);
        assertNotNull(e);

        lastStepMockEndpoint.assertIsSatisfied();
        // if the transaction rolled back, then the second write never happened
        assertEquals("Should still only be 1 record.", 1, recordRepository.count());
    }
}