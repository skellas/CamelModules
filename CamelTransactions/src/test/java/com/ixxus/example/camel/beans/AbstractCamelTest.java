package com.ixxus.example.camel.beans;

import com.ixxus.example.AbstractTransactionTest;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultExchange;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * All rights reserved.
 * Copyright (c) Ixxus Ltd. 2017
 * <p/>
 *
 * @author skellas, Ixxus Ltd.
 */
public abstract class AbstractCamelTest extends AbstractTransactionTest {

    @Autowired
    protected CamelContext camelContext;

    protected Exchange getExchange() {
        return new DefaultExchange(camelContext);
    }
}
