package com.ixxus.example.camel.beans;

import com.ixxus.example.domain.model.Record;
import com.ixxus.example.domain.repository.RecordRepository;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * All rights reserved.
 * Copyright (c) Ixxus Ltd. 2017
 * <p/>
 *
 * @author skellas, Ixxus Ltd.
 */
@Component
public class WriteNewRecord {
    private static final Logger LOGGER = LoggerFactory.getLogger(WriteNewRecord.class);
    @Autowired
    private RecordRepository recordRepository;

    @Handler
    @Transactional
    public void execute(@Body final String actionToRecord, final Exchange exchange) {
        Record newRecord = recordRepository.save(new Record(actionToRecord));
        LOGGER.info(String.format("Wrote Record to Repo: %s", newRecord));
        LOGGER.info(String.format("New Size of Repo: %d", recordRepository.count()));
        exchange.getIn().setBody(newRecord, Record.class);
    }
}
