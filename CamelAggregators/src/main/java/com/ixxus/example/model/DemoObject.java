package com.ixxus.example.model;

import java.util.Collection;
import java.util.HashSet;

/**
 * All rights reserved.
 * Copyright (c) Ixxus Ltd. 2017
 * <p>
 *
 * @author skellas, Ixxus Ltd.
 */
public class DemoObject {
    private long id;
    private String value;
    private Collection<String> aggregatedValues;

    public DemoObject(long id, String value) {
        this.id = id;
        this.value = value;
        aggregatedValues = new HashSet<>();
    }

    public void addAggregatedValue(final String aggregatedValue) {
        aggregatedValues.add(aggregatedValue);
    }
    /**
     * Get id.
     *
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Get value.
     *
     * @return value
     */
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "DemoObject{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", aggregatedValues='" + aggregatedValues.toString() + '\'' +
                '}';
    }
}
