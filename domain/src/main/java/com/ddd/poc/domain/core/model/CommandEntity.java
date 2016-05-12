package com.ddd.poc.domain.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.SortedSet;

@Entity
@Table(name = "commands")
public class CommandEntity implements Comparable<CommandEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt = new Date();

    @Column(nullable = false, updatable = false)
    private String commandData;

    @Column(nullable = false, updatable = false)
    private String commandClass;

    @Column(nullable = false, updatable = false, unique = true)
    private String uuid;

    @OrderBy("createdAt asc")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "command")
    private SortedSet<EventEntity> events;

    CommandEntity() {

    }

    public CommandEntity(String commandData, String commandClass, String uuid) {
        this.commandData = commandData;
        this.commandClass = commandClass;
        this.uuid = uuid;
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

    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandEntity)) return false;
        CommandEntity that = (CommandEntity) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "CommandEntity{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", commandData='" + commandData + '\'' +
                ", commandClass='" + commandClass + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }

    @Override
    public int compareTo(CommandEntity o) {
        if(o == null || o.createdAt == null || createdAt == null) {
            return -1;
        } else {
            return createdAt.compareTo(o.createdAt);
        }
    }
}
