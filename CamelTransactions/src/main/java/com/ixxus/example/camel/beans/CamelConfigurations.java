package com.ixxus.example.camel.beans;

import org.apache.camel.builder.DefaultErrorHandlerBuilder;
import org.apache.camel.processor.DefaultErrorHandler;
import org.apache.camel.processor.RedeliveryPolicy;
import org.apache.camel.spring.spi.TransactionErrorHandlerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * All rights reserved.
 * Copyright (c) Ixxus Ltd. 2017
 * <p/>
 *
 * @author skellas, Ixxus Ltd.
 */
@Configuration
public class CamelConfigurations {
    @Bean(name = "transactionErrorHandler")
    public TransactionErrorHandlerBuilder transactionErrorHandler() {
        TransactionErrorHandlerBuilder builder = new TransactionErrorHandlerBuilder();
        RedeliveryPolicy redeliveryPolicy = builder.getRedeliveryPolicy();
        redeliveryPolicy.maximumRedeliveries(0).logStackTrace(false);
        builder.setRedeliveryPolicy(redeliveryPolicy);
        return builder;
    }
    @Bean(name = "nonTransactionErrorHandler")
    public DefaultErrorHandlerBuilder nonTransactionErrorHandler() {
        return (new DefaultErrorHandlerBuilder()).maximumRedeliveries(0).logStackTrace(false);
    }
}
