package com.ixxus.example.domain.model;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * All rights reserved.
 * Copyright (c) Ixxus Ltd. 2017
 * <p/>
 *
 * @author skellas, Ixxus Ltd.
 */
@Entity
public class Record implements Serializable{
    private static final DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String action;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    protected Record() {}
    public Record(final String action) {
        this.action = action;
        this.createdOn = Calendar.getInstance().getTime();
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
     * Get action.
     *
     * @return action
     */
    public String getAction() {
        return action;
    }

    /**
     * Get createdOn.
     *
     * @return createdOn
     */
    public Date getCreatedOn() {
        return createdOn;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (Objects.isNull(obj) || !Objects.equals(getClass(), obj.getClass()))
            return false;
        Record that = (Record) obj;
        if (Objects.equals(that.action, this.action)
                && Objects.equals(that.createdOn, this.createdOn)
                && Objects.equals(that.id, this.id))
            return true;

        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Record[");
        sb.append("ID:" + (Objects.isNull(this.id) ? "N/A" : this.id));
        sb.append(";");
        sb.append("Action:" + this.action);
        sb.append(";");
        sb.append("@" + df.format(this.createdOn));

        return sb.toString();
    }
}
