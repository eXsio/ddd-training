package com.ddd.poc.domain.core.dto;

import java.time.LocalDateTime;
import java.util.Collection;

public class CommandDTO {

    private Long id;

    private LocalDateTime createdAt;

    private String commandClass;

    private String data;

    private Collection<EventDTO> events;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCommandClass() {
        return commandClass;
    }

    public void setCommandClass(String commandClass) {
        this.commandClass = commandClass;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Collection<EventDTO> getEvents() {
        return events;
    }

    public void setEvents(Collection<EventDTO> events) {
        this.events = events;
    }
}
