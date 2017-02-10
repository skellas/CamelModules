package com.ixxus.example.camel.beans;

import com.ixxus.example.AbstractTransactionTest;
import com.ixxus.example.domain.model.Record;
import com.ixxus.example.domain.repository.RecordRepository;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultExchange;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * All rights reserved.
 * Copyright (c) Ixxus Ltd. 2017
 * <p/>
 *
 * @author skellas, Ixxus Ltd.
 */
public class WriteNewRecordTest extends AbstractCamelTest {

    private static final String TEST_ACTION = "Record me!";

    @Autowired
    private WriteNewRecord writeNewRecord;
    @Autowired
    private RecordRepository recordRepository;

    @Test
    public void testWritingNewRecord() throws Exception {
        assertEquals("Should be 0 records.", 0, recordRepository.count());

        Exchange exchange = getExchange();
        writeNewRecord.execute(TEST_ACTION, exchange);

        assertEquals("Should be 1 record now.", 1, recordRepository.count());

        Record record = recordRepository.findAll().get(0);
        assertEquals("That record action should be the one we just wrote.", TEST_ACTION, record.getAction());
        assertEquals("In fact, it should be the body of our returned exchange.", exchange.getIn().getBody(Record.class), record);
    }
}