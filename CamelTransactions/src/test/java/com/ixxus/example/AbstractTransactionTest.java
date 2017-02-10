package com.ixxus.example;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * All rights reserved.
 * Copyright (c) Ixxus Ltd. 2017
 * <p/>
 *
 * @author skellas, Ixxus Ltd.
 */
@RunWith(SpringRunner.class)
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
@SpringBootTest(classes = CamelTransactionsApplication.class)
public abstract class AbstractTransactionTest {
}
