package com.ixxus.example.domain.repository;

import com.ixxus.example.AbstractTransactionTest;
import com.ixxus.example.domain.model.Record;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * All rights reserved.
 * Copyright (c) Ixxus Ltd. 2017
 * <p/>
 *
 * @author skellas, Ixxus Ltd.
 */
public class RecordRepositoryTest extends AbstractTransactionTest {
    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private DataSource dataSource;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Test
    public void testCRUDOperations() throws Exception {
        assertEquals("There are zero records to start.", 0, recordRepository.findAll().size());
        Record firstRecord = recordRepository.save(new Record("FirstActionTaken"));
        List<Record> allRecords = recordRepository.findAll();
        assertEquals("There is now one record in the repository.", 1, allRecords.size());
        assertEquals("That one record is our very own firstRecord.", firstRecord, allRecords.get(0));
        recordRepository.delete(firstRecord.getId());
        assertEquals("There are now no more records in the repository.", 0, recordRepository.count());
    }

    @Test
    public void testDataSource() throws Exception {
        assertNotNull(dataSource);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        assertNotNull(entityManager);
    }
}