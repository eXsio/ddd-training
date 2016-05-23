package com.ddd.poc.domain.core.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Collection;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommandDTO {

    private Long id;

    private String createdAt;

    private String commandClass;

    private String data;

    private String uuid;

    private Collection<EventDTO> events;

    private Collection<CommandDTO> subCommands;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
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

    public Collection<CommandDTO> getSubCommands() {
        return subCommands;
    }

    public void setSubCommands(Collection<CommandDTO> subCommands) {
        this.subCommands = subCommands;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
