package com.ixxus.example.domain.repository;

import com.ixxus.example.domain.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 * All rights reserved.
 * Copyright (c) Ixxus Ltd. 2017
 * <p/>
 *
 * @author skellas, Ixxus Ltd.
 */
@Component
public interface RecordRepository extends JpaRepository<Record, Long> {

}
