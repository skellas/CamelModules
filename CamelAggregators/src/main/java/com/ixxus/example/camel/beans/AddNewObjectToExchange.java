package com.ixxus.example.camel.beans;

import com.ixxus.example.model.DemoObject;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Value;

import java.util.Random;

/**
 * All rights reserved.
 * Copyright (c) Ixxus Ltd. 2017
 * <p>
 *
 * @author skellas, Ixxus Ltd.
 */
public class AddNewObjectToExchange {
    @Value("${object.id.minValue}")
    private int minValue;
    @Value("${object.id.maxValue}")
    private int maxValue;

    @Handler
    public void execute(final Exchange exchange) {
        long objectId = RandomUtils.nextLong(minValue, maxValue);
        String value = RandomStringUtils.randomAlphabetic(8);
        DemoObject obj = new DemoObject(objectId, value);

        exchange.getIn().setBody(obj);
    }
}
