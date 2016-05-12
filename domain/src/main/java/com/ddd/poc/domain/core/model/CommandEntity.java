package com.ddd.poc.domain.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "commands")
public class CommandEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false, updatable = false)
    private Date createdAt = new Date();

    @Column(nullable = false, updatable = false)
    private String commandData;

    @Column(nullable = false, updatable = false)
    private String commandClass;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "command")
    private Collection<EventEntity> events;

    CommandEntity() {

    }

    public CommandEntity(String commandData, String commandClass) {
        this.commandData = commandData;
        this.commandClass = commandClass;
    }

    public Long getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getCommandData() {
        return commandData;
    }

    public String getCommandClass() {
        return commandClass;
    }

    public Collection<EventEntity> getEvents() {
        return events;
    }
}
