package com.ddd.poc.domain.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "events")
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false, updatable = false)
    private Date createdAt = new Date();

    @Column(nullable = false, updatable = false)
    private String eventClass;

    @Column(nullable = false, updatable = false)
    private String data;

    public EventEntity(String eventClass, String data) {
        this.eventClass = eventClass;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getEventClass() {
        return eventClass;
    }

    public String getData() {
        return data;
    }

}
